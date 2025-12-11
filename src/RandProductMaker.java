import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * GUI application for creating Product records in a Random Access File
 * Allows user to enter product data with validation and displays record count
 *
 * @author Maria Smith
 */
public class RandProductMaker extends JFrame {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField idField;
    private JTextField costField;
    private JTextField recordCountField;
    private JButton addButton;
    private JButton quitButton;

    private RandomAccessFile raf;
    private int recordCount;
    private static final String FILE_NAME = "ProductData.dat";

    /**
     * Constructor - sets up the GUI
     */
    public RandProductMaker() {
        setTitle("Random Access Product Maker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        recordCount = 0;
        initializeFile();
        createGUI();
        setVisible(true);
    }

    /**
     * Initialize or open the Random Access File
     */
    private void initializeFile() {
        try {
            File file = new File(FILE_NAME);
            raf = new RandomAccessFile(file, "rw");

            // If file exists, calculate record count
            if (file.exists() && file.length() > 0) {
                recordCount = (int) (raf.length() / Product.RECORD_SIZE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error opening file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Create the GUI components
     */
    private void createGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Product Data Entry", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 5, 10));

        inputPanel.add(new JLabel("Product Name (max 35 chars):"));
        nameField = new JTextField(20);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Description (max 75 chars):"));
        descriptionField = new JTextField(20);
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Product ID (6 digits):"));
        idField = new JTextField(20);
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Cost:"));
        costField = new JTextField(20);
        inputPanel.add(costField);

        inputPanel.add(new JLabel("Record Count:"));
        recordCountField = new JTextField(20);
        recordCountField.setEditable(false);
        recordCountField.setText(String.valueOf(recordCount));
        recordCountField.setBackground(Color.LIGHT_GRAY);
        inputPanel.add(recordCountField);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        addButton = new JButton("Add Record");
        addButton.addActionListener(e -> addRecord());
        buttonPanel.add(addButton);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quitApplication());
        buttonPanel.add(quitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Validate and add a record to the file
     */
    private void addRecord() {
        // Get field values
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String id = idField.getText().trim();
        String costText = costField.getText().trim();

        // Validate all fields are filled
        if (name.isEmpty() || description.isEmpty() || id.isEmpty() || costText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields must be filled in!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate name length
        if (name.length() > Product.NAME_SIZE) {
            JOptionPane.showMessageDialog(this,
                    "Product name must be " + Product.NAME_SIZE + " characters or less!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate description length
        if (description.length() > Product.DESCRIPTION_SIZE) {
            JOptionPane.showMessageDialog(this,
                    "Description must be " + Product.DESCRIPTION_SIZE + " characters or less!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate ID length and format
        if (id.length() != Product.ID_SIZE) {
            JOptionPane.showMessageDialog(this,
                    "Product ID must be exactly " + Product.ID_SIZE + " characters!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate cost is a valid double
        double cost;
        try {
            cost = Double.parseDouble(costText);
            if (cost < 0) {
                throw new NumberFormatException("Cost cannot be negative");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Cost must be a valid positive number!",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create Product and write to file
        try {
            Product product = new Product(name, description, id, cost);

            // Move to end of file
            raf.seek(raf.length());

            // Write the product
            product.writeToRandomFile(raf);

            // Increment record count
            recordCount++;
            recordCountField.setText(String.valueOf(recordCount));

            // Show success message
            JOptionPane.showMessageDialog(this,
                    "Record added successfully!\nRecord #" + recordCount,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            // Clear all fields for next entry
            clearFields();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error writing to file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clear all input fields
     */
    private void clearFields() {
        nameField.setText("");
        descriptionField.setText("");
        idField.setText("");
        costField.setText("");
        nameField.requestFocus();
    }

    /**
     * Close file and quit application
     */
    private void quitApplication() {
        try {
            if (raf != null) {
                raf.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to quit?",
                "Confirm Quit",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RandProductMaker());
    }
}
