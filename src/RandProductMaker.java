import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {

    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel idLabel;
    private JLabel costLabel;
    private JLabel recordCountLabel;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JTextField idTextField;
    private JTextField costTextField;
    private JTextField recordCountTextField;
    private JButton addButton;
    private JButton quitButton;
    private RandomAccessFile productFile;

    public RandProductMaker() throws IOException {
        super("Product Data Entry");

        nameLabel = new JLabel("Product Name:");
        descriptionLabel = new JLabel("Product Description:");
        idLabel = new JLabel("Product ID:");
        costLabel = new JLabel("Product Cost:");
        recordCountLabel = new JLabel("Record Count:");
        nameTextField = new JTextField(20);
        descriptionTextField = new JTextField(20);
        idTextField = new JTextField(20);
        costTextField = new JTextField(20);
        recordCountTextField = new JTextField(5);
        addButton = new JButton("Add Product");
        quitButton = new JButton("Quit");


        setLayout(new GridLayout(7, 2));
        add(nameLabel);
        add(nameTextField);
        add(descriptionLabel);
        add(descriptionTextField);
        add(idLabel);
        add(idTextField);
        add(costLabel);
        add(costTextField);
        add(recordCountLabel);
        add(recordCountTextField);
        add(addButton);
        add(quitButton);
        recordCountTextField.setText("0");

        addButton.addActionListener(e -> addProduct());

        quitButton.addActionListener(e -> {
            closeFile();
            System.exit(0);
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setVisible(true);

        System.setProperty("java.awt.headless", "true");
    }

    private void addProduct() {
        if (nameTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || idTextField.getText().isEmpty() ||
                idTextField.getText().isEmpty() || recordCountTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all product data fields.");
            return;
        }

        String name = nameTextField.getText();
        String description = descriptionTextField.getText();
        String id = idTextField.getText();
        double cost = Double.parseDouble(costTextField.getText());
        Product product = new Product(id, name, description, cost);
        String formattedRecord = product.getFormattedRandomAccessRecord();

        try {
            if (productFile == null) {
                productFile = new RandomAccessFile("productData.dat", "rw");
            }
            productFile.seek(productFile.length());
            productFile.writeBytes(formattedRecord);
            int recordCount = Integer.parseInt(recordCountTextField.getText());
            recordCount++;
            recordCountTextField.setText(String.valueOf(recordCount));
            nameTextField.setText("");
            descriptionTextField.setText("");
            idTextField.setText("");
            costTextField.setText("");

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while writing to the file.");
        }
    }

    private void closeFile() {
        try {
            if (productFile != null) {
                productFile.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
