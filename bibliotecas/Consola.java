package bibliotecas;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import presentcion.PresentacionConsola;

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
		Integer intRetorno = null;
		boolean repetir = true;
		do {
			try {
				intRetorno = Integer.parseInt(sc.nextLine());
				repetir = false;
			} catch (Exception e ) {
				System.out.println(mensaje);
			}
		} while(repetir);		
		return intRetorno;
	}
}
