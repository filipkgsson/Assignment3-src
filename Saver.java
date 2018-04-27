import java.lang.reflect.*;

public class Saver {

	private String xml;

	public Saver() {
		xml = "";
	}

	public String save(Object o) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Class<?> clazz = o.getClass();
		Element e = clazz.getDeclaredAnnotation(Element.class);
		if (e != null) {
			xml += "<" + e.name() + " ";
			String s = "";
			String end = "";
			for (Method m : clazz.getDeclaredMethods()) {
				SubElements sub = m.getDeclaredAnnotation(SubElements.class);
				if (sub != null) {
					Field f = clazz.getDeclaredField("children");
					f.setAccessible(true);
					if (f.getType().isArray()) {
						Object[] children = (Object[]) f.get(o);
						if (children != null) {
							s += " <" + sub.name() + ">";
							for (Object child : children) {
								Saver temp = new Saver();
								s += "\n " + temp.save(child);
							}
							s += "\n</" + sub.name() + ">\n</" + e.name() + ">";
						}
						else
							end = "/";
					}
				}
				ElementField ef = m.getDeclaredAnnotation(ElementField.class);
				if (ef != null) {
					Object value = m.invoke(o);
					xml += ef.name() + "=" + "\"" + value + "\"";
				}
			}
			xml += end + ">";
			xml += "\n " + s;
		}
		return xml;
	}
}