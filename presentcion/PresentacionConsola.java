package presentcion;

import static bibliotecas.Consola.errorPl;
import static bibliotecas.Consola.pedirInt;
import static bibliotecas.Consola.pl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Logger;

import accesodatos.DaoEmpleado;
import accesodatos.DaoEmpleadoMemoria;
import entidades.Empleado;

public class PresentacionConsola {

	private static final DaoEmpleado DAO = DaoEmpleadoMemoria.getInstancia();
	private static final Logger LOGGER = Logger.getLogger(PresentacionConsola.class.getName());
	
	public static void main(String[] args) {
		LOGGER.info("Se ha iniciado el programa");

		// LOGGER.log(Level.SEVERE, "Esto es un ejemplo en caso de error", new
		// RuntimeException("Aqu� va el error"));
		int opcion;
		do {
			mostrarMenu();
			opcion = pedirOpcion();
			procesarOpcion(opcion);
		} while (opcion != 0);
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
		return pedirInt("Selecciona una de las opciones del men�");
	}

	private static void procesarOpcion(int opcion) {
		switch (opcion) {
		case 1:
			// mostrarTodos();
			mostrarTodosTabla();
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
			LOGGER.info("Se ha finalizado el programa");
			pl("0. �Gracias por usar la apliaci�n!");
			break;
		default:
			errorPl("Por favor, elija una de las opciones existentes");
		}
	}

	private static void borrar() {
		Scanner sc = new Scanner(System.in);
		boolean encontrado = false;

		pl("Introduce la ID del empleado que desee borrar: ");
		Long selectedId = null;
		try {
			selectedId = Long.parseLong(sc.nextLine());
		} catch (Exception e) {
		}
		;

		for (Empleado e : DAO.obtenerTodos()) {
			if (e.getId() == selectedId) {
				DAO.borrar(e.getId());
				pl("Se ha eliminado el empleado con ID: " + e.getId());
				encontrado = true;
				break;
			}
		}

		if (encontrado == false) {
			pl("No se ha encontrado un empleado con ID: " + selectedId);
		}

	}

	private static void modificar() {
		Empleado empleado = new Empleado();
		Scanner sc = new Scanner(System.in);

		boolean encontrado = false;
		Long selectedId = 0L;

		do {
			pl("Introduce la ID del empleado que quiera modificar: ");

			try {
				selectedId = Long.parseLong(sc.nextLine());
				pl(DAO.obtenerPorId(selectedId));
			} catch (Exception e) {
			}
			;

			for (Empleado e : DAO.obtenerTodos()) {
				if (e.getId() == selectedId) {
					empleado.setId(selectedId);
					encontrado = true;
				}
			}

			if (encontrado == false) {
				pl("No se ha encontrado un empleado con ID: " + selectedId);
			}
		} while (encontrado == false);

		boolean repetir = false;
		int opcion = 0;

		do {
			pl("�Qu� campo le gustar�a modificar?");
			pl("1. NIF");
			pl("2. Nombre");
			pl("3. Fecha de Nacimiento");
			pl("4. Sueldo");
			pl("0. Cancelar");

			try {
				opcion = Integer.parseInt(sc.nextLine());
				repetir = false;
			} catch (Exception e) {
				pl("Opci�n no v�lida");
				repetir = true;
			}
		} while (repetir);

		empleado.setNif(DAO.obtenerPorId(selectedId).getNif());
		empleado.setNombre(DAO.obtenerPorId(selectedId).getNombre());
		empleado.setFechaNacimineto(DAO.obtenerPorId(selectedId).getFechaNacimineto());
		empleado.setSueldo(DAO.obtenerPorId(selectedId).getSueldo());

		boolean modificado = true;

		switch (opcion) {
		case 1:
			do {
				pl("Introduce el NIF: ");
				try {
					String nif = sc.nextLine();
					empleado.setNif(nif);
					repetir = false;
				} catch (Exception e) {
					repetir = true;
					pl("El NIF introducido debe ser v�lido");
				}
			} while (repetir);
			break;
		case 2:
			do {
				pl("Introduce el nombre: ");
				try {
					String nombre = sc.nextLine();
					empleado.setNombre(nombre);
					repetir = false;
				} catch (Exception e) {
					repetir = true;
					pl("El nombre debe de tener al menos 2 letras y no se admiten n�meros");
				}
			} while (repetir);
			break;
		case 3:
			do {
				pl("Introduce la fecha de nacimiento: AAAA-MM-D");
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
					LocalDate fechaNacimiento = LocalDate.parse(sc.nextLine(), formatter);
					empleado.setFechaNacimineto(fechaNacimiento);
					repetir = false;
				} catch (Exception e) {
					repetir = true;
					pl("La fecha de nacimiento debe estar comprendida entre 1900 y la fecha actual");
				}
			} while (repetir);
			break;
		case 4:
			do {
				pl("Introduce el sueldo: ");
				try {
					BigDecimal sueldo = new BigDecimal(sc.nextLine());
					empleado.setSueldo(sueldo);
					repetir = false;
				} catch (Exception e) {
					repetir = true;
					pl("El sueldo debe ser mayor o igual que 0");
				}
			} while (repetir);
			break;
		case 0:
			pl("Se ha cancelado la modificaci�n");
			modificado = false;
			break;
		default:
			errorPl("Opci�n no v�lida");
			modificado = false;
		}

