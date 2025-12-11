/**
 * Demonstration program to test SafeInputObj methods
 * This program tests each method in SafeInputObj to show they work correctly
 *
 * @author Maria Smith
 */

public class ObjInputTest {

    public static void main(String[] args) {
        // Create a SafeInputObj instance
        SafeInputObj safeInput = new SafeInputObj();

        System.out.println("=== SafeInputObj Test Program ===");
        System.out.println("This program will test each method in SafeInputObj");
        System.out.println();

        // Test getNonZeroLenString
        System.out.println("1. Testing getNonZeroLenString():");
        String name = safeInput.getNonZeroLenString("Enter your name");
        System.out.println("You entered: " + name);
        System.out.println();

        // Test getInt
        System.out.println("2. Testing getInt():");
        int favoriteNumber = safeInput.getInt("Enter your favorite number");
        System.out.println("You entered: " + favoriteNumber);
        System.out.println();

        // Test getRangedInt
        System.out.println("3. Testing getRangedInt():");
        int age = safeInput.getRangedInt("Enter your age", 0, 120);
        System.out.println("You entered: " + age);
        System.out.println();

        // Test getDouble
        System.out.println("4. Testing getDouble():");
        double height = safeInput.getDouble("Enter your height in feet (e.g., 5.8)");
        System.out.println("You entered: " + height);
        System.out.println();

        // Test getRangedDouble
        System.out.println("5. Testing getRangedDouble():");
        double gpa = safeInput.getRangedDouble("Enter your GPA", 0, 4);
        System.out.println("You entered: " + gpa);
        System.out.println();

        // Test getYNConfirm
        System.out.println("6. Testing getYNConfirm():");
        boolean likesJava = safeInput.getYNConfirm("Do you like Java programming?");
        System.out.println("You answered: " + (likesJava ? "Yes" : "No"));
        System.out.println();

        // Test getRegExString
        System.out.println("7. Testing getRegExString():");
        String email = safeInput.getRegExString("Enter a simple email (format: word@word.word)",
                "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$");
        System.out.println("You entered: " + email);
        System.out.println();

        // Summary
        System.out.println("=== Test Results Summary ===");
        System.out.println("Name: " + name);
        System.out.println("Favorite Number: " + favoriteNumber);
        System.out.println("Age: " + age);
        System.out.println("Height: " + height + " feet");
        System.out.println("GPA: " + gpa);
        System.out.println("Likes Java: " + (likesJava ? "Yes" : "No"));
        System.out.println("Email: " + email);
        System.out.println();
        System.out.println("All SafeInputObj methods tested successfully!");
    }
}