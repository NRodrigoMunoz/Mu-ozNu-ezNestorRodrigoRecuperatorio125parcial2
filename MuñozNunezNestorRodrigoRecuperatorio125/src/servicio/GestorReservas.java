package servicio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import modelo.ReservaViaje;

public class GestorReservas<T>  implements Gestionable<T>{
    List<T> listaViajes = new ArrayList<>();
    /*
    agrega el item a la lista si este mismo no es nulo y no es repetido
    */
    @Override
    public void agregar(T item) {
        if (item == null) {
            throw new NullPointerException("El item que se a pasado es nulo");
        }
        else if (listaViajes.contains(item)) {
            throw new RuntimeException("El item esta repetido");
        }
        listaViajes.add(item);
    }
    /*
    Recibe un indice el cual es verificado si es menor a 0 o sobrepasa la cantidad
    de items en la lista lanza una excepcion este es eliminado con el metodo remove y devuelto.
    */
    @Override
    public T eliminar(int indice) {
        if (indice < 0 || indice >= listaViajes.size()) {
            throw new IndexOutOfBoundsException("El indice es invalido");
        }
        return listaViajes.remove(indice);
    }

    @Override
    public T obtener(int indice) {
        if (indice < 0 || indice >= listaViajes.size()) {
            throw new IndexOutOfBoundsException("El indice es invalido");
        }
        return listaViajes.get(indice);
    }

    @Override
    public void limpiarElementos() {
        listaViajes.clear();
    }
    /*
    Los ordena de forma natural utilizando un Comparator castiado a Comparator<? super T>
    que permite recibir objetos que sean superclase de T llamando a la sobrecarga de ordenar
    */
    @Override
    public void ordenar() {
        ordenar((Comparator<? super T>) Comparator.naturalOrder());
                
    }
    /*
    Recibe un comparator en forma de lambda y este por parametro se pasa al metodo sort de la lista
    */
    @Override
    public void ordenar(Comparator<? super T> comparator) {      
        listaViajes.sort(comparator);
    }
    /*
    Se encarga de filtrar los objetos por medio de una lambda
    este devuelve una lista que previamente con el metodo test
    realiza una evaluacion boolean si es true lo agregaga a la lista 
    */
    @Override
    public List<T> filtrar(Predicate<? super T> criterio) {
        List<T> listaRetorno = new ArrayList<>();
        for (T item : listaViajes) {
            if (criterio.test(item)) {
                listaRetorno.add(item);
            }
        }
        return listaRetorno;
    }
    /*
    Guarda una lista de objetos en un archivo binario por parametro recibe un path
    se verifica que la lista no este vacia despues con el writeObject se escribe el objeto
    este debe ser serializable y salida close cierra el archivo para que cuando se utilize 
    puedan escribirse los objetos
    */
    @Override
    public void guardarEnBinario(String path) throws IOException {
        if (listaViajes.isEmpty()) {
            throw new IllegalArgumentException("La lista esta vacia.");
        }
        ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(path));
        salida.writeObject(listaViajes);
        salida.close();
    }
    /*
    Se encarga de leer una lista de objetos en un archivo binario y cargarla en la variable listaviajes
    el metodo limpiarElementos se utiliza para borrar la lista y a la hora de cargar no se dupliquen
    se lee el archivo por el metodo readObject qeu interpreta los objetos serializables y los convierte
    al tipo original que es una lista de tipo T.
    */
    @Override
    public void cargarDesdeBinario(String path) throws IOException, ClassNotFoundException {
        limpiarElementos();
        try {
            ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(path));
            listaViajes = (List<T>) entrada.readObject();
            entrada.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    @Override
    public void guardarEnCSV(String path) throws IOException {
        if (listaViajes.isEmpty()) {
            throw new IllegalArgumentException("La lista esta vacia.");
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        if (listaViajes.get(0) instanceof ReservaViaje r) {
                    bw.write(r.toHeaderCSV() + "\n");
        }    
        for(T item : listaViajes){
            if (item instanceof ReservaViaje r) {
                    bw.write(r.toCSV() + "\n");
                }    
        }
        bw.close();
        
    }

    @Override
    public void cargarDesdeCSV(String path, Function<String, T> transformadora) throws IOException {       
        limpiarElementos();
        try{
        BufferedReader br = new BufferedReader(new FileReader(path));
        String linea;
        br.readLine();
        while((linea = br.readLine()) != null){  
        listaViajes.add(transformadora.apply(linea));
        }
        br.close();
        }catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        
    }

    @Override
    public void mostrarTodos() {
        for (T listaViaje : listaViajes) {
            System.out.println(listaViaje);
        }
    }

    
    
}
