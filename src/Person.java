import java.util.Calendar;
import java.util.Objects;

/**
 * Person class represents a person with basic information including name, ID, title, and year of birth.
 * This class provides various methods to format and display person information.
 * 
 * @author Maria Smith
 */

public class Person {
    private String firstName;
    private String lastName;
    private String ID;
    private String title;
    private int YOB;

    /**
    * Main constructor that takes all fields
    * @param ID - id, 6 digit number
    * @param firstName - first name
    * @param lastName - last name
    * @param title - title (Mr., Mrs., Dr., etc.)
    * @param YOB - year of birth
    */
    public Person(String ID, String firstName, String lastName, String title, int YOB) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.title = title;
        this.YOB = YOB;
    }

    /**
     * Overloaded constructor without title (defaults to empty string)
     * @param ID - id, 6 digit number
     * @param firstName - first name
     * @param lastName - last name
     * @param YOB - year of birth
     */
    public Person (String ID, String firstName, String lastName, int YOB) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.title = "";
        this.YOB = YOB;
    }

    // Getters for each field
    /**
     * Gets person first name
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets person last name
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets person ID
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Gets person title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets person YOB
     * @return YOB
     */
    public int getYOB() {
        return YOB;
    }


    // Setters for each field
    /**
     * Sets person first name
     * @param firstName - new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets person last name
     * @param lastName - new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets person title
     * @param title - new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets person year of birth
     * @param YOB - new YOB
     */
    public void setYOB(int YOB) {
        this.YOB = YOB;
    }


    // Additional methods
    /**
     * Returns the full name (first name + ' ' + last name)
     * @return The full name as a string
     */
    public String fullName() {
        return firstName + " " + lastName;
    }

    /**
     * Returns the full formal name (title + ' ' + full name)
     * @return The formal name as a string
     */
    public String formalName() {
        if (title == null || title.trim().isEmpty()) {
            return fullName();
        }
        return title + " " + fullName();
    }

    /**
     * Calculates and returns the person's current age assuming current year
     * @return Person's age as a string
     */
    public String getAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        return getAge(currentYear);
    }

    /**
     * Calculates age for a specific year that is given
     * @param year - the year to calculate an age for
     * @return The person's age for specified year
     */
    public String getAge(int year) {
        int age = year - YOB;
        return String.valueOf(age);
    }

    /**
     * Returns a comma-separated value (CSV) string
     * @return CSV formatted string
     */
    public String toCSV() {
        return ID + "," + firstName + "," + lastName + "," + title + "," + YOB;
    }

    /**
     * Returns a JSON formatted string of person object
     * @return JSON formatted string
     */
    public String toJSON() {
        return "{" +
                "\"ID\":\"" + ID + "\"," +
                "\"firstName\":\"" + firstName + "\"," +
                "\"lastName\":\"" + lastName + "\"," +
                "\"title\":\"" + title + "\"," +
                "\"YOB\":" + YOB +
                "}";    
    }

    /**
     * Returns an XML formatted string of person object
     * @return XML formatted string
     */
    public String toXML() {
        return "<Person>" +
                "<ID>" + ID + "</ID>" +
                "<firstName>" + firstName + "</firstName>" +
                "<lastName>" + lastName + "</lastName>" +
                "<title>" + title + "</title>" +
                "<YOB>" + YOB + "</YOB>" +
                "</Person>";    
    }

    /**
     * Returns string representation of the Person object
     * @return String representation showing all field values
     */
    @Override
    public String toString() {
        return "Person{" +
                ", ID='" + ID + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", YOB=" + YOB +
                '}'; 
    }

    /**
     * Compares this Person object with another object for equality
     * @param obj - the object to compare with
     * @return true if the objects are euqal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person person = (Person) obj;
        return YOB == person.YOB &&
                Objects.equals(ID, person.ID) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(title, person.title);
    }

    /**
     * Generates hash code for this Person object
     * @return hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(ID, firstName, lastName, title, YOB);
    }
}
