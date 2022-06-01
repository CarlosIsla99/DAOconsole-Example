package presentcion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import entidades.Empleado;
import accesodatos.DaoEmpleado;
import accesodatos.DaoEmpleadoMemoria;
import static bibliotecas.Consola.*;

public class PresentacionConsola {
	private static final DaoEmpleado DAO = DaoEmpleadoMemoria.getInstancia();

	public static void main(String[] args) {
		int opcion;
		do {
			mostrarMenu();
			opcion = pedirOpcion();
			procesarOpcion(opcion);
		} while(opcion != 0);
	}

	private static void mostrarMenu() {
		pl("1. Mostrar todo");
		pl("2. Buscar por Id");
		pl("3. Insertar");
		pl("4. Modificar");
		pl("5. Borrar");
		pl("0. Salir");
	}

	private static int pedirOpcion() {
		return pedirInt("Selecciona una de las opciones del menú");
	}

	private static void procesarOpcion(int opcion) {
		switch(opcion) {
		case 1:
			mostrarTodos();
			break;
		case 2:
			mostrarSeleccionado();
			break;
		case 3:
			insertar();
			break;
		case 4:
			modificar();
			break;
		case 5:
			borrar();
			break;
		case 0:
			pl("0. ¡Gracias por usar la apliación! :)");
			break;
		default:
			errorPl("Por favor, elija una de las opciones existentes");
		}
	}

	private static void borrar() {
		Scanner sc = new Scanner(System.in);
		pl("Introduce la ID del empleado que desee borrar: ");
		Long selectedId = Long.parseLong(sc.nextLine());
		
		DAO.borrar(selectedId);	
		pl("Se ha eliminado el empleado con ID: " + selectedId);
	}

	private static void modificar() {
		Empleado empleado = new Empleado();
		Scanner sc = new Scanner(System.in);
		
		pl("Introduce la ID del empleado que quiera modificar: ");
		Long selectedId = Long.parseLong(sc.nextLine());
		empleado.setId(selectedId);
		
		pl("¿Qué campo le gustaría modificar?");
		pl("1. NIF");
		pl("2. Nombre");
		pl("3. Fecha de Nacimiento");
		pl("4. Sueldo");
		pl("0. Cancelar");
		int opcion = Integer.parseInt(sc.nextLine());
		
		empleado.setNif(DAO.obtenerPorId(selectedId).getNif());
		empleado.setNombre(DAO.obtenerPorId(selectedId).getNombre());
		empleado.setFechaNacimineto(DAO.obtenerPorId(selectedId).getFechaNacimineto());
		empleado.setSueldo(DAO.obtenerPorId(selectedId).getSueldo());
			
		switch(opcion) {
		case 1:
			pl("Introduce el NIF: ");
			String nif = sc.nextLine();
			empleado.setNif(nif);
			break;
		case 2:
			pl("Introduce el nombre: ");
			String nombre = sc.nextLine();
			empleado.setNombre(nombre);
			break;
		case 3:
			pl("Introduce la fecha de nacimiento: yyyy-MM-d");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
			LocalDate fechaNacimiento = LocalDate.parse(sc.nextLine(), formatter);
			empleado.setFechaNacimineto(fechaNacimiento);
			break;
		case 4:
			pl("Introduce el sueldo: ");
			BigDecimal sueldo = sc.nextBigDecimal();
			empleado.setSueldo(sueldo);
			break;
		case 0:
			pl("Se ha cancelado la modificación");
			break;
		default:
			errorPl("Por favor, elija una de las opciones existentes");
		}
		
		DAO.modificar(empleado);
		pl("Se ha modificado el empleado con ID: " + selectedId);
	}

	private static void insertar() {
		Empleado empleado = new Empleado();
		Scanner sc = new Scanner(System.in);
		
		pl("Introduce el NIF: ");
		String nif = sc.nextLine();
		empleado.setNif(nif);
		
		pl("Introduce el nombre: ");
		String nombre = sc.nextLine();
		empleado.setNombre(nombre);
		
		pl("Introduce la fecha de nacimiento: yyyy-MM-d");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
		LocalDate fechaNacimiento = LocalDate.parse(sc.nextLine(), formatter);
		empleado.setFechaNacimineto(fechaNacimiento);
		
		pl("Introduce el sueldo: ");
		BigDecimal sueldo = sc.nextBigDecimal();
		empleado.setSueldo(sueldo);
		
		DAO.intsertar(empleado);
		pl("Nuevo empleado insertado (ID: " + empleado.getId() +")");
	}

	private static void mostrarSeleccionado() {
		Scanner sc = new Scanner(System.in);
		pl("Introduce la ID del empleado: ");
		
		try {
				Long selectedId = Long.parseLong(sc.nextLine());
				if(DAO.obtenerPorId(selectedId) == null) {
					pl("No existe un usuario con ese ID");
					mostrarSeleccionado();
				} else {
					pl(DAO.obtenerPorId(selectedId));
				}
			}
		catch(Exception e) {
				pl("Formato de ID incorrecto");
				pl("Intentelo de nuevo...");
				mostrarSeleccionado();
			}
		
	}

	private static void mostrarTodos() {
		for(Empleado e: DAO.obtenerTodos()) {
			mostrarLinea(e);
		}
	}

	private static void mostrarLinea(Empleado e) {
		pl(e);
	}

}
