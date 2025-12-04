package cr.ac.ucenfotec;

import cr.ac.ucenfotec.tl.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        try {
            controller.start();
        } catch (Exception e) {
            System.out.println("Error al ejecutar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
