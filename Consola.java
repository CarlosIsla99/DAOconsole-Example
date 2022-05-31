import java.util.Scanner;

public class Consola {
	private static final Scanner sc = new Scanner(System.in);
	
	public static void pl(Object o) {
		System.out.println(o);
	}
	
	public static void p(Object o) {
		System.out.println(o);
	}
	
	public static void errorPl(Object o) {
		System.out.println(o);
	}
	
	public static int pedirInt(String mensaje) {
		p(mensaje + ": ");
		return sc.nextInt();
	}
}
