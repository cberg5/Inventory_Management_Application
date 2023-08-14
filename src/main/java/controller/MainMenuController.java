package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The MainMenuController Class.
 * Provides control logic to the MainMenu to search for existing parts and products, select existing products to modify,
 * or add new parts or products.
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;

    private static Part selectedPart;
    private static Product selectedProduct;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TextField partsTxtSearch;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TextField productsTxtSearch;

    /**
     * A static method that allows the selected part data from the parts table to be sent to the ModifyPartMenuController
     * @return the selected part from the parts table.
     */
    public static Part getSelectedPart(){
        return selectedPart;
    }

    /**
     * A static method that allows the selected product data from the product table to be sent to the ModifyProductMenuController
     * @return the selected product from the product table.
     */
    public static Product getSelectedProduct(){
        return selectedProduct;
    }

    /**
     * On action method for the search bar for the parts table.
     * Takes user input from the search bar and displays matching parts based on ID or name.
     * @param event
     */
    @FXML
    void OnActionPartSearch(ActionEvent event){

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String searchText = partsTxtSearch.getText();

        for(Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchText) || part.getName().contains(searchText)) {

            foundParts.add(part);
            }
        }
        partsTableView.setItems(foundParts);

        if(foundParts.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found.");
            alert.showAndWait();
        }

        if(partsTxtSearch.getText().isEmpty()){
            partsTableView.setItems(Inventory.getAllParts());
        }

    }

    /**
     * On action method for the search bar for the product table.
     * Takes user input from the search bar and displays matching products based on ID or name.
     * @param event
     */
    @FXML
    void OnActionProductSearch(ActionEvent event){

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        String searchText = productsTxtSearch.getText();

        for(Product product : allProducts) {
            if (String.valueOf(product.getId()).contains(searchText) || product.getName().contains(searchText)) {

                foundProducts.add(product);
            }
        }
        productsTableView.setItems(foundProducts);

        if(foundProducts.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product not found.");
            alert.showAndWait();
        }

        if(productsTxtSearch.getText().isEmpty()){
            productsTableView.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * On action method for the add button beneath the parts table.
     * Loads and sends the user to the AddPartMenu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * On action method for the add button beneath the product table.
     * Loads and sends the user to the AddProductMenu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * On action method for the delete button beneath the part table.
     * Deletes the selected part from the part table.
     * @param event
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {

        Part partToDelete = partsTableView.getSelectionModel().getSelectedItem();
        if(partToDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to delete.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please confirm if you would like to delete the selected part.");
            alert.setTitle("Confirm Part Deletion");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(partToDelete);
            }
        }
    }

    /**
     * On action method for the delete button beneath the product table.
     * Deletes the selected product from the product table. Restricts deletion if a product still has associated parts.
     * Future Enhancement: Currently the application does not allow you to delete a product if it still has associated parts in the object.
     * A future enhancement would be to automatically remove the associated parts from the product when a product is deleted instead of requiring
     * the user to remove them manually.
     * @param event
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {

        Product productToDelete = productsTableView.getSelectionModel().getSelectedItem();
        if(productToDelete == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to delete.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Please confirm if you would like to delete the selected product.");
            alert.setTitle("Confirm Product Deletion");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                ObservableList<Part> assoParts = productToDelete.getAllAssociatedParts();

                if(assoParts.size() > 0){
                    Alert alert2 = new Alert(Alert.AlertType.ERROR, "All associated parts must be removed prior to product deletion.");
                    alert2.showAndWait();
                }
                else{
                    Inventory.deleteProduct(productToDelete);
                }
            }
        }
    }

    /**
     * On action method for the exit button.
     * Exits the program.
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * On action method for the modify button beneath the part table.
     * Checks if part is selected and loads and sends the user to the ModifyPartMenu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to modify.");
            alert.showAndWait();
        }
        else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyPartMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * On action method for the modify button beneath the product table.
     * Checks if product is selected and loads and sends the user to the ModifyProductMenu.
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to modify.");
            alert.showAndWait();
        }
        else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }



    /**
     * Initializes the controller.
     * Populates the tables.
     * Runtime Error: "Can not retrieve property 'id' in PropertyValueFactory: javafx.scene.control.cell.PropertyValueFactory@26ddb094 with provided class type: class model.InHouse
     * java.lang.RuntimeException: java.lang.IllegalAccessException:"  Was not able to get the tables to populate with data. I double-checked all the field names were correct.
     * After further research realized I needed to adjust the module-info.java file to open and export the model file. This fixed the problem and the tables populated correctly.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

}