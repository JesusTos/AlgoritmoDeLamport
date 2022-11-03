import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class gui extends JFrame {
    public JPanel panel_uno, panel_dos, panel_tres, panel_cuatro, panel_cinco;
    public JLabel etiqueta_uno, etiqueta_dos, etiqueta_tres;
    public TextArea a1, a2, a3, a4, a5;

    public gui() {
        super("Lamport");
        Container contenedor = getContentPane();
        contenedor.setLayout(new BorderLayout());
        etiqueta_uno = new JLabel("Proceso 1");
        etiqueta_dos = new JLabel("Proceso 2");
        etiqueta_tres = new JLabel("Proceso 3");
        a1 = new TextArea(15, 5);
        a1.setEditable(false); // para q no se pueda editar el area de texto
        a2 = new TextArea(15, 5);
        a2.setEditable(false); // para q no se pueda editar el area de texto
        a3 = new TextArea(15, 5);
        a3.setEditable(false); // para q no se pueda editar el area de texto
        a4 = new TextArea(10, 60);
        a4.setEditable(false); // para q no se pueda editar el area de texto

        panel_uno = new JPanel(new FlowLayout());
        panel_dos = new JPanel(new FlowLayout());
        panel_tres = new JPanel(new FlowLayout());
        panel_cuatro = new JPanel(new FlowLayout());
        panel_uno.add(etiqueta_uno);
        panel_uno.add(a1);
        panel_dos.add(etiqueta_dos);
        panel_dos.add(a2);
        panel_tres.add(etiqueta_tres);
        panel_tres.add(a3);
        panel_cuatro.add(new JLabel("Detalles:"));
        panel_cuatro.add(a4);
        setSize(800, 800);
        setVisible(true);

        // centramos la pantalla
        // obtenemos la dimension de la pantalla de cliente
        Dimension dim_pantalla = Toolkit.getDefaultToolkit().getScreenSize();

        // Una cuenta para situar la ventana en el centro de la pantalla.
        setLocation((dim_pantalla.width - this.getWidth()) / 2,
                (dim_pantalla.height - this.getHeight()) / 2);

        contenedor.add(panel_uno, BorderLayout.WEST);
        contenedor.add(panel_dos, BorderLayout.CENTER);
        contenedor.add(panel_tres, BorderLayout.EAST);
        contenedor.add(panel_cuatro, BorderLayout.SOUTH);
        setResizable(false);

    }
}

class lamport {
    public int proc1[], proc2[], proc3[], i, v1, v2, v3, seg1, seg2, seg3;
    public gui en;

    public lamport() {
        en = new gui();
        en.setDefaultCloseOperation(en.EXIT_ON_CLOSE);
        seg1 = 0;
        seg2 = 0;
        seg3 = 0;
        v1 = 0;
        v2 = 0;
        v3 = 0;
        i = 0;
        proc1 = new int[10];
        proc2 = new int[10];
        proc3 = new int[10];

    }

    public void p1(int segg) {
        seg1 = segg;
        proc1[0] = 0;
        for (i = 1; i <= 9; i++) {
            proc1[i] = proc1[i - 1] + seg1;
            if (i % 4 == 0 && i != 0) {
                sincronizacion();
            }
        }

        i = 0;
        for (i = 0; i <= 9; i++) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            String xx = null;
            xx = String.valueOf(proc1[i]);
            en.a1.append(xx + "\n");
            v1 = proc1[i];
        }
        i = 0;
    }

    public void p2(int segg) {
        seg2 = segg;
        proc2[0] = 0;
        for (i = 1; i <= 9; i++) {
            proc2[i] = proc2[i - 1] + seg2;
            if (i % 6 == 0 && i != 0) {
                sincronizacion2();
            }
        }
        i = 0;
        for (i = 0; i <= 9; i++)// while(i<=9)
        {
            try {
                Thread.sleep(1200);
            } catch (Exception e) {
            }
            String xx = null;
            xx = String.valueOf(proc2[i]);
            en.a2.append(xx + "\n");
            v2 = proc2[i];
        }
        i = 0;
    }

    public void p3(int segg) {
        seg3 = segg;
        proc3[0] = 0;
        for (i = 1; i <= 9; i++) {
            proc3[i] = proc3[i - 1] + seg3;
        }
        i = 0;
        for (i = 0; i <= 9; i++)// while(i<=9)
        {
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
            }
            String xx = null;
            xx = String.valueOf(proc3[i]);
            en.a3.append(xx + "\n");
            v3 = proc1[i];
        }
        i = 0;
    }

    public void sincronizacion() {
        int j = i;
        j--;
        System.out.println("j " + j);
        System.out.println("v1 " + v1);
        System.out.println("v2 " + v2);

        if (v1 < v2) {
            en.a4.append("Mande un mensaje de P1 a P2\n");
            en.a4.append("Mande un mensaje de P2 a P1\n");
            en.a4.append("sincronizando\n");
            System.out.println("entre");
            en.a4.append("cambiando: ");
            String cad = String.valueOf(proc1[j + 1]);
            en.a4.append(cad);
            en.a4.append(" por: ");
            proc1[j + 1] = proc2[j];
            proc1[j + 1] += 1;
            System.out.println(proc1[j + 1]);
            String cad2 = String.valueOf(proc1[j + 1]);
            en.a4.append(cad2);
            en.a4.append("\n");
            en.a4.append("sincronizacion entre P1 y P2 Terminada!!!\n");

        }
    }

    public void sincronizacion2() {
        int k = i;
        k--;
        System.out.println("k " + k);
        System.out.println("v2 " + v2);
        System.out.println("v3 " + v3);
        if (v2 <= v3) {
            en.a4.append("Mande un mensaje de P2 a P3\n");
            en.a4.append("Mande un mensaje de P3 a P2\n");
            en.a4.append("sincronizando\n");
            System.out.println("entre");
            en.a4.append("cambiando: ");
            String cad = String.valueOf(proc2[k + 1]);
            en.a4.append(cad);
            en.a4.append(" por: ");
            proc2[k + 1] = proc3[k];
            proc2[k + 1] += 1;
            System.out.println(proc2[k + 1]);
            String cad2 = String.valueOf(proc2[k + 1]);
            en.a4.append(cad2);
            en.a4.append("\n");
            en.a4.append("sincronizacion entre P2 y P3 Terminada!!!\n");
        }
    }

}

class corrible {
    public static void main(String[] args) {
        lamport l = new lamport();
        l.p3(1);
        l.p2(11);
        l.p1(21);
    }
}