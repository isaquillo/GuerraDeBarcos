
package guerradebarcos;

import java.util.Random;
import java.util.Scanner;

public class TableroBarcos {
    private final int FILA;
    private final int COLUMNA;
    private final int CANTIDAD_BARCOS;
    private final int JUGADOR_UNO;
    private final int JUGADOR_DOS;
    private int[][] tablero;
    private boolean[][] casillasJugadas;
    private boolean hayGanador;
    private int jugadorActivo;
    private int jugadorEnEspera;
    private int fila;
    private int columna;
    private int contadorJugadorUno;
    private int contadorJugadorDos;
    private Scanner reader;
    
    // Constructor
    public TableroBarcos(){
        this.FILA = 5;
        this.COLUMNA = 5;
        this.CANTIDAD_BARCOS = 5;
        this.JUGADOR_UNO = 1;
        this.JUGADOR_DOS = 2;
        this.tablero = new int[FILA][COLUMNA];
        this.casillasJugadas = new boolean[FILA][COLUMNA];
        this.jugadorActivo = JUGADOR_UNO;
        this.jugadorEnEspera = JUGADOR_DOS;
        this.contadorJugadorUno = CANTIDAD_BARCOS;
        this.contadorJugadorDos = CANTIDAD_BARCOS;
        this.reader = new Scanner(System.in);
    }
    
    
    // Método que inicializa el tablero colocando los barcos de los jugadores
    private void iniciaTablero(){
        hayGanador = false;
        Random aleatorio = new Random(System.currentTimeMillis());
        for (fila = 0; fila < FILA; fila++) {
            for (columna = 0; columna < COLUMNA; columna++) {
                tablero[fila][columna] = 0;
                casillasJugadas[fila][columna] = false;
            }
        }
        // Coloca los barcos de los jugadores en el tablero
        for (int i = 0; i < CANTIDAD_BARCOS; i++) {
            fila = aleatorio.nextInt(5);
            columna = aleatorio.nextInt(5);
            
            while(tablero[fila][columna] != 0) {
            	fila = aleatorio.nextInt(5);
                columna = aleatorio.nextInt(5);
            }
        
            tablero[fila][columna] = JUGADOR_UNO;
            while(tablero[fila][columna] != 0) {
            	fila = aleatorio.nextInt(5);
                columna = aleatorio.nextInt(5);
            }
            
            tablero[fila][columna] = JUGADOR_DOS;
        }
    }
    
    // Método que ejecuta cada turno en el juego
    private void juegaTurno(){
        while(!hayGanador){
            try {
                System.out.println("Es el turno del Jugador: "+ jugadorActivo + "\n"+
                    "Elige una posición en el tablero...");
                System.out.print("Elige la fila (1-5): ");                
                fila = reader.nextInt() - 1;
                System.out.print("Elige la columna (1-5): ");
                columna = reader.nextInt() - 1;
                if((fila >= 0 && fila < FILA) && (columna >= 0 && columna < COLUMNA)){
                    if(casillasJugadas[fila][columna] == false){
                        casillasJugadas[fila][columna] = true;
                        if(tablero[fila][columna] != 0){
                            if(tablero[fila][columna] != jugadorActivo){
                                tablero[fila][columna] = 0;
                                if(jugadorEnEspera == JUGADOR_UNO){
                                    contadorJugadorUno--;
                                }
                                else{
                                    contadorJugadorDos--;
                                }
                                System.out.println("Barco hundido!!!\n\n");
                                hayGanador = evaluaTablero();
                            }
                            else{
                                System.out.println("La posición dada corresponde a un barco propio!!!\n\n");
                            }
                        }
                        else{
                            System.out.println("Proyectil dio en el agua!!!\n");
                            cambiaJugador();
                        }                     
                        imprimeEstadoJuego();                                              
                    }
                    else{
                        System.out.println("La posición ya ha sido utilizada. Elige otra...\n");
                    }
            
                }
                else{
                    System.out.println("La posición dada está fuera de rango...\n\n");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
        
    }
    
    // Método que evalúa el estado del tablero después de cada turno
    private boolean evaluaTablero(){
        boolean encontrado = false;
        for (fila = 0; fila < FILA; fila++) {
            for (columna = 0; columna < COLUMNA; columna++) {
                if(jugadorEnEspera == tablero[fila][columna])
                   encontrado = true;
            }
        }
        if(!encontrado){
            hayGanador = true;
            System.out.println("Gana el jugador: " + jugadorActivo + "\n\n");
        }
        return hayGanador;
    }
    
    // Método que controla los turnos de los jugadores
    private void cambiaJugador(){
        if(jugadorActivo == JUGADOR_UNO){
            jugadorActivo = JUGADOR_DOS;
            jugadorEnEspera = JUGADOR_UNO; 
        }
        else{
            jugadorActivo = JUGADOR_UNO;
            jugadorEnEspera = JUGADOR_DOS;
        }
    }
    
    private void imprimeEstadoJuego(){
        System.out.println("Barcos del jugador UNO: " + contadorJugadorUno + "\n" +
                           "Barcos del jugador DOS: " + contadorJugadorDos + "\n\n");
        for (fila = 1; fila <= FILA; fila++) {
            for (columna = 1; columna <= COLUMNA; columna++) {
                System.out.print(tablero[fila-1][columna-1] + " ");
                if(columna % COLUMNA == 0){
                    System.out.println("\n");
                }                
            }
            
        }
    }
    
    // Método que da comienzo al juego
    public void juegaBatalla(){
        boolean salir = false;
        int opcion;
        while(!salir){
            try {                
                System.out.println("GUERRA DE BARCOS\n\n"+
                        "1) Jugar partida\n"+
                        "2) Salir del juego\n");
                System.out.print("Elige una opcion: ");
                opcion = reader.nextInt();
                
                switch(opcion){
                    case 1: iniciaTablero();
                            juegaTurno();
                            break;
                    case 2: salir = true;
                            break;
                    default: System.out.println("Opcion inexistente. Vuelve a elegir\n\n");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
    }
}
