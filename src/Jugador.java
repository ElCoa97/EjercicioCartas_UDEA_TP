import java.util.Random;

import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;
    private int puntosFinal = 0;
    private int sumaEscaleras = 0;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    private Random r = new Random(); // la suerte del jugador

    public void repartir() {
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicion = MARGEN + (TOTAL_CARTAS - 1) * DISTANCIA;
        for (Carta carta : cartas) {
            carta.mostrar(pnl, posicion, MARGEN);
            posicion -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron figuras";
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;
        for (int contador : contadores) {
            if (contador > 1) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje = "Se encontraron los siguientes grupos:\n";
            int fila = 0;
            for (int contador : contadores) {
                if (contador > 1) {
                    mensaje += Grupo.values()[contador] + " de " + NombreCarta.values()[fila] + "\n";
                }
                fila++;
            }
        }

        return mensaje;
    }

    public int getPuntos() {
        int total = 0;
        for (Carta c : cartas) {
            total += c.valorCarta();
        }
        return total;
    }

    public String getEscaleras() {
        Carta[] treboles = new Carta[10];
        Carta[] picas = new Carta[10];
        Carta[] corazones = new Carta[10];
        Carta[] diamante = new Carta[10];

        int tre = 0, pic = 0, cor = 0, dia = 0;
        sumaEscaleras = 0;

        for (Carta carta : cartas) {
            switch (carta.getPinta()) {
                case TREBOL:
                    treboles[tre++] = carta;
                    break;
                case PICA:
                    picas[pic++] = carta;
                    break;
                case CORAZON:
                    corazones[cor++] = carta;
                    break;
                case DIAMANTE:
                    diamante[dia++] = carta;
                    break;
            }
        }

        String resultado = "";
        
        resultado += revisarEscaleras(treboles, tre, "TREBOL");
        resultado += revisarEscaleras(picas, pic, "PICA");
        resultado += revisarEscaleras(corazones, cor, "CORAZON");
        resultado += revisarEscaleras(diamante, dia, "DIAMANTE");

        puntosFinal = getPuntos() - sumaEscaleras;
        
        if (resultado.isEmpty()) {
            return "NO HAY ESCALERAS\nPUNTAJE AL FINALIZAR: "+ puntosFinal;
        }else{
            return resultado + "\n\nPUNTOS AL FINALIZAR: " + puntosFinal;
        }
    }

    public String revisarEscaleras(Carta[] arreglo, int cantidad, String nombrePinta){
        Carta[] cartasValidas = new Carta[cantidad];
        for (int i = 0; i< cantidad; i++){
            cartasValidas[i] = arreglo[i];
        }

        java.util.Arrays.sort(cartasValidas, (a,b) -> a.getNombre().ordinal() - b.getNombre().ordinal());

        String mensaje = "";
        int i = 0;

        while (i< cantidad -1){
            int inicio = i;

            while (i<cantidad-1 && cartasValidas[i+1].getNombre().ordinal() == cartasValidas[i].getNombre().ordinal()+1) {
                i++;                
            }

            int longitud = i - inicio +1;

            if(longitud >= 2){
                int puntosEscalera = 0;
                mensaje += "ESCALERAS:\n"+ longitud + " CARTAS DE "+ nombrePinta + ": ";
                for (int j = inicio; j<= i; j++){
                    mensaje += cartasValidas[j].getNombre()+", ";
                    puntosEscalera += cartasValidas[j].valorCarta();
                }
                sumaEscaleras = sumaEscaleras + puntosEscalera;;
            }

            i++;
        }

        return mensaje;
        
    }

    public int getPuntosFinal(){
        return puntosFinal;
    }

    

}
