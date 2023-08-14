package model;

/**
 * InHouse parts class. Is a subclass of Part
 */
public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Method to set machine ID.
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Method to retrieve machine ID
     * @return The machine ID for an InHouse object.
     */
    public int getMachineId() {
        return machineId;
    }

}
