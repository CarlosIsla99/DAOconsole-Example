package accesodatos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.TreeMap;

import entidades.Empleado;

public class DaoEmpleadoMemoria implements DaoEmpleado {

	private static final TreeMap<Long, Empleado> empleados = new TreeMap<>();

	static {
		empleados.put(1L, new Empleado(1L, "12345678Z", "Javier", LocalDate.of(2000, 1, 2), new BigDecimal("15000")));
		empleados.put(2L, new Empleado(2L, "87654321X", "Rafa", LocalDate.of(1999, 2, 1), new BigDecimal("23000")));
		empleados.put(3L, new Empleado(3L, "18273645F", "Laura", LocalDate.of(1998, 4, 5), new BigDecimal("22500")));
		empleados.put(4L, new Empleado(4L, "29701926W", "Sara", LocalDate.of(1995, 1, 19), new BigDecimal("17800")));
		empleados.put(5L, new Empleado(5L, "13595576T", "Pedro", LocalDate.of(1997, 8, 28), new BigDecimal("16200")));
	}

	private DaoEmpleadoMemoria() {
	}

	private static final DaoEmpleadoMemoria INSTANCIA = new DaoEmpleadoMemoria();

	public static DaoEmpleadoMemoria getInstancia() {
		return INSTANCIA;
	}

	@Override
	public Iterable<Empleado> obtenerTodos() {
		return empleados.values();
	}

	@Override
	public Empleado obtenerPorId(Long id) {
		return empleados.get(id);
	}

	@Override
	public void intsertar(Empleado empleado) {
		Long id = empleados.size() > 0 ? empleados.lastKey() + 1L : 1L;
		empleado.setId(id);
		empleados.put(empleado.getId(), empleado);
	}

	@Override
	public void modificar(Empleado empleado) {
		empleados.put(empleado.getId(), empleado);
	}

	@Override
	public void borrar(Long id) {
		empleados.remove(id);
	}

}
