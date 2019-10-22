package uk.ac.ucl.app;

public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
        javax.swing.SwingUtilities.invokeLater(()->View.createGUI(view));
    }

}
