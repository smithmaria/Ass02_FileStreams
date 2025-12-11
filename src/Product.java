import java.util.Objects;

/**
 * Product class represents a product with basic information including name, description, ID, and cost.
 * This class provides various methods to format and display product information.
 *
 * @author Maria Smith
 * @version 1.0
 */
public class Product {
    private String name;
    private String description;
    private String ID;
    private double cost;

    /**
     * Main constructor that takes all fields
     * @param name The product's name
     * @param description The product's description
     * @param ID The product's unique ID (sequence of digits)
     * @param cost The product's cost
     */
    public Product(String name, String description, String ID, double cost) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.cost = cost;
    }

    /**
     * Overloaded constructor without description (defaults to empty string)
     * @param name The product's name
     * @param ID The product's unique ID
     * @param cost The product's cost
     */
    public Product(String name, String ID, double cost) {
        this(name, "", ID, cost);
    }

    /**
     * Overloaded constructor with just name and ID (defaults: description="", cost=0.0)
     * @param name The product's name
     * @param ID The product's unique ID
     */
    public Product(String name, String ID) {
        this(name, "", ID, 0.0);
    }

    // Getters for all fields
    /**
     * Gets the product's name
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the product's description
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the product's ID
     * @return The ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Gets the product's cost
     * @return The cost
     */
    public double getCost() {
        return cost;
    }

    // Setters where it makes sense (ID should never change, so no setter for ID)
    /**
     * Sets the product's name
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the product's description
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the product's cost
     * @param cost The new cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Returns a comma-separated value (CSV) string suitable for writing to a file
     * @return CSV formatted string
     */
    public String toCSV() {
        return name + "," + description + "," + ID + "," + cost;
    }

    /**
     * Returns a JSON formatted string representation of the product
     * @return JSON formatted string
     */
    public String toJSON() {
        return "{" +
                "\"name\":\"" + name + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"ID\":\"" + ID + "\"," +
                "\"cost\":" + cost +
                "}";
    }

    /**
     * Returns an XML formatted string representation of the product
     * @return XML formatted string
     */
    public String toXML() {
        return "<Product>" +
                "<name>" + name + "</name>" +
                "<description>" + description + "</description>" +
                "<ID>" + ID + "</ID>" +
                "<cost>" + cost + "</cost>" +
                "</Product>";
    }

    /**
     * Returns a string representation of the Product object
     * @return String representation showing all field values
     */
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ID='" + ID + '\'' +
                ", cost=" + cost +
                '}';
    }

    /**
     * Compares this Product object with another object for equality
     * @param obj The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Product product = (Product) obj;
        return Double.compare(product.cost, cost) == 0 &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(ID, product.ID);
    }

    /**
     * Generates hash code for this Product object
     * @return hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, description, ID, cost);
    }
}
