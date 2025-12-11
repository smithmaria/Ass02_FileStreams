import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class PersonReader
{
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        ArrayList<Person> people = new ArrayList<>();

        /*
        Here is the data file we are reading:
        000001, Bilbo, Baggins, Esq., 1060
        000002, Frodo, Baggins, Esq., 1120
        000003, Samwise, Gamgee, Esq., 1125
        000004, Peregrin, Took, Esq., 1126
        000005, Meridoc, Brandybuck, Esq., 1126
        */

        final int FIELDS_LENGTH = 5;

        String id, firstName, lastName, title;
        int yob;

        System.out.print("\nChoose a person file to be read");

        try
        {
            // use the toolkit to get the current working directory of the IDE
            // Not sure if the toolkit is thread safe...
            File workingDirectory = new File(System.getProperty("user.dir"));

            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                // Typical java pattern of inherited classes
                // we wrap a BufferedWriter around a lower level BufferedOutputStream
                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));

                // Finally we can read the file LOL!
                int line = 0;  // if we want to keep track of the line numbers

                while(reader.ready())
                {
                    rec = reader.readLine();

                    // Split the line into fields and create Person object
                    String[] fields = rec.split(",");

                    if(fields.length == FIELDS_LENGTH)
                    {
                        id        = fields[0].trim();
                        firstName = fields[1].trim();
                        lastName  = fields[2].trim();
                        title     = fields[3].trim();
                        yob       = Integer.parseInt(fields[4].trim());

                        // Create a Person object and add to ArrayList
                        // Note: Using your constructor order: ID, firstName, lastName, title, YOB
                        Person person = new Person(id, firstName, lastName, title, yob);
                        people.add(person);
                    }
                    else
                    {
                        System.out.println("Found a record that may be corrupt: ");
                        System.out.println(rec);
                    }
                    line++;
                }
                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("\n\nData file read!");

                // Now display the people from the ArrayList using Person objects
                System.out.printf("\n%-8s%-25s%-25s%-6s%6s", "ID#", "First Name", "Last Name", "Title", "YOB");
                System.out.print("\n======================================================================");

                for(Person person : people)
                {
                    System.out.printf("\n%-8s%-25s%-25s%-6s%6d",
                            person.getID(),
                            person.getFirstName(),
                            person.getLastName(),
                            person.getTitle(),
                            person.getYOB());
                }
            }
            else  // user closed the file dialog without choosing
            {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
        }  // end of TRY
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}