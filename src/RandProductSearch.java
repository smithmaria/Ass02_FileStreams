import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * GUI application for searching Product records in a Random Access File
 * Searches by partial product name and displays all matching results
 *
 * @author Maria Smith
 */
public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JButton quitButton;
    private JTextArea resultsArea;
    private JScrollPane scrollPane;

    private static final String FILE_NAME = "ProductData.dat";

    /**
     * Constructor - sets up the GUI
     */
    public RandProductSearch() {
        setTitle("Random Access Product Search");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();
        setVisible(true);
    }

    /**
     * Create the GUI components
     */
    private void createGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Product Search", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        searchPanel.add(new JLabel("Search by Product Name:"));
        searchField = new JTextField(30);
        searchPanel.add(searchField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        searchPanel.add(searchButton);

        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quitApplication());
        searchPanel.add(quitButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Results area
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        scrollPane = new JScrollPane(resultsArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Instructions panel
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new BorderLayout());
        JLabel instructionsLabel = new JLabel(
                "<html><i>Enter a partial product name and click Search to find matching products</i></html>");
        instructionsPanel.add(instructionsLabel, BorderLayout.CENTER);
        mainPanel.add(instructionsPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Add Enter key listener to search field
        searchField.addActionListener(e -> performSearch());
    }

    /**
     * Perform the search operation
     */
    private void performSearch() {
        String searchTerm = searchField.getText().trim();

        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a search term!",
                    "Search Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check if file exists
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this,
                    "Product data file not found!\nPlease create products first using RandProductMaker.",
                    "File Not Found",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            ArrayList<Product> matchingProducts = new ArrayList<>();

            // Read all records and find matches
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                Product product = Product.readFromRandomFile(raf);

                if (product != null) {
                    // Check if product name contains search term (case-insensitive)
                    if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                        matchingProducts.add(product);
                    }
                }
            }

            // Display results
            displayResults(searchTerm, matchingProducts);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error reading file: " + e.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Display search results in the text area
     */
    private void displayResults(String searchTerm, ArrayList<Product> products) {
        resultsArea.setText("");

        if (products.isEmpty()) {
            resultsArea.append("No products found matching: \"" + searchTerm + "\"\n");
            return;
        }

        resultsArea.append("Search Results for: \"" + searchTerm + "\"\n");
        resultsArea.append("Found " + products.size() + " matching product(s)\n");
        resultsArea.append("=".repeat(80) + "\n\n");

        // Format header
        resultsArea.append(String.format("%-8s %-35s %-40s %10s\n",
                "ID", "Name", "Description", "Cost"));
        resultsArea.append("-".repeat(80) + "\n");

        // Display each matching product
        for (Product product : products) {
            String name = product.getName();
            String description = product.getDescription();

            // Truncate if too long for display
            if (name.length() > 35) {
                name = name.substring(0, 32) + "...";
            }
            if (description.length() > 40) {
                description = description.substring(0, 37) + "...";
            }

            resultsArea.append(String.format("%-8s %-35s %-40s $%9.2f\n",
                    product.getID(),
                    name,
                    description,
                    product.getCost()));
        }

        resultsArea.append("\n");
        resultsArea.append("=".repeat(80) + "\n");

        // Scroll to top
        resultsArea.setCaretPosition(0);
    }

    /**
     * Quit the application
     */
    private void quitApplication() {
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
        SwingUtilities.invokeLater(() -> new RandProductSearch());
    }
}
