import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class currency_converter implements ActionListener {

    JFrame frame;
    JPanel panel1, panel2, panel3;
    JLabel label1,label2,label3,label4,label5;
    JTextField textField1,textField2;
    JButton convert,clear;
    JComboBox<String> box1, box2;

    currency_converter() {
        createFrame();
        createPanel();
        createLabel();
        createTextField();
        createButton();
        createCombobox();
        frame.setVisible(true);
    }

    void createFrame() {
        frame = new JFrame("Currency Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 475);
        frame.setLayout(null);
    }

    void createPanel() {
        panel1 = new JPanel();
        panel1.setBounds(0, 0, 800, 50);
        panel1.setBackground(new Color(30, 144, 255)); 

        panel2 = new JPanel();
        panel2.setBounds(0, 50, 400, 400);
        panel2.setBackground(new Color(255, 239, 204)); 
        panel2.setLayout(null);

        panel3 = new JPanel();
        panel3.setBounds(400, 50, 400, 400);
        panel3.setBackground(new Color(210, 180, 140)); 
        panel3.setLayout(null);

        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
    }

    void createLabel() {
        label1 = new JLabel("CURRENCY CONVERTER", JLabel.CENTER); 
        label1.setFont(new Font("Arial", Font.BOLD, 24)); 
        label1.setForeground(Color.WHITE); 
        label1.setBounds(0, 0, 800, 50); 
    
        label2 = new JLabel("From:");
        label2.setFont(new Font("Arial", Font.PLAIN, 18)); 
        label2.setBounds(30, 50, 50, 30); 
    
        label3 = new JLabel("Enter amount:");
        label3.setFont(new Font("Arial", Font.PLAIN, 18)); 
        label3.setBounds(30, 150, 175, 30); 
    
        label4 = new JLabel("To:");
        label4.setFont(new Font("Arial", Font.PLAIN, 18)); 
        label4.setBounds(30, 50, 50, 30); 
    
        label5 = new JLabel("Converted amount:");
        label5.setFont(new Font("Arial", Font.PLAIN, 18)); 
        label5.setBounds(30, 150, 175, 30); 
    
        panel1.add(label1);
        panel2.add(label2);
        panel2.add(label3);
        panel3.add(label4);
        panel3.add(label5);
    }

    void createTextField(){

        textField1 = new JTextField();
        textField1.setBounds(175, 150, 175, 30);
        textField1.setFont(new Font("Arial", Font.PLAIN, 16));
        textField1.setBackground(Color.WHITE);

        textField2 = new JTextField();
        textField2.setBounds(205, 150, 175, 30);
        textField2.setFont(new Font("Arial", Font.PLAIN, 16));
        textField2.setBackground(Color.WHITE);
        textField2.setEditable(false);

        panel2.add(textField1);
        panel3.add(textField2);
    }

    void createButton(){

        convert = new JButton("convert");
        clear = new JButton("clear");
      
        convert.setBounds(140, 270, 120, 40); 
        convert.setFocusable(false);
      
        clear.setBounds(140, 270, 120, 40);
        clear.setFocusable(false);
      
        convert.addActionListener(this);
        clear.addActionListener(this);
      
        panel2.add(convert);
        panel3.add(clear);
    }

    void createCombobox(){
        
        String[] currencies = {"USD", "INR", "EUR", "GBP"}; 

        box1 = new JComboBox<>(currencies);
        box2 = new JComboBox<>(currencies);
        box1.setBounds(100, 50, 100, 30); 
        box2.setBounds(80, 50, 100, 30); 

        panel2.add(box1);
        panel3.add(box2);
    }
    
    void convert(){

        float amount;
        amount = Float.parseFloat(textField1.getText());

     
        String fromCurrency = (String) box1.getSelectedItem();
        String toCurrency = (String) box2.getSelectedItem();
        float convertedAmount = 0;
        switch (fromCurrency + "-" + toCurrency) {
            case "USD-INR":
                convertedAmount = amount * 84.13f; 
                break;
            case "USD-EUR":
                convertedAmount = amount * 0.92f; 
                break;
            case "USD-GBP":
                convertedAmount = amount * 0.77f; 
                break;
            case "INR-USD":
                convertedAmount = amount * 0.012f; 
                break;
            case "INR-EUR":
                convertedAmount = amount * 0.011f; 
                break;
            case "INR-GBP":
                convertedAmount = amount * 0.0092f; 
                break;
            case "EUR-USD":
                convertedAmount = amount * 1.09f; 
                break;
            case "EUR-INR":
                convertedAmount = amount * 91.10f; 
                break;
            case "EUR-GBP":
                convertedAmount = amount * 0.84f; 
                break;
            case "GBP-USD":
                convertedAmount = amount * 1.29f; 
                break;
            case "GBP-INR":
                convertedAmount = amount * 108.71f; 
                break;
            case "GBP-EUR":
                convertedAmount = amount * 1.19f; 
                break;
            default:
                convertedAmount = amount;
                JOptionPane.showMessageDialog(frame, "Conversion not supported.");
               
        }

        textField2.setText(String.format("%.2f", convertedAmount));
    }



    public void actionPerformed(ActionEvent e) {

           if (e.getSource() == convert) {
            convert();
        } else if (e.getSource() == clear) {
            textField1.setText("");
            textField2.setText("");
            box1.setSelectedIndex(0); 
            box2.setSelectedIndex(0); 
        }
    
  
    }
    


    public static void main(String[] args) {
        new currency_converter();
    }
}
