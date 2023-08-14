package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The ModifyProductMenuController Class.
 * Provides control logic to the ModifyProductMenu to allow the user to modify existing products and adjust the
 * associated parts linked to a product
 */
public class ModifyProductMenuController implements Initializable {

    Stage stage;
    Parent scene;
    private Product selectedProduct;
    private ObservableList<Part> assoParts = FXCollections.observableArrayList();


    @FXML
    private TableColumn<Part, Integer> assoPartIdCol;

    @FXML
    private TableColumn<Part, Integer> assoPartInvCol;

    @FXML
    private TableColumn<Part, String> assoPartNameCol;

    @FXML
    private TableColumn<Part, Double> assoPartPriceCol;

    @FXML
    private TableView<Part> assoPartTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TextField productIdTxt;

    @FXML
    private TextField productInvTxt;

    @FXML
    private TextField productMaxTxt;

    @FXML
    private TextField productMinTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productPriceTxt;

    /**
     * On action method for the search bar for the parts table.
     * Takes user input from the search bar and displays matching parts based on ID or name.
     * @param event
     */
    @FXML
    public void OnActionPartSearch(ActionEvent event){

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String searchText = partSearchTxt.getText();

        for(Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchText) || part.getName().contains(searchText)) {

                foundParts.add(part);
            }
        }
        partTableView.setItems(foundParts);

        if(foundParts.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found.");
            alert.showAndWait();
        }

        if(partSearchTxt.getText().isEmpty()){
            partTableView.setItems(Inventory.getAllParts());
        }

    }

    /**
     * On action method for the Add button.
     * Adds the selected part from the parts table to the associated parts table for the existing product.
     * @param event
     */
    @FXML
    void onActionAdd(ActionEvent event) {
        Part partToAdd = partTableView.getSelectionModel().getSelectedItem();

        if(partToAdd == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to add.");
            alert.showAndWait();
        }
        else {
            assoParts.add(partToAdd);
            assoPartTableView.setItems(assoParts);
        }

    }

    /**
     * On Action method for the cancel button.
     * Returns the user to the Main Menu.
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
     * On action method for the remove button.
     * Removes the selected part from the associated parts table and from the existing product.
     * @param event
     */
    @FXML
    void onActionRemove(ActionEvent event) {
        Part partToRemove = assoPartTableView.getSelectionModel().getSelectedItem();
        if(partToRemove == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to remove.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please confirm if you would like to remove the selected part.");
            alert.setTitle("Confirm Part Removal");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                assoParts.remove(partToRemove);
                assoPartTableView.setItems(assoParts);
            }
        }
    }

    /**
     * On action method for the save button.
     * Takes the user input from the text fields and updates the existing product. Adds any new associated parts to the existing product.
     * Returns user to the Main Menu when complete.
     * @param event
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try {
            int id = Integer.parseInt(productIdTxt.getText());
            String name = productNameTxt.getText();
            int stock = Integer.parseInt(productInvTxt.getText());
            double price = Double.parseDouble(productPriceTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());

            if (checkMinMax(min, max) && checkInv(min, max, stock)) {


                Product newProduct = new Product(id, name, price, stock, min, max);

                for (Part part : assoParts) {
                    newProduct.addAssociatedPart(part);
                }

                Inventory.addProduct(newProduct);
                Inventory.deleteProduct(selectedProduct);

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
     * Retrieves the selected product data from the MainMenuController. Populates the text fields with selected product data.
     * Populates the tables.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = MainMenuController.getSelectedProduct();
        assoParts = selectedProduct.getAllAssociatedParts();

        productIdTxt.setText(String.valueOf(selectedProduct.getId()));
        productNameTxt.setText(selectedProduct.getName());
        productInvTxt.setText(String.valueOf((selectedProduct.getStock())));
        productPriceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        productMaxTxt.setText(String.valueOf(selectedProduct.getMax()));
        productMinTxt.setText(String.valueOf(selectedProduct.getMin()));

        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        assoPartTableView.setItems(assoParts);
        assoPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assoPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assoPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assoPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
