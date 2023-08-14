package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product Class. Used to set and retrieve data on product objects.
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Method to set ID of a product
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method to set name of a product
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to set price of a product
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method to set stock of a product
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Method to set min of a product
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Method to set max of a product
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Method to retrieve ID of a product
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Method to retrieve name of a product
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to retrieve price of a product
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Method to retrieve stock of a product
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Method to retrieve min of a product
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * Method to retrieve max of a product
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Method to add an associated part to a product
     * @param part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Method to remove an associated part from a product
     * @param selectedAssociatedPart
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method to retrieve all associated parts for a product
     * @return all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
