import java.lang.reflect.*;

public class Saver {

	private int count;

	public Saver() {
		count = 0;
	}

	public String save(Object o) throws IllegalArgumentException, InvocationTargetException, IllegalAccessException {
		String xml = "";
		Class<?> clazz = o.getClass();
		Element e = clazz.getDeclaredAnnotation(Element.class);
		if (e == null)
			return "";
		xml += createSpace(count) + "<" + e.name() + " ";
		String s = "";
		for (Method m : clazz.getDeclaredMethods()) {
			SubElements sub = m.getDeclaredAnnotation(SubElements.class);
			if (sub != null) {
				Object[] children = (Object[]) m.invoke(o);
				if (children != null) {
					count++;
					s += "\n" + createSpace(count) + "<" + sub.name() + ">";
					for (Object child : children)
						s += "\n " + this.save(child);
					s += "\n" + createSpace(count) + "</" + sub.name() + ">\n" + "</" + e.name() + ">";
					count--;
				}
			}
			ElementField ef = m.getDeclaredAnnotation(ElementField.class);
			if (ef != null)
				xml += ef.name() + "=" + "\"" + m.invoke(o) + "\"";
		}
		if(s.equals(""))
			xml += "/";
		xml += ">" + s;
		return xml;
	}
	
	private static String createSpace(int n) {
		String space = "";
		for (int i = 0; i < n; i++)
			space += " ";
		return space;
	}
	
}