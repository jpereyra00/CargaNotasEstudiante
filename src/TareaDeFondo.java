public class TareaDeFondo implements Runnable {

    @Override
    public void run() {
        //devuelve el hilo que se ejecuta ahora mismo.
        String nombreHilo=  Thread.currentThread().getName();

        for(int paso=1; paso <= 3; paso ++){
            System.out.println("Hilo: "+nombreHilo+ "Paso numero: "+paso);
        }
    }
}
