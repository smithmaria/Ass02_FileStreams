import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter
{
    public static void main(String[] args)
    {

        //Product fields
        String id = "";
        String name = "";
        String description = "";
        double cost;

        String rec = "";

        String fileName = "";
        boolean doneInput = false;

        //Array to store each product as one line
        ArrayList<String> recs = new ArrayList<>();
        Scanner in = new Scanner(System.in);

        do
        {
            id = SafeInput.getNonZeroLenString(in, "Enter ID");
            name = SafeInput.getNonZeroLenString(in, "Enter name");
            description = SafeInput.getNonZeroLenString(in, "Enter description");
            cost = SafeInput.getDouble(in, "Enter cost");

            rec = id + ", " + name + ", " + description + ", " + cost;

            recs.add(rec);

            doneInput = SafeInput.getYNConfirm(in, "Are you done entering products?");

        } while(!doneInput);

        fileName = SafeInput.getNonZeroLenString(in, "Enter file name");

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName + ".txt");

        try
        {
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            // Finally can write the file LOL!

            for(String r : recs)
            {
                writer.write(r, 0, r.length());  // stupid syntax for write rec
                // 0 is where to start (1st char) the write
                // r. length() is how many chars to write (all)
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