		if (modificado) {
			DAO.modificar(empleado);
			pl("Se ha modificado el empleado con ID: " + empleado.getId());
		}

	}

	private static void insertar() {
		Empleado empleado = new Empleado();
		Scanner sc = new Scanner(System.in);
		boolean repetir = false;

		do {
			pl("Introduce el NIF: ");
			try {
				String nif = sc.nextLine();
				empleado.setNif(nif);
				repetir = false;
			} catch (Exception e) {
				repetir = true;
				pl("El NIF introducido debe ser v�lido");
			}
		} while (repetir);

		do {
			pl("Introduce el nombre: ");
			try {
				String nombre = sc.nextLine();
				empleado.setNombre(nombre);
				repetir = false;
			} catch (Exception e) {
				repetir = true;
				pl("El nombre debe de tener al menos 2 letras y no se admiten n�meros");
			}
		} while (repetir);

		do {
			pl("Introduce la fecha de nacimiento: AAAA-MM-D");
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
				LocalDate fechaNacimiento = LocalDate.parse(sc.nextLine(), formatter);
				empleado.setFechaNacimineto(fechaNacimiento);
				repetir = false;
			} catch (Exception e) {
				repetir = true;
				pl("La fecha de nacimiento debe estar comprendida entre 1900 y la fecha actual");
			}
		} while (repetir);

		do {
			pl("Introduce el sueldo: ");
			try {
				BigDecimal sueldo = new BigDecimal(sc.nextLine());
				empleado.setSueldo(sueldo);
				repetir = false;
			} catch (Exception e) {
				repetir = true;
				pl("El sueldo debe ser mayor o igual que 0");
			}
		} while (repetir);

		DAO.intsertar(empleado);
		pl("El nuevo empleado ha sido insertado correctamente: ");
		mostrarLinea(empleado);
	}

	private static void mostrarSeleccionado() {
		Scanner sc = new Scanner(System.in);
		pl("Introduce la ID del empleado: ");

		try {
			Long selectedId = Long.parseLong(sc.nextLine());
			if (DAO.obtenerPorId(selectedId) == null) {
				pl("No existe esa ID");
				mostrarSeleccionado();
			} else {
				pl(DAO.obtenerPorId(selectedId));
			}
		} catch (Exception e) {
			// LOGGER.log(Level.SEVERE, "Error al introducir ID en b�squeda empleado", e);
			pl("Formato de ID incorrecto");
			mostrarSeleccionado();
		}

	}

	/*
	 * private static void mostrarTodos() { for(Empleado e: DAO.obtenerTodos()) {
	 * mostrarLinea(e); } }
	 */

	private static void mostrarTodosTabla() {
		System.out.println("-------------------------------------------------------------");
		System.out.printf("%5S %9S %11S %18S %8S %n", "ID", "NIF", "Nombre", "Fecha Nacimiento", "Sueldo");
		System.out.println("-------------------------------------------------------------");

		for (Empleado e : DAO.obtenerTodos()) {
			System.out.format("%5d %12s %8s %15s %11s %n", e.getId(), e.getNif(), e.getNombre(), e.getFechaNacimineto(),
					e.getSueldo() + "�");
		}
		System.out.println("-------------------------------------------------------------");
	}

	private static void mostrarLinea(Empleado e) {
		pl(e);
	}

}
