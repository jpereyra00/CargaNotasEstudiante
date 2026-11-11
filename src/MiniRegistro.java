import java.io.*;
import java.util.Scanner;

public class MiniRegistro {
    //tener el archivo
    private static final String ARCHIVO= "estudiantes.txt";
    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        System.out.println("=========== MINI REGISTRO DE ESTUDIANTES=======");
        //entada y salida + archivo de escrito
        guardarEstudiantes(scanner);
        //lectura del archivo que acabamos de crear
        mostrarEstudiantes();
        //mostar los hilos
        demostrarHilos();
        //buena practica, cerrar al terminar de usar.
        scanner.close();
        System.out.println("\nPrograma finalizado con exito. ¡Gracias!");
    }
    public static void guardarEstudiantes(Scanner scanner){
        System.out.print("\n ¿Cuantos estudiantes vas a cargar?");
        int cantidad = scanner.nextInt();
        scanner.nextLine();
        try(FileWriter escritor= new  FileWriter(ARCHIVO)){
            for(int i=1; i<= cantidad;i++){
                System.out.print("Nombre del estudiante: "+i+" : ");
                String nombre = scanner.nextLine();
                System.out.print("Nota de: "+nombre+" : ");
                String nota = scanner.nextLine();
                escritor.write(nombre+" - "+nota +"\n");
                System.out.println(">> Datos guardados con exito en el archivo: "+ARCHIVO+ " .");
            }
        }catch(IOException e){
            System.out.println(">> ERROR: no se pudo escribir en el archivo.");
        }
    }
    public static void mostrarEstudiantes(){
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
}
