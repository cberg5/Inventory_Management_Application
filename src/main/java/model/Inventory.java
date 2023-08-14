package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory Class. Used to set part and product objects into observable lists.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partId = 0;
    private static int productId = 0;

    /**
     * Method to generate a new ID number for a new part. Increments by 1 each time it is called.
     * @return a new ID number
     */
    public static int generatePartId(){
        return ++partId;
    }

    /**
     * Method to generate a new ID number for a new product. Increments by 1 each time it is called.
     * @return a new ID number
     */
    public static int generateProductId(){
        return ++productId;
    }

    /**
     * Method to add a new part object to the observable list for parts.
     * @param newPart
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Method to add a new product object to the observable list for products.
     * @param newProduct
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Method to look up a part in the part observable list using part ID
     * @param partId
     * @return the matching part
     */
    public static Part lookupPart(int partId){

        Part foundPart = null;

        for (Part part : allParts) {
            if (part.getId() == partId) {
                foundPart = part;
            }
        }
        return foundPart;
    }

    /**
     * Method to look up a part in the part observable list using product ID
     * @param productId
     * @return the matching product
     */
    public static Product lookupProduct(int productId){

        Product foundProduct = null;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                foundProduct = product;
            }
        }
        return foundProduct;
    }

    /**
     * Method to look up a part in the observable list using a part name
     * @param partName
     * @return the matching part
     */
    public static ObservableList<Part> lookupPart(String partName){

        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * Method to look up a part in the observable list using a product name
     * @param productName
     * @return the matching product
     */
   public static ObservableList<Product> lookupProduct(String productName){

       ObservableList<Product> foundProducts = FXCollections.observableArrayList();

       for (Product product : allProducts) {
           if (product.getName().equals(productName)) {
               foundProducts.add(product);
           }
       }
       return foundProducts;
    }

    /**
     * Method to update an existing part in the observable list
     * @param index
     * @param newPart
     */
    public static void updatePart(int index, Part newPart){
        allParts.set(index, newPart);
    }

    /**
     * Method to update an existing product in the observable list
     * @param index
     * @param newProduct
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * Method to remove a selected part from the observable list
     * @param selectedPart
     * @return true if successful, false if part is not found in the list
     */
    public static boolean deletePart(Part selectedPart){
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method to remove a a selected product from the observable list
     * @param selectedProduct
     * @return true if successful, false if product is not found in the list
     */
    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method to return all parts in the observable list
     * @return all parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Method to return all products in the observable list
     * @return all products
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
