package modelo;

import java.io.Serializable;
import java.time.LocalDate;


public class ReservaViaje extends Reserva implements Comparable<ReservaViaje>,Serializable{
    //Atributos
    private static final long SerialVersionUID = 1L;
    private String destino;
    private TipoTransporte transporte;

    //Constructor
    public ReservaViaje(int id, String pasajero, LocalDate fecha,String destino, TipoTransporte transporte) {
        super(id, pasajero, fecha);
        this.destino = destino;
        this.transporte = transporte;
    }

    @Override
    public String toString() {
        String nombreDeTipo = transporte.name();
        return super.toString() + "destino: " + destino + ", transporte: " + nombreDeTipo.substring(0,1) + nombreDeTipo.substring(1).toLowerCase();
    }
    /*
    Metodo que se implementa de Comparable en este metodo se compara su orden por fecha
    */
    @Override
    public int compareTo(ReservaViaje reserva) {
        return getFecha().compareTo(reserva.getFecha());
    }
    /*
    Metodo para poner un encabezado con los datos en el archivo CSV
    */
    public String toHeaderCSV() {
        return "id,pasajero,fecha,destino,transporte";
    }
    /*
    Metodo utilizado en guardarEnCSV para guardar los datos de las reservas de viajes
    */
    public String toCSV() {
        return getId() + "," + getPasajero() + "," + getFecha() + "," + destino + "," + transporte;
    }
    /*
    Se utiliza en el metodo cargarDesdeCSV para leer los datos del archivo recibir el viajeCSV
    se almacena en una cadena de texto el cual se separa por , al recibirla se genera 5 valores
    de los cuales se parcean para poder generar la instanciacion del objeto ReservaViaje.
    */
    public static ReservaViaje fromCSV(String viajeCSV){
        String[] values = viajeCSV.split(",");
        ReservaViaje retornarViaje = null;
        if (values.length == 5) {
            int id = Integer.parseInt(values[0]);
            String nombre = values[1];
            LocalDate fecha = LocalDate.parse(values[2]);
            String destino = values[3];
            TipoTransporte transporte = TipoTransporte.valueOf(values[4]);
            retornarViaje = new ReservaViaje(id,nombre,fecha,destino,transporte);
        }
        else{
            throw new ArrayIndexOutOfBoundsException("La cadena values no tiene 4 elementos despues de ser dividida.");
        }
        return retornarViaje;
    }

    public TipoTransporte getTransporte() {
        return transporte;
    }
    /*
    En primera instancia pregunta si el objeto es del mismo tipo, si es nulo
    si tiene el mismo tipo de trasnporte y los compara por su id. 
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof TipoTransporte t) {
            return transporte.equals(t);
        }
        ReservaViaje other = (ReservaViaje) obj;
        return getId() == other.getId();
    }
    
}
