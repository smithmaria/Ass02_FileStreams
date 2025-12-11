import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Transient;

/**
 * Junit test class for Person class
 * Tests constructors, setter methods, and additional specified methods
 * (Getter methods not tested)
 * 
 * @author Maria Smith
 */

public class PersonTest {
    private Person testPerson;
    private Person testPerson2;
    private Person testPerson3;

    /**
    * Set up test objects
    * Runs before every @Test method
    */
    @BeforeEach
    void setUp() {
        testPerson = new Person("12345", "John", "Doe", "Mr.", 1990);
        testPerson2 = new Person("67890", "Jane", "Smith", 1985);
    }

    /**
    * Test the main constructor with all parameters
    */
    @Test
    void testMainConstructor() {
        Person person = new Person("99999", "Alice", "Brown", "Dr.", 1975);

        assertEquals("99999", person.getID());
        assertEquals("Alice", person.getFirstName());
        assertEquals("Brown", person.getLastName());
        assertEquals("Dr.", person.getTitle());
        assertEquals(1975, person.getYOB());
    }

    /**
     * Test the overloaded constructor without title
     */
    @Test
    void testConstructorWithoutTitle() {
        Person person = new Person("99999", "Alice", "Brown", 1975);

        assertEquals("99999", person.getID());
        assertEquals("Alice", person.getFirstName());
        assertEquals("Brown", person.getLastName());
        assertEquals("", person.getTitle());
        assertEquals(1975, person.getYOB());
    }

    /**
     * Test setFirstName method
     */
    @Test
    void testSetFirstName() {
        testPerson.setFirstName("John");
        assertEquals("John", testPerson.getFirstName());
    }

    /**
     * Test setLastName method
     */
    @Test
    void testSetLastName() {
        testPerson.setLastName("Smith");
        assertEquals("Smith", testPerson.getLastName());
    }

    /**
     * Test setTitle method
     */
    @Test
    void testSetTitle() {
        testPerson.setTitle("Dr.");
        assertEquals("Dr.", testPerson.getTitle());
    }

    /**
     * Test setYOB method
     */
    @Test
    void testSetYOB() {
        testPerson.setYOB(1990);
        assertEquals(1990, testPerson.getYOB());
    }

    /**
     * Test fullName method
     */
    @Test
    void testFullName() {
        assertEquals("John Doe", testPerson.fullName());
        assertEquals("Jane Smith", testPerson2.fullName());
    }

    /**
     * Test formalName method with title
     */
    @Test
    void testFormalNameWithTitle() {
        assertEquals("Mr. John Doe", testPerson.formalName());
    }

    /**
     * Test formalName method without title
     */
    @Test
    void testFormalNameWithoutTitle() {
        assertEquals("Jane Smith", testPerson2.formalName()); // Should just return fullName
    }

    /**
     * Test getAge method with current year
     */
    @Test
    void testGetAgeCurrentYear() {
        String age = testPerson.getAge();
        assertNotNull(age);
        assertTrue(Integer.parseInt(age) >= 0);
    }

    /**
     * Test getAge method with specified year
     */
    @Test
    void testGetAgeSpecificYear() {
        assertEquals("30", testPerson.getAge(2020)); // Born 1990, age in 2020 = 30
        assertEquals("35", testPerson2.getAge(2020)); // Born 1985, age in 2020 = 35
    }

    /**
     * Test toCSV method
     */
    @Test
    void testToCSV() {
        String expected = "12345,John,Doe,Mr.,1990";
        assertEquals(expected, testPerson.toCSV());

        String expected2 = "67890,Jane,Smith,,1985";
        assertEquals(expected2, testPerson2.toCSV());
    }

    /**
     * Test toJSON method
     */
    @Test
    void testToJSON() {
        String expected = "{\"ID\":\"12345\",\"firstName\":\"John\",\"lastName\":\"Doe\",\"title\":\"Mr.\",\"YOB\":1990}";
        assertEquals(expected, testPerson.toJSON());
    }

    /**
     * Test toXML method
     */
    @Test
    void testToXML() {
        String expected = "<Person><ID>12345</ID><firstName>John</firstName><lastName>Doe</lastName><title>Mr.</title><YOB>1990</YOB></Person>";
        assertEquals(expected, testPerson.toXML());
    }

    /**
     * Test toString method
     */
    @Test
    void testToString() {
        String result = testPerson.toString();
        assertTrue(result.contains("ID='12345'"));
        assertTrue(result.contains("firstName='John'"));
        assertTrue(result.contains("lastName='Doe'"));
        assertTrue(result.contains("title='Mr.'"));
        assertTrue(result.contains("YOB=1990"));
    }

    /**
     * Test equals method - same content should be equal
     */
    @Test
    void testEqualsTrue() {
        Person person1 = new Person("12345", "John", "Doe", "Mr.", 1990);
        Person person2 = new Person("12345", "John", "Doe", "Mr.", 1990);

        assertTrue(person1.equals(person2));
        assertTrue(person2.equals(person1)); // Test symmetry
    }

    /**
     * Test equals method - different content should not be equal
     */
    @Test
    void testEqualsFalse() {
        Person differentPerson = new Person("12345","Jane", "Doe",  "Mr.", 1990);

        assertFalse(testPerson.equals(differentPerson));
    }

    /**
     * Test equals method - comparing with null should return false
     */
    @Test
    void testEqualsNull() {
        assertFalse(testPerson.equals(null));
    }

    /**
     * Test equals method - comparing with different class should return false
     */
    @Test
    void testEqualsDifferentClass() {
        String notAPerson = "Not a person";
        assertFalse(testPerson.equals(notAPerson));
    }

    /**
     * Test equals method - comparing with self should return true
     */
    @Test
    void testEqualsSelf() {
        assertTrue(testPerson.equals(testPerson));
    }

    /**
     * Test hashCode method - equal objects should have same hash code
     */
    @Test
    void testHashCodeConsistency() {
        Person person1 = new Person("12345", "John", "Doe", "Mr.", 1990);
        Person person2 = new Person("12345", "John", "Doe", "Mr.", 1990);

        assertEquals(person1.hashCode(), person2.hashCode());
    }

    /**
     * Test that hashCode is consistent (returns same value when called multiple times)
     */
    @Test
    void testHashCodeStability() {
        int firstCall = testPerson.hashCode();
        int secondCall = testPerson.hashCode();

        assertEquals(firstCall, secondCall);
    }
}
