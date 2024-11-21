import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GroceryManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/grocery_management";
    private static final String USER = "root"; // XAMPP default username
    private static final String PASSWORD = ""; // leave blank if no password is set

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

    // Method to create the popup window for adding grocery information
    public static void showGroceryEntryPopup() {
        // Creating a JFrame for the popup
        JFrame frame = new JFrame("Enter Grocery Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);

        // Creating the form elements
        JLabel nameLabel = new JLabel("Item Name:");
        JTextField nameField = new JTextField(15);
        
        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField(15);

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(15);

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(15);

        JButton submitButton = new JButton("Submit");

        // Adding an action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String itemName = nameField.getText();
                String category = categoryField.getText();
                String priceText = priceField.getText();
                String quantityText = quantityField.getText();

                // Validate the input
                if (itemName.isEmpty() || category.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    double price = Double.parseDouble(priceText);
                    int quantity = Integer.parseInt(quantityText);

                    // Call method to insert into database
                    insertGroceryItem(itemName, category, price, quantity);
                    JOptionPane.showMessageDialog(frame, "Grocery item added successfully!");
                    
                    // Clear the input fields after successful insertion
                    nameField.setText("");
                    categoryField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Price and quantity must be numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adding the components to the frame
        frame.setLayout(new GridLayout(5, 2));
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(categoryLabel);
        frame.add(categoryField);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(quantityLabel);
        frame.add(quantityField);
        frame.add(submitButton);

        frame.setVisible(true); // Display the popup
    }

    // Method to insert a grocery item into the database
    public static void insertGroceryItem(String itemName, String category, double price, int quantity) {
        String insertQuery = "INSERT INTO item (item_name, category, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            pstmt.setString(1, itemName);
            pstmt.setString(2, category);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);

            pstmt.executeUpdate();
            System.out.println("Grocery item inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting grocery item.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        showGroceryEntryPopup(); // Show the popup window on startup
    }
}
