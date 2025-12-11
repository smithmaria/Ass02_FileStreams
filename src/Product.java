import java.util.Objects;
import java.io.RandomAccessFile;
import java.io.IOException;

/**
 * Product class represents a product with basic information including name, description, ID, and cost.
 * Enhanced with Random Access File support using fixed-length fields.
 *
 * @author Maria Smith
 * @version 2.0
 */
public class Product {
    private String name;
    private String description;
    private String ID;
    private double cost;

    // Fixed field sizes for Random Access File
    // Size is measured in characters not bytes
    public static final int NAME_SIZE = 35;
    public static final int DESCRIPTION_SIZE = 75;
    public static final int ID_SIZE = 6;
    public static final int RECORD_SIZE = (NAME_SIZE + DESCRIPTION_SIZE + ID_SIZE) * 2 + 8; // 240 bytes

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
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getID() {
        return ID;
    }

    public double getCost() {
        return cost;
    }

    // Setters where it makes sense (ID should never change, so no setter for ID)
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Pads a string to the specified length with spaces
     * @param str The string to pad
     * @param length The desired length
     * @return Padded string
     */
    private static String padString(String str, int length) {
        if (str == null) {
            str = "";
        }
        // Truncate if too long
        if (str.length() > length) {
            return str.substring(0, length);
        }
        // Pad with spaces if too short
        return String.format("%-" + length + "s", str);
    }

    /**
     * Returns the name padded to NAME_SIZE characters
     * @return Padded name
     */
    public String getFixedName() {
        return padString(name, NAME_SIZE);
    }

    /**
     * Returns the description padded to DESCRIPTION_SIZE characters
     * @return Padded description
     */
    public String getFixedDescription() {
        return padString(description, DESCRIPTION_SIZE);
    }

    /**
     * Returns the ID padded to ID_SIZE characters
     * @return Padded ID
     */
    public String getFixedID() {
        return padString(ID, ID_SIZE);
    }

    /**
     * Writes this Product to a RandomAccessFile at the current position
     * @param raf The RandomAccessFile to write to
     * @throws IOException If an I/O error occurs
     */
    public void writeToRandomFile(RandomAccessFile raf) throws IOException {
        // Write name (35 chars × 2 bytes = 70 bytes)
        raf.writeChars(getFixedName());

        // Write description (75 chars × 2 bytes = 150 bytes)
        raf.writeChars(getFixedDescription());

        // Write ID (6 chars × 2 bytes = 12 bytes)
        raf.writeChars(getFixedID());

        // Write cost (8 bytes)
        raf.writeDouble(cost);
    }

    /**
     * Reads a Product from a RandomAccessFile at the current position
     * @param raf The RandomAccessFile to read from
     * @return A Product object, or null if end of file
     * @throws IOException If an I/O error occurs
     */
    public static Product readFromRandomFile(RandomAccessFile raf) throws IOException {
        // Check if we're at end of file
        if (raf.getFilePointer() >= raf.length()) {
            return null;
        }

        // Read name (35 characters)
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < NAME_SIZE; i++) {
            nameBuilder.append(raf.readChar());
        }
        String name = nameBuilder.toString().trim();

        // Read description (75 characters)
        StringBuilder descBuilder = new StringBuilder();
        for (int i = 0; i < DESCRIPTION_SIZE; i++) {
            descBuilder.append(raf.readChar());
        }
        String description = descBuilder.toString().trim();

        // Read ID (6 characters)
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < ID_SIZE; i++) {
            idBuilder.append(raf.readChar());
        }
        String id = idBuilder.toString().trim();

        // Read cost
        double cost = raf.readDouble();

        return new Product(name, description, id, cost);
    }

    /**
     * Seeks to a specific record number in the RandomAccessFile
     * @param raf The RandomAccessFile
     * @param recordNumber The record number (0-based)
     * @throws IOException If an I/O error occurs
     */
    public static void seekToRecord(RandomAccessFile raf, int recordNumber) throws IOException {
        raf.seek((long) recordNumber * RECORD_SIZE);
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