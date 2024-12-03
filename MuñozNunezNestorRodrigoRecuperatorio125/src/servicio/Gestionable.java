package servicio;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Gestionable<T> {
    
    void agregar(T item);
    
    T eliminar(int indice);
    
    T obtener(int indice);
    
    void limpiarElementos();
    
    void ordenar();
    
    void ordenar(Comparator<? super T> comparator);
    
    List<T> filtrar(Predicate<? super T> criterio);
    
    void guardarEnBinario(String path) throws IOException;
    
    void cargarDesdeBinario(String path) throws IOException, ClassNotFoundException;
    
    void guardarEnCSV(String path) throws IOException;
    
    void cargarDesdeCSV(String path,Function<String,T> transformadora) throws IOException;
    
    void mostrarTodos();
}
