import java.lang.reflect.*;

public class Saver {
	
	private String xml;
	
	public Saver() {
	}
		
	public String save(Object o) {
		Class<?> clazz = o.getClass();
		Element e = clazz.getDeclaredAnnotation(Element.class);
		if(e != null) {
			xml += "\n<" + e.name() + " ";
			for(Method m : clazz.getDeclaredMethods()) {
				SubElements sub = m.getDeclaredAnnotation(SubElements.class);
				if(sub != null) {
					try {
						Field f = clazz.getDeclaredField("children");
					} catch (NoSuchFieldException e1) {
						e1.printStackTrace();
					} catch (SecurityException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		return "";
	}
}