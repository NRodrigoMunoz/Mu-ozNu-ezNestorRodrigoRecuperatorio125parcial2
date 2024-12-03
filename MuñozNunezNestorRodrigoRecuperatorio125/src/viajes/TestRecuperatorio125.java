package viajes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import modelo.AppConfig;
import modelo.ReservaViaje;
import modelo.TipoTransporte;
import servicio.GestorReservas;

public class TestRecuperatorio125 {
    public static void main(String[] args) {
        try{
        // Crear instancia del gestor 
        GestorReservas<ReservaViaje> gestor = new GestorReservas<>(); 
        // Agregar reservas 
        gestor.agregar(new ReservaViaje(1, "Juan Perez", LocalDate.of(2024, 12, 10), 
        "Paris", TipoTransporte.AVION)); 
        gestor.agregar(new ReservaViaje(2, "Ana Gomez", LocalDate.of(2024, 11, 20), 
        "Madrid", TipoTransporte.TREN));     
        gestor.agregar(new ReservaViaje(3, "Luis Torres", LocalDate.of(2024, 12, 5), 
        "Tokio", TipoTransporte.AVION)); 
        gestor.agregar(new ReservaViaje(4, "Maria Lopez", LocalDate.of(2025, 1, 15), 
        "Roma", TipoTransporte.BARCO)); 
 
        // Mostrar reservas agregadas 
        System.out.println("Reservas iniciales:"); 
        gestor.mostrarTodos(); 
 
        System.out.println("\nFiltrar por transporte: AVION"); 
        List<ReservaViaje> avionReservas = gestor.filtrar(viaje -> viaje.getTransporte().equals(TipoTransporte.AVION)); 
        avionReservas.forEach(System.out::println); 
 
        // Filtrar por rango de fechas 
        System.out.println("\nFiltrar por rango de fechas (2024-12-01 a 2024-12-31):"); 
        List<ReservaViaje> rangoFechas = gestor.filtrar(viaje -> !viaje.getFecha().isBefore(LocalDate.of(2024, 12, 1)) && !viaje.getFecha().isAfter(LocalDate.of(2024, 12, 31))); 
        rangoFechas.forEach(System.out::println); 
 
        // Ordenar por nombre del pasajero 
        System.out.println("\nReservas ordenadas por nombre del pasajero:"); 
        gestor.ordenar(Comparator.comparing(viaje -> viaje.getPasajero())); 
        gestor.mostrarTodos(); 
 
        // Guardar y cargar en archivo binario 
        gestor.guardarEnBinario(AppConfig.PATH_BIN); 
        GestorReservas<ReservaViaje> gestorBinario = new GestorReservas<>(); 
        gestorBinario.cargarDesdeBinario(AppConfig.PATH_BIN); 
        System.out.println("\nReservas cargadas desde archivo binario:"); 
        gestorBinario.mostrarTodos(); 
 
        // Guardar y cargar en archivo CSV 
        gestor.guardarEnCSV(AppConfig.PATH_CSV); 
        GestorReservas<ReservaViaje> gestorCSV = new GestorReservas<>(); 
        gestorCSV.cargarDesdeCSV(AppConfig.PATH_CSV, ReservaViaje::fromCSV); 
        System.out.println("\nReservas cargadas desde archivo CSV:"); 
        gestorCSV.mostrarTodos(); 
        
    } catch (IOException | ClassNotFoundException ex) { 
        System.out.println("Error: " + ex.getMessage()); 
    }
    catch (ArrayIndexOutOfBoundsException e) { 
        System.err.println("Error: " + e.getMessage()); 
    }
    catch (IllegalArgumentException e) { 
        System.err.println("Error: " + e.getMessage()); 
    }
    catch (IndexOutOfBoundsException e) { 
        System.err.println("Error: " + e.getMessage()); 
    }
    catch (NullPointerException e) { 
        System.err.println("Error: " + e.getMessage()); 
    }
}
}   
