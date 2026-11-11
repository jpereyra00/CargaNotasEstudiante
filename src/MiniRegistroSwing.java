import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MiniRegistroSwing {
    private static final String ARCHIVO= "estudiantes.txt";
    private JTextArea areaResultado;
    private JTextField campoNombre;
    private JTextField campoNota;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> new MiniRegistroSwing().crearVentana());

    }
    private void crearVentana(){
        JFrame ventana = new JFrame("Mini Registro de estudiantes Swing");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(520,420);
        ventana.setLocationRelativeTo(null);
        //panel de arriba
        JPanel panelDeEntrada = new JPanel();
        campoNombre = new JTextField(12);
        campoNota = new JTextField(4);

        JButton botonGuardar = new JButton("Guardar");
        JButton botonMostrar = new JButton("Mostrar");
        JButton botonHilos= new JButton("demostrar Hilos");
        //necesitamos agregar los componentes --> izq a der
        panelDeEntrada.add(new JLabel("Nombre:"));
        panelDeEntrada.add(campoNombre);
        panelDeEntrada.add(new JLabel("Nota:"));
        panelDeEntrada.add(campoNota);
        panelDeEntrada.add(botonGuardar);
        panelDeEntrada.add(botonMostrar);
        panelDeEntrada.add(botonHilos);

        //el area de resultados
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultado);

        //llega el momento de armar el rompecabezas
        ventana.add(panelDeEntrada,"North");
        ventana.add(scroll,"Center");
        //conectar los botones con sus acciones
        botonGuardar.addActionListener(e -> guardarEstudiante());
        botonMostrar.addActionListener(e -> mostrarArchivo());
        botonHilos.addActionListener(e -> demostrarHilos());
        ventana.setVisible(true);
        mostrar("===========MINI REGISTRO DE ESTUDIANTES==========");
        mostrar("Escribi un nombre y una nota y presiona sobre guardar");
    }
    private void guardarEstudiante() {
        String nombre = campoNombre.getText().trim();
        String nota = campoNota.getText().trim();
        if (nombre.isEmpty() || nota.isEmpty()) {
            mostrar(">> faltan datos: completa por favor nombre y nota");
            return;
        }
        try (FileWriter escritor = new FileWriter(ARCHIVO)) {
            escritor.write(nombre + " - " + nota + "\n");
            mostrar(">> Guardado: " + nombre + " " + nota + " ");
        } catch (IOException e) {
            mostrar(">>error al guardar, no se pudo leer el archivo");
        }
        campoNombre.setText("");
        campoNota.setText("");
        campoNombre.requestFocus();
    }
    private void mostrarArchivo() {
        System.out.println("\n --- Contenido del archivo ---");
        try(BufferedReader lector = new BufferedReader(new InputStreamReader(new FileInputStream(ARCHIVO)))){
            //leer linea por linea
            String linea = lector.readLine();
            while(linea != null){
                System.out.println(" "+linea);
                linea= lector.readLine();
            }

        }catch (IOException e){
            System.out.println(">> ERROR: no se pudo leer en el archivo.");

        }
    }
    public static void demostrarHilos(){
        System.out.println("\n--- Demostracion de hilos -- ");
        System.out.println("--- Hilo Principal:  ---"+Thread.currentThread().getName());
        //1) crear la tarea (el que) y darle a "el quien"
        TareaDeFondo tarea = new TareaDeFondo();
        Thread hilo = new Thread(tarea);
        // 2) llamada directa
        System.out.println( "\n1) llamando al run ()-> mismo hilo secuencial: ");
        hilo.run();
        //3) pedirle a java que cree un hilo nuevo
        System.out.println("n2) llamando a start --> hilo nuevo (paralelo): ");
        hilo.start();
        //4) ejecutar un join para poder esperar que el hilo termine
        try{
            hilo.join();
        }catch(InterruptedException e){
            System.out.println(" el hilo fue interrumpido");
        }
    }
    private void mostrar(String texto){
        areaResultado.append(texto + "\n");
        areaResultado.setCaretPosition(areaResultado.getDocument().getLength());
    }


}
