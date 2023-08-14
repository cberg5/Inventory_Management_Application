package model;

/**
 * Outsourced parts class. Is a subclass of Part
 */
public class Outsourced extends Part {
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Method to set the company name of a new part
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Method to retrieve the company name of an existing part
     * @return company name of a part
     */
    public String getCompanyName() {
        return companyName;
    }
}
