import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for Product class
 * Tests constructors, setter methods, and additional specified methods
 * Note: Getter methods are not tested as per assignment requirements
 *
 * @author Maria Smith
 */
public class ProductTest {

    private Product testProduct;
    private Product testProduct2;
    private Product testProduct3;

    /**
     * Set up test objects before each test method
     * This runs before every @Test method
     */
    @BeforeEach
    void setUp() {
        // Create test Product objects for use in tests
        testProduct = new Product("Widget", "A useful widget", "12345", 19.99);
        testProduct2 = new Product("Gadget", "67890", 29.99); // No description
        testProduct3 = new Product("Tool", "11111"); // Just name and ID
    }

    /**
     * Test the main constructor with all parameters
     */
    @Test
    void testMainConstructor() {
        Product product = new Product("TestItem", "Test description", "99999", 49.99);

        assertEquals("TestItem", product.getName());
        assertEquals("Test description", product.getDescription());
        assertEquals("99999", product.getID());
        assertEquals(49.99, product.getCost(), 0.01); // Delta for double comparison
    }

    /**
     * Test the overloaded constructor without description
     */
    @Test
    void testConstructorWithoutDescription() {
        Product product = new Product("SimpleItem", "55555", 15.99);

        assertEquals("SimpleItem", product.getName());
        assertEquals("", product.getDescription()); // Should default to empty string
        assertEquals("55555", product.getID());
        assertEquals(15.99, product.getCost(), 0.01);
    }

    /**
     * Test the overloaded constructor with just name and ID
     */
    @Test
    void testConstructorNameAndIdOnly() {
        Product product = new Product("BasicItem", "77777");

        assertEquals("BasicItem", product.getName());
        assertEquals("", product.getDescription()); // Should default to empty string
        assertEquals("77777", product.getID());
        assertEquals(0.0, product.getCost(), 0.01); // Should default to 0.0
    }

    /**
     * Test setName method
     */
    @Test
    void testSetName() {
        testProduct.setName("Super Widget");
        assertEquals("Super Widget", testProduct.getName());
    }

    /**
     * Test setDescription method
     */
    @Test
    void testSetDescription() {
        testProduct.setDescription("An amazing widget");
        assertEquals("An amazing widget", testProduct.getDescription());
    }

    /**
     * Test setCost method
     */
    @Test
    void testSetCost() {
        testProduct.setCost(24.99);
        assertEquals(24.99, testProduct.getCost(), 0.01);
    }

    /**
     * Test toCSV method
     */
    @Test
    void testToCSV() {
        String expected = "Widget,A useful widget,12345,19.99";
        assertEquals(expected, testProduct.toCSV());

        String expected2 = "Gadget,,67890,29.99"; // Empty description
        assertEquals(expected2, testProduct2.toCSV());
    }

    /**
     * Test toJSON method
     */
    @Test
    void testToJSON() {
        String expected = "{\"name\":\"Widget\",\"description\":\"A useful widget\",\"ID\":\"12345\",\"cost\":19.99}";
        assertEquals(expected, testProduct.toJSON());
    }

    /**
     * Test toXML method
     */
    @Test
    void testToXML() {
        String expected = "<Product><name>Widget</name><description>A useful widget</description><ID>12345</ID><cost>19.99</cost></Product>";
        assertEquals(expected, testProduct.toXML());
    }

    /**
     * Test toString method
     */
    @Test
    void testToString() {
        String result = testProduct.toString();
        assertTrue(result.contains("name='Widget'"));
        assertTrue(result.contains("description='A useful widget'"));
        assertTrue(result.contains("ID='12345'"));
        assertTrue(result.contains("cost=19.99"));
    }

    /**
     * Test equals method - same content should be equal
     */
    @Test
    void testEqualsTrue() {
        Product product1 = new Product("Widget", "A useful widget", "12345", 19.99);
        Product product2 = new Product("Widget", "A useful widget", "12345", 19.99);

        assertTrue(product1.equals(product2));
        assertTrue(product2.equals(product1)); // Test symmetry
    }

    /**
     * Test equals method - different content should not be equal
     */
    @Test
    void testEqualsFalse() {
        Product differentProduct = new Product("Different", "A useful widget", "12345", 19.99);

        assertFalse(testProduct.equals(differentProduct));
    }

    /**
     * Test equals method - comparing with null should return false
     */
    @Test
    void testEqualsNull() {
        assertFalse(testProduct.equals(null));
    }

    /**
     * Test equals method - comparing with different class should return false
     */
    @Test
    void testEqualsDifferentClass() {
        String notAProduct = "Not a product";
        assertFalse(testProduct.equals(notAProduct));
    }

    /**
     * Test equals method - comparing with self should return true
     */
    @Test
    void testEqualsSelf() {
        assertTrue(testProduct.equals(testProduct));
    }

    /**
     * Test hashCode method - equal objects should have same hash code
     */
    @Test
    void testHashCodeConsistency() {
        Product product1 = new Product("Widget", "A useful widget", "12345", 19.99);
        Product product2 = new Product("Widget", "A useful widget", "12345", 19.99);

        assertEquals(product1.hashCode(), product2.hashCode());
    }

    /**
     * Test that hashCode is consistent (returns same value when called multiple times)
     */
    @Test
    void testHashCodeStability() {
        int firstCall = testProduct.hashCode();
        int secondCall = testProduct.hashCode();

        assertEquals(firstCall, secondCall);
    }
}
