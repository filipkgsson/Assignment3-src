import java.lang.reflect.InvocationTargetException;

public class SaverDemo {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Tree<String> t = new Tree<String>("top", new Tree[] { new Tree("sub1"), new Tree("sub2") });
		Saver s = new Saver();
		String r = s.save(t);
		System.out.println(r);
	}

}
