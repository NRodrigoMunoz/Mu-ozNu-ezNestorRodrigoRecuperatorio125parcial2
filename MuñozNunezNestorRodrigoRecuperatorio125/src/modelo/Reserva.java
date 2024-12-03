package modelo;

import java.io.Serializable;
import java.time.LocalDate;


public abstract class Reserva implements Serializable{
    private static final long SerialVersionUID = 1L;
    private int id;
    private String pasajero;
    private LocalDate fecha;

    public Reserva(int id, String pasajero, LocalDate fecha) {
        this.id = id;
        this.pasajero = pasajero;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Reserva" + "id: " + id + ", pasajero: " + pasajero + ", fecha: " + fecha + ' ';
    }

    public int getId() {
        return id;
    }

    public String getPasajero() {
        return pasajero;
    }

    public LocalDate getFecha() {
        return fecha;
    }
    
    
    
}
