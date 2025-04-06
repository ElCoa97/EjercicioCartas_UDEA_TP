import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmJuego extends JFrame {

    private JButton btnRepartir;
    private JButton btnVerificar;
    private JPanel pnlJugador1;
    private JPanel pnlJugador2;
    private JTabbedPane tpJugadores;
    private JButton btnAnalizar;
    private JLabel lblPuntosJ1, lblPuntosJ2;
    private JButton btnGanador;

    public FrmJuego() {
        btnRepartir = new JButton();
        btnVerificar = new JButton();
        btnVerificar.setEnabled(false);
        tpJugadores = new JTabbedPane();
        pnlJugador1 = new JPanel();
        pnlJugador2 = new JPanel();
        btnAnalizar = new JButton();
        btnAnalizar.setEnabled(false);
        lblPuntosJ1 = new JLabel();
        lblPuntosJ2 = new JLabel();
        btnGanador = new JButton();
        btnGanador.setEnabled(false);

        setSize(600, 300);
        setTitle("Juego de Cartas");
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/IconoPintas.jpg")).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pnlJugador1.setBackground(new Color(153, 255, 51));
        pnlJugador1.setLayout(null);
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        tpJugadores.setBounds(10, 40, 550, 170);
        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raul Vidal", pnlJugador2);

        btnRepartir.setBounds(10, 10, 100, 25);
        btnRepartir.setText("Repartir");
        btnRepartir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRepartirClick(evt);
            }
        });

        btnVerificar.setBounds(120, 10, 100, 25);
        btnVerificar.setText("Verificar");
        btnVerificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnVerificarClick(evt);
            }
        });

        btnAnalizar.setBounds(230, 10, 100, 25);
        btnAnalizar.setText("Analizar");
        btnAnalizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAnalizarClick(evt);
            }
        });

        btnGanador.setBounds(340, 10, 100, 25);
        btnGanador.setText("Ganador");
        btnGanador.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                btnGanadorClick(evt);

            }
        });

        lblPuntosJ1.setBounds(10, 220, 250, 25);
        lblPuntosJ2.setBounds(300, 220, 250, 25);

        getContentPane().setLayout(null);
        getContentPane().add(tpJugadores);
        getContentPane().add(btnRepartir);
        getContentPane().add(btnVerificar);
        getContentPane().add(btnAnalizar);
        getContentPane().add(lblPuntosJ1);
        getContentPane().add(lblPuntosJ2);
        getContentPane().add(btnGanador);

    }

    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();

    private void btnRepartirClick(ActionEvent evt) {
        lblPuntosJ1.setText("");
        jugador1.repartir();
        jugador1.mostrar(pnlJugador1);

        lblPuntosJ2.setText("");
        jugador2.repartir();
        jugador2.mostrar(pnlJugador2);

        habilitarbtnGanador();

        if (btnAnalizar.isEnabled() && btnVerificar.isEnabled()){
        }else{
            btnAnalizar.setEnabled(true);
            btnVerificar.setEnabled(true);
        }
    }

    private void btnVerificarClick(ActionEvent evt) {
        switch (tpJugadores.getSelectedIndex()) {
            case 0:
                JOptionPane.showMessageDialog(null, jugador1.getGrupos());
                break;
            case 1:
                JOptionPane.showMessageDialog(null, jugador2.getGrupos());
                break;
        }
    }

    private void btnAnalizarClick(ActionEvent evt) {
        switch (tpJugadores.getSelectedIndex()) {
            case 0:
                JOptionPane.showMessageDialog(null, jugador1.getEscaleras());
                lblPuntosJ1.setText("Puntos al final J1: " + jugador1.getPuntosFinal());
                break;
            case 1:
                JOptionPane.showMessageDialog(null, jugador2.getEscaleras());
                lblPuntosJ2.setText("Puntos al final J2: " + jugador2.getPuntosFinal());
                break;
        }
        habilitarbtnGanador();
        
    }
 
    private void btnGanadorClick(ActionEvent evt) {
        int puntosJ1 = jugador1.getPuntosFinal();
        int puntosJ2 = jugador2.getPuntosFinal();

        String mensaje = "COMPARACION DE PUNTAJES:\n";
        mensaje += "\nJugador 1: " + puntosJ1 + " puntos\n";
        mensaje += "Jugador 2: " + puntosJ2 + " puntos\n\n";

        if(puntosJ1 < puntosJ2){
            mensaje += "Jugador 1 tiene menor puntaje. !Ganador¡";
        }else if (puntosJ2 < puntosJ1) {
            mensaje += "Jugador 2 tiene menor puntaje. !Ganador¡";
        }else{
            mensaje += "!Empate¡";
        }
        JOptionPane.showMessageDialog(null,mensaje);
    }

    private void habilitarbtnGanador(){
        if(!lblPuntosJ1.getText().isEmpty() && !lblPuntosJ2.getText().isEmpty()){
            btnGanador.setEnabled(true);
        }else{
            btnGanador.setEnabled(false);
        }
    }

}