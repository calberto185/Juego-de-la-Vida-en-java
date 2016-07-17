package juegodelavida;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javafx.scene.layout.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class JuegoVida extends Thread implements Runnable {

    private JFrame frame;
    private JPanel main, tablero, info, control;
    private int[][] contenedor;
    private int[][] tableroActual;
    private int[][] tableroFuturo;
    private int[][] auxiliar;
    private JButton[][] botones;
    private JLabel texto1, texto2, texto3, texto4;
    private JTextField x, y, tiempo;
    private int filas;
    private int columnas;
    private int milisegundos;

    private GridBagLayout layout;

    public JuegoVida() {
        System.out.println(ColorRandom());
        Dialog();
    }

    public JuegoVida(int filas, int columnas, int milisegundos) {
        System.out.println(ColorRandom());
        this.filas = filas;
        this.columnas = columnas;
        this.milisegundos = milisegundos;
        construyePanelInferior();
        construyeVentana(1600, 750);
        empiezaJuego();
    }

    private void start(int filas, int columnas, int milisegundos){
        System.out.println(ColorRandom());
        this.filas = filas;
        this.columnas = columnas;
        this.milisegundos = milisegundos;
        construyePanelInferior();
        construyeVentana(1600, 750);
        empiezaJuego();
    
    }
    private void Dialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Menu de Control");
//        dialog.setDefaultCloseOperation(dialog.EXIT_ON_CLOSE);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(null);
        JPanel vista=new JPanel();
        GridBagLayout layout = new GridBagLayout();
        vista.setLayout(new GridLayout(8, 2));
        vista.setBackground(Color(ColorRandom()));
        JButton procesar = new JButton("INICIAR");
        procesar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                filas = Integer.parseInt(x.getText().trim());
                columnas =Integer.parseInt(y.getText().trim());
                milisegundos =Integer.parseInt(tiempo.getText().trim());
//                  start(filas, columnas, milisegundos);
                  new JuegoVida(filas, columnas, milisegundos);
            }
        });
        vista.add(new JLabel("Dimension en X"));
        x = new JTextField();
        vista.add(x);
        vista.add(new JLabel("Dimension en Y"));
        y = new JTextField();
        vista.add(y);
        vista.add(new JLabel("Tiempo por generacion"));
        tiempo = new JTextField();
        vista.add(tiempo);
        vista.add(procesar);
        dialog.add(vista);
        dialog.setVisible(true);
    }

    private void construyeVentana(int width, int heigth) {
        frame = new JFrame("Juego de a vida");
        frame.setSize(width, heigth);
        MainPanel();
        frame.add(main);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void MainPanel() {
        main = new JPanel();
        layout = new GridBagLayout();
        main.setLayout(layout);
        tableroPanel();
        controlPanel();
        infoPanel();
    }

    private void tableroPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 3;
        gbc.weighty = 3;
        layout.setConstraints(tablero, gbc);
        main.add(tablero);
    }

    private void controlPanel() {
        control = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 2;
        layout.setConstraints(control, gbc);
        main.add(control);
        botonControl();
    }

    private void infoPanel() {
        info = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        layout.setConstraints(info, gbc);
        main.add(info);
        labelInfo();
    }

    private void botonControl() {
        control.setLayout(new GridLayout(6, 2));
        JButton Boton1 = new JButton("Iniciar");
        Boton1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                System.out.println("click");
                empiezaJuego();
            }
        });
        control.add(Boton1);
        control.add(new JButton("Parar"));
        control.add(new JButton("Nuevos"));
        control.add(new JButton("Color"));
        control.add(new JButton("boton 5"));
        control.add(new JButton("boton 6"));
    }

    private void labelInfo() {
        info.setLayout(new GridLayout(1, 2));
        info.setBackground(Color.yellow);

        texto1 = new JLabel("Individuos:  0", SwingConstants.CENTER);
        texto1.setHorizontalTextPosition(JLabel.CENTER);
        texto1.setVerticalTextPosition(JLabel.CENTER);
        info.add(texto1);
        texto2 = new JLabel("Vivos:  0", SwingConstants.CENTER);
        texto2.setHorizontalTextPosition(JLabel.CENTER);
        texto2.setVerticalTextPosition(JLabel.CENTER);
        info.add(texto2);
        texto3 = new JLabel("Tiempo:  0", SwingConstants.CENTER);
        texto3.setHorizontalTextPosition(JLabel.CENTER);
        texto3.setVerticalTextPosition(JLabel.CENTER);
        info.add(texto3);
    }

    private void construyePanelInferior() {
        botones = new JButton[filas][columnas];
        tablero = new JPanel();
        tablero.setBackground(Color.black);
        tablero.setLayout(new GridLayout(filas, columnas));

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton boton = new JButton();
                String id = String.valueOf(i) + "-" + String.valueOf(j);
                boton.setName(id);
                boton.setToolTipText(id);
                boton.setBackground(Color.black);
                botones[i][j] = boton;
            }
        }

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero.add(botones[i][j]);
            }
        }

    }

    public void empiezaJuego() {
        contenedor = new int[3][3];
        tableroActual = new int[filas][columnas];
        tableroFuturo = new int[filas][columnas];
        auxiliar = new int[filas][columnas];

        inicializaTableros(tableroActual);
        iniciaVida();
        maquillaTablero();
        pintaTablero(tableroActual);
        int t = 0;
        do {
            for (int i = 0; i < filas - 2; i++) {
                for (int j = 0; j < columnas - 2; j++) {

                    contenedor[0][0] = tableroActual[(i)][(j)];
                    contenedor[0][1] = tableroActual[(i)][(j + 1)];
                    contenedor[0][2] = tableroActual[(i)][(j + 2)];

                    contenedor[1][0] = tableroActual[(i + 1)][(j)];
                    contenedor[1][1] = tableroActual[(i + 1)][(j + 1)];
                    contenedor[1][2] = tableroActual[(i + 1)][(j + 2)];

                    contenedor[2][0] = tableroActual[(i + 2)][(j)];
                    contenedor[2][1] = tableroActual[(i + 2)][(j + 1)];
                    contenedor[2][2] = tableroActual[(i + 2)][(j + 2)];

                    int contador = 0;
                    int k = 0;
                    int l = 0;

                    for (k = 0; k < 3; k++) {
                        for (l = 0; l < 3; l++) {
                            if (!(k == 1 && l == 1)) {
                                if (contenedor[k][l] == 1) {
                                    contador++;
                                }
                            }

                        }
                    }

                    if (contador < 2 && contenedor[1][1] == 1) {
                        tableroFuturo[(i + 1)][(j + 1)] = 0;
                    } else if (contador > 3 && contenedor[1][1] == 1) {
                        tableroFuturo[(i + 1)][(j + 1)] = 0;
                    } else if (contador == 3 && contenedor[1][1] == 0) {
                        tableroFuturo[(i + 1)][(j + 1)] = 1;
                    } else if (contador == 3 && contenedor[1][1] == 1) {
                        tableroFuturo[(i + 1)][(j + 1)] = 1;
                    } else if (contador == 2) {
                        tableroFuturo[(i + 1)][(j + 1)] = tableroActual[(i + 1)][(j + 1)];
                    }

                }

            }

            auxiliar = tableroActual;
            tableroActual = tableroFuturo;
            tableroFuturo = auxiliar;

            try {
                t++;
                texto3.setText("Tiempo:  " + String.valueOf(t) + "  seg.");
                Thread.currentThread().sleep(milisegundos);
            } catch (InterruptedException ie) {
                System.out.println("Exception " + ie.toString());
            }

            pintaTablero(tableroFuturo);
            inicializaTableros(tableroFuturo);

        } while (true);

    }

    private void pintaTablero(int[][] tabla) {
        int d = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (tabla[i][j] == 1) {
                    d++;
                    botones[i][j].setBackground(Color(ColorRandom()));
//                    botones[i][j].setBackground(Color.green);
                    texto2.setText("Vivos:  " + String.valueOf(d));
                }

                if (tabla[i][j] == 0) {
                    botones[i][j].setBackground(Color.black);
                    texto1.setText("Individuos:  " + String.valueOf(filas * columnas - d));
                }

            }
        }
    }

    private void maquillaTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                botones[i][j].setBackground(Color.green);
            }
        }

    }

    private void inicializaTableros(int[][] tablero) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = 0;
            }
        }
    }

    public void iniciaVida() {
        for (int j = 0; j < 800; j++) {
            int y = (int) Math.floor(Math.random() * (0 - filas + 1) + filas);
            int x = (int) Math.floor(Math.random() * (0 - columnas + 1) + columnas);
            tableroActual[y][x] = 1;
        }

    }

    public String ColorRandom() {
        String color = "";
        for (int i = 0; i < 3; i++) {
            color += String.valueOf((int) Math.floor(Math.random() * (0 - 254 + 1) + 254)) + ",";
        }
        return color;
    }

    public java.awt.Color Color(String color) {
        String c[] = color.split(",");
        return new java.awt.Color(
                Integer.parseInt(c[0]),
                Integer.parseInt(c[1]),
                Integer.parseInt(c[2]));
    }

    public static void main(String[] args) {
//        new JuegoVida(40,40,120);
        new JuegoVida();
    }

}
