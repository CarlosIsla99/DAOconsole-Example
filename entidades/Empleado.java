package entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import bibliotecas.Validaciones;

public class Empleado {

	private Long id;
	private String nif;
	private String nombre;
	private LocalDate fechaNacimineto;
	private BigDecimal sueldo;

	public Empleado(Long id, String nif, String nombre, LocalDate fechaNacimineto, BigDecimal sueldo) {
		super();
		setId(id);
		setNif(nif);
		setNombre(nombre);
		setFechaNacimineto(fechaNacimineto);
		setSueldo(sueldo);
	}

	public Empleado() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		if(id == null || id < 0) {
			throw new EntidadesException("La ID debe ser mayor o igual a 0");
		}
		this.id = id;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		if (!Validaciones.validarNif(nif)) {
			throw new EntidadesException("El NIF introducido debe ser v�lido");
		}
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre == null || nombre.trim().length() < 2 || nombre.matches(".*\\d.*")) {
			throw new EntidadesException("El nombre debe de tener al menos 2 letras y no se admiten n�meros");
		}
		this.nombre = nombre;
	}

	public LocalDate getFechaNacimineto() {
		return fechaNacimineto;
	}

	public void setFechaNacimineto(LocalDate fechaNacimineto) {
		if (fechaNacimineto == null || fechaNacimineto.isAfter(LocalDate.now().minusYears(18))
				|| fechaNacimineto.isBefore(LocalDate.of(1900, 1, 1))) {
			throw new EntidadesException("La fecha de nacimiento debe estar comprendida entre 1900 y la fecha actual");
		}
		this.fechaNacimineto = fechaNacimineto;
	}

	public BigDecimal getSueldo() {
		return sueldo;
	}

	public void setSueldo(BigDecimal sueldo) {
		if (sueldo == null || sueldo.compareTo(BigDecimal.ZERO) < 0) {
			throw new EntidadesException("El sueldo debe ser un n�mero mayor o igual que 0");
		}
		this.sueldo = sueldo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaNacimineto, id, nif, nombre, sueldo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return Objects.equals(fechaNacimineto, other.fechaNacimineto) && Objects.equals(id, other.id)
				&& Objects.equals(nif, other.nif) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(sueldo, other.sueldo);
	}

	@Override
	public String toString() {
		return "////// EMPLEADO ID: " + id + " //////" + "\nNIF: " + nif + "\nNombre: " + nombre
				+ "\nFecha Nacimiento: " + fechaNacimineto + "\nSueldo: " + sueldo + "� \n";
	}

}
