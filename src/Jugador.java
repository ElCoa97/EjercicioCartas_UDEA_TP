import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;
    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private Carta[] cartasEnEscalera = new Carta[TOTAL_CARTAS];
    private Carta[] cartasRestantes = new Carta[0];
    private Carta[] cartasEnGrupo = new Carta[TOTAL_CARTAS];
    private Carta[] cartasSinNada = new Carta[0];
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

    public String getEscalerasYGrupos() {
        int totalEscaleras = 0;
        boolean[] usadaEnEscalera = new boolean[TOTAL_CARTAS];
        boolean[] usadaEnGrupo = new boolean[TOTAL_CARTAS];

        // Para cada pinta
        for (Pinta pinta : Pinta.values()) {
            // Extraer las cartas de esa pinta
            Carta[] porPinta = new Carta[TOTAL_CARTAS];
            int count = 0;

            for (int i = 0; i < TOTAL_CARTAS; i++) {
                if (cartas[i].getPinta() == pinta) {
                    porPinta[count++] = cartas[i];
                }
            }

            // Ordenar por valor
            for (int i = 0; i < count - 1; i++) {
                for (int j = 0; j < count - i - 1; j++) {
                    if (porPinta[j].getNombre().ordinal() > porPinta[j + 1].getNombre().ordinal()) {
                        Carta temp = porPinta[j];
                        porPinta[j] = porPinta[j + 1];
                        porPinta[j + 1] = temp;
                    }
                }
            }

            // Buscar escaleras consecutivas
            int i = 0;
            while (i < count - 1) {
                int inicio = i;
                while (i < count - 1
                        && porPinta[i + 1].getNombre().ordinal() == porPinta[i].getNombre().ordinal() + 1) {
                    i++;
                }

                int longitud = i - inicio + 1;

                if (longitud >= 2) {
                    // Guardar las cartas de la escalera
                    for (int j = inicio; j <= i; j++) {
                        for (int k = 0; k < TOTAL_CARTAS; k++) {
                            if (cartas[k] == porPinta[j]) {
                                usadaEnEscalera[k] = true;
                                cartasEnEscalera[totalEscaleras++] = cartas[k];
                                break;
                            }
                        }
                    }
                }
                i++;
            }
        }

        // Crear cartasRestantes
        cartasRestantes = new Carta[TOTAL_CARTAS - totalEscaleras];
        int idx = 0;
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (!usadaEnEscalera[i]) {
                cartasRestantes[idx++] = cartas[i];
            }
        }

        // Contar grupos
        int[] contadores = new int[NombreCarta.values().length];
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (!usadaEnEscalera[i]) {
                contadores[cartas[i].getNombre().ordinal()]++;
            }
        }

        // Marcar cartas que pertenecen a grupos
        int totalGrupos = 0;
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] > 1) {
                for (int j = 0; j < TOTAL_CARTAS; j++) {
                    if (!usadaEnEscalera[j] && cartas[j].getNombre().ordinal() == i) {
                        cartasEnGrupo[totalGrupos++] = cartas[j];
                        usadaEnGrupo[j] = true;
                    }
                }
            }
        }

        // Cartas que no estan en nada
        int totalSinNada = 0;
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (!usadaEnEscalera[i] && !usadaEnGrupo[i]) {
                totalSinNada++;
            }
        }
        cartasSinNada = new Carta[totalSinNada];
        int contador = 0;
        for (int i = 0; i < TOTAL_CARTAS; i++) {
            if (!usadaEnEscalera[i] && !usadaEnGrupo[i]) {
                cartasSinNada[contador++] = cartas[i];
            }
        }
        String mensaje = "";

        // Mostrar escaleras
        if (totalEscaleras > 0) {
            mensaje += "SE ENCONTRARON LAS SIGUIENTES ESCALERAS:\n";

            for (Pinta pinta : Pinta.values()) {
                String nPinta = pinta + ": ";
                boolean hayCartas = false;

                for (int i = 0; i < totalEscaleras; i++) {
                    if (cartasEnEscalera[i].getPinta() == pinta) {
                        if (hayCartas)
                            nPinta += ", ";
                        nPinta += cartasEnEscalera[i].getNombre();
                        hayCartas = true;
                    }
                }

                if (hayCartas) {
                    mensaje += "*" + nPinta + "\n";
                }
            }
        } else {
            mensaje += "NO SE ENCONTRARON ESCALERAS.\n";
        }

        // Mostrar grupos
        boolean hayGrupos = false;
        for (int c : contadores) {
            if (c > 1) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje += "\nSE ENCONTRARON LOS SIGUIENTES GRUPOS:\n";
            for (int i = 0; i < contadores.length; i++) {
                int cantidad = contadores[i];
                if (cantidad > 1) {
                    mensaje += "*" + Grupo.values()[cantidad] + " de " + NombreCarta.values()[i] + "\n";
                }
            }
        } else {
            mensaje += "\nNO SE ENCONTRARON GRUPOS RESTANTES.";
        }
        return mensaje;
    }

    public int getPuntaje() {
        int suma = 0;
        for (Carta c : cartasSinNada) {
            if (c != null) {
                suma += c.valorCarta();
            }
        }
        return suma;
    }
}
