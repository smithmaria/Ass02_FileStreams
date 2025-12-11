import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

/**
 * JUnit test class for SafeInputObj class
 * Tests constructors and methods using simulated input
 * Note: Interactive methods are tested with pre-programmed input strings
 *
 * @author Maria Smith
 */
public class SafeInputObjTest {

    private SafeInputObj safeInputDefault;
    private SafeInputObj safeInputCustom;

    /**
     * Set up test objects before each test method
     * This runs before every @Test method
     */
    @BeforeEach
    void setUp() {
        // Create SafeInputObj with default constructor
        safeInputDefault = new SafeInputObj();

        // Create SafeInputObj with custom Scanner for testing
        String simulatedInput = "test\n5\n25.5\nY\nvalid123\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        safeInputCustom = new SafeInputObj(testScanner);
    }

    /**
     * Test the default constructor
     */
    @Test
    void testDefaultConstructor() {
        SafeInputObj safeInput = new SafeInputObj();
        assertNotNull(safeInput);
    }

    /**
     * Test the constructor with Scanner parameter
     */
    @Test
    void testConstructorWithScanner() {
        Scanner testScanner = new Scanner("test input");
        SafeInputObj safeInput = new SafeInputObj(testScanner);
        assertNotNull(safeInput);
    }

    /**
     * Test getNonZeroLenString method with valid input
     */
    @Test
    void testGetNonZeroLenStringValid() {
        // Simulate input: "Hello"
        String input = "Hello\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        String result = safeInput.getNonZeroLenString("Enter text");
        assertEquals("Hello", result);
    }

    /**
     * Test getNonZeroLenString method with empty input then valid input
     */
    @Test
    void testGetNonZeroLenStringEmptyThenValid() {
        // Simulate input: empty line, then "Valid"
        String input = "\nValid\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        String result = safeInput.getNonZeroLenString("Enter text");
        assertEquals("Valid", result);
    }

    /**
     * Test getInt method with valid input
     */
    @Test
    void testGetIntValid() {
        // Simulate input: "42"
        String input = "42\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        int result = safeInput.getInt("Enter number");
        assertEquals(42, result);
    }

    /**
     * Test getInt method with invalid then valid input
     */
    @Test
    void testGetIntInvalidThenValid() {
        // Simulate input: "abc", then "123"
        String input = "abc\n123\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        int result = safeInput.getInt("Enter number");
        assertEquals(123, result);
    }

    /**
     * Test getRangedInt method with valid input
     */
    @Test
    void testGetRangedIntValid() {
        // Simulate input: "15" (within range 10-20)
        String input = "15\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        int result = safeInput.getRangedInt("Enter number", 10, 20);
        assertEquals(15, result);
    }

    /**
     * Test getRangedInt method with out-of-range then valid input
     */
    @Test
    void testGetRangedIntOutOfRangeThenValid() {
        // Simulate input: "25" (out of range), then "15" (valid)
        String input = "25\n15\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        int result = safeInput.getRangedInt("Enter number", 10, 20);
        assertEquals(15, result);
    }

    /**
     * Test getDouble method with valid input
     */
    @Test
    void testGetDoubleValid() {
        // Simulate input: "3.14"
        String input = "3.14\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        double result = safeInput.getDouble("Enter decimal");
        assertEquals(3.14, result, 0.001);
    }

    /**
     * Test getRangedDouble method with valid input
     */
    @Test
    void testGetRangedDoubleValid() {
        // Simulate input: "2.5" (within range 1-5)
        String input = "2.5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        double result = safeInput.getRangedDouble("Enter decimal", 1, 5);
        assertEquals(2.5, result, 0.001);
    }

    /**
     * Test getYNConfirm method with 'Y' input
     */
    @Test
    void testGetYNConfirmYes() {
        // Simulate input: "Y"
        String input = "Y\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        boolean result = safeInput.getYNConfirm("Confirm");
        assertTrue(result);
    }

    /**
     * Test getYNConfirm method with 'N' input
     */
    @Test
    void testGetYNConfirmNo() {
        // Simulate input: "N"
        String input = "N\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        boolean result = safeInput.getYNConfirm("Confirm");
        assertFalse(result);
    }

    /**
     * Test getYNConfirm method with invalid then valid input
     */
    @Test
    void testGetYNConfirmInvalidThenValid() {
        // Simulate input: "maybe", then "Y"
        String input = "maybe\nY\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        boolean result = safeInput.getYNConfirm("Confirm");
        assertTrue(result);
    }

    /**
     * Test getRegExString method with valid input
     */
    @Test
    void testGetRegExStringValid() {
        // Simulate input: "abc123" (matches pattern for alphanumeric)
        String input = "abc123\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        String result = safeInput.getRegExString("Enter alphanumeric", "^[a-zA-Z0-9]+$");
        assertEquals("abc123", result);
    }

    /**
     * Test getRegExString method with invalid then valid input
     */
    @Test
    void testGetRegExStringInvalidThenValid() {
        // Simulate input: "abc@123" (invalid), then "abc123" (valid)
        String input = "abc@123\nabc123\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);
        SafeInputObj safeInput = new SafeInputObj(testScanner);

        String result = safeInput.getRegExString("Enter alphanumeric", "^[a-zA-Z0-9]+$");
        assertEquals("abc123", result);
    }
}