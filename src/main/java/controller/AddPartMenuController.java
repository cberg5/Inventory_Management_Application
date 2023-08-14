package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The AddPartMenuController Class.
 * Provides control logic to the AddPartMenu to allow new parts to be added using user input from text fields.
 */
public class AddPartMenuController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Label partIdNameLabel;

    @FXML
    private RadioButton partInRBtn;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partIdNameTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private RadioButton partOutRBtn;

    @FXML
    private TextField partPriceTxt;

    /**
     * On Action event for the In-House radio button. Changes the label text to "Machine ID" if pressed.
     * @param event
     */
    @FXML
    void onActionInHouseRB(ActionEvent event){
        partIdNameLabel.setText("Machine ID");
    }

    /**
     * On Action method for the Outsourced radio button. Changes the label text to "Company Name" if pressed.
     * @param event
     */
    @FXML
    void onActionOutsourcedRB(ActionEvent event){
        partIdNameLabel.setText("Company Name");
    }

    /**
     * On Action method for the Cancel Button. Returns user to the Main Menu when clicked.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * On Action method for the Save Button. Takes the inputted data from the text fields and creates a new part.
     * Returns the user to the Main Menu when complete.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {

        try {
                int id = 0;
                String name = partNameTxt.getText();
                int stock = Integer.parseInt(partInvTxt.getText());
                double price = Double.parseDouble(partPriceTxt.getText());
                int max = Integer.parseInt(partMaxTxt.getText());
                int min = Integer.parseInt(partMinTxt.getText());
                int machineId;
                String companyName;

                if (checkMinMax(min, max) && checkInv(min, max, stock)) {
                    if (partInRBtn.isSelected()) {
                        machineId = Integer.parseInt((partIdNameTxt.getText()));
                        id = Inventory.generatePartId();
                        InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newPart);
                    }
                    if (partOutRBtn.isSelected()) {
                        companyName = partIdNameTxt.getText();
                        id = Inventory.generatePartId();
                        Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        Inventory.addPart(newPart);
                    }

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
        }

        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please input valid data for each field.");
            alert.showAndWait();
        }
    }

    /**
     * A method to verify if the user inputted min and max are valid.
     * Displays error message if min is set to a higher value than max.
     * @param min
     * @param max
     * @return true or false depending if input is valid
     */
    private boolean checkMinMax(int min, int max){
        if(min >= max || min < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Minimum cannot be greater than Maximum.");
            alert.showAndWait();
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * A method to verify if the user inputted stock value is valid.
     * Displays an error if the stock quantity is set to a value outside the range of the min and max.
     * @param min
     * @param max
     * @param stock
     * @return true or false depending if input is valid
     */
    private boolean checkInv(int min, int max, int stock){
        if(stock < min || stock > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between Min and Max values.");
            alert.showAndWait();
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Initializes the controller.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
