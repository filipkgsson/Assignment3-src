import java.lang.reflect.*;

public class Saver {

	private int count;

	public Saver() {
		count = 0;
	}

	public String save(Object o) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		String xml = "";
		Class<?> clazz = o.getClass();
		Element e = clazz.getDeclaredAnnotation(Element.class);
		if (e != null) {
			for(int i = 0; i < count; i++)
				xml += " ";
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
							count ++;
							s += "\n";
							for(int i = 0; i < count; i++)
								s += " ";
							s += "<" + sub.name() + ">";
							for (Object child : children) {
								//Saver temp = new Saver();
								s += "\n " + this.save(child);
							}
							s += "\n";
							count --;
							String space = "";
							for(int i = 0; i < count; i++)
								s += " ";
							s += " ";
							s += "</" + sub.name() + ">\n" + space + "</" + e.name() + ">";
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
			xml += s;
		}
		return xml;
	}
}