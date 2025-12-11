import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductReader
{
    public static void main(String[] args)
    {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        // Changed from ArrayList<String> to ArrayList<Product>
        ArrayList<Product> products = new ArrayList<>();

        final int FIELDS_LENGTH = 4;

        String id, name, description;
        double cost;

        System.out.print("\nChoose a file to be read");

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

                while(reader.ready())
                {
                    rec = reader.readLine();

                    // Split the line into fields and create Product object
                    String[] fields = rec.split(",");

                    if(fields.length == FIELDS_LENGTH)
                    {
                        id          = fields[0].trim();
                        name        = fields[1].trim();
                        description = fields[2].trim();
                        cost        = Double.parseDouble(fields[3].trim());

                        // Create a Product object and add to ArrayList
                        Product product = new Product(name, description, id, cost);
                        products.add(product);
                    }
                    else
                    {
                        System.out.println("Found a record that may be corrupt: ");
                        System.out.println(rec);
                    }
                }
                reader.close(); // must close the file to seal it and flush buffer
                System.out.println("\n\nData file read!");

                // Now display the products from the ArrayList using Product objects
                System.out.printf("\n%-8s%-20s%-30s%-6s", "ID#", "Name", "Description", "Cost");
                System.out.print("\n===================================================================");

                for(Product product : products)
                {
                    System.out.printf("\n%-8s%-20s%-30s%-6.2f",
                            product.getID(),
                            product.getName(),
                            product.getDescription(),
                            product.getCost());
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