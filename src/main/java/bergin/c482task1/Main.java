package bergin.c482task1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
/**
 * @author Cody Bergin
 */

/**
 * The Main class that creates the application and inputs sample data for the program.
 */
public class Main extends Application {

    /** The start method that initiates the stage and loads the Main Menu screen.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 839, 378);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method that starts the application and loads in sample data.
     * @param args
     */
    public static void main(String[] args) {
        int partId;
        int productId;

        partId = Inventory.generatePartId();
        InHouse part1 = new InHouse(partId, "Window", 100.00, 10, 1, 50, 01);
        partId = Inventory.generatePartId();
        InHouse part2 = new InHouse(partId, "Door", 250.00, 15, 1, 50, 02);
        partId = Inventory.generatePartId();
        InHouse part3 = new InHouse(partId, "Tire", 150.00, 20, 1, 50, 03);
        partId = Inventory.generatePartId();
        Outsourced part4 = new Outsourced(partId, "Handle", 50.00, 25, 1, 50, "Handle's R Us");
        partId = Inventory.generatePartId();
        Outsourced part5 = new Outsourced(partId, "Steering Wheel", 75.00, 18, 1, 50, "Wheelers");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);

        productId = Inventory.generateProductId();
        Product product1 = new Product(productId, "Car", 25000.00, 10, 1, 50);
        product1.addAssociatedPart(part1);
        product1.addAssociatedPart(part2);
        product1.addAssociatedPart(part3);
        product1.addAssociatedPart(part5);

        productId = Inventory.generateProductId();
        Product product2 = new Product(productId, "Motorcycle", 10000.00, 5, 1, 50);
        product2.addAssociatedPart(part3);
        product2.addAssociatedPart(part4);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        launch();
    }
}