// Data Acces Object
// CRUD: Create, Retrieve, Update and Delete
public interface Dao<T> {
	Iterable<T> obtenerTodos();
	T obtenerPorId(Long id);
	
	void intsertar(T objeto);
	void modificar(T objeto);
	void borrar(Long id);

}
