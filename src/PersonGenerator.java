import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonGenerator
{
    public static void main(String[] args)
    {
        // Changed from ArrayList<String> to ArrayList<Person>
        ArrayList<Person> people = new ArrayList<>();

        Scanner in = new Scanner(System.in);
        boolean doneInput = false;

        String ID = "";
        String fName = "";
        String lName = "";
        String title = "";
        int yob = 0;

        String fileName = "";

        do
        {
            ID = SafeInput.getNonZeroLenString(in, "Enter your ID");
            fName = SafeInput.getNonZeroLenString(in, "Enter your First Name");
            lName = SafeInput.getNonZeroLenString(in, "Enter your Last Name");
            title = SafeInput.getNonZeroLenString(in, "Enter your Title");
            yob = SafeInput.getRangedInt(in, "Enter the year of your birth", 1000, 9999);

            // Create a Person object instead of a string
            Person person = new Person(ID, fName, lName, title, yob);

            // Add the Person object to the ArrayList
            people.add(person);

            doneInput = SafeInput.getYNConfirm(in, "Are you done entering records?");

        } while(!doneInput);

        fileName = SafeInput.getNonZeroLenString(in, "Enter desired file name");

        // uses a fixed known path:
        //  Path file = Paths.get("c:\\My Documents\\data.txt");

        // use the toolkit to get the current working directory of the IDE
        // will create the file within the Netbeans project src folder
        // (may need to adjust for other IDE)
        // Not sure if the toolkit is thread safe...
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath(), "src", fileName + ".txt");

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!
            // Use the Person's toCSV() method to write each record
            for(Person person : people)
            {
                String csvRecord = person.toCSV(); // Use the toCSV() method we created
                writer.write(csvRecord, 0, csvRecord.length());
                writer.newLine();  // adds the new line
            }

            writer.close(); // must close the file to seal it and flush buffer
            System.out.println("Data file written!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}