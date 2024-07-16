import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp extends JFrame implements ActionListener {
    private JTextField firstNumberField;
    private JTextField secondNumberField;
    private JTextField resultField; // Added field to display result
    private JButton[] digitButtons;
    private JButton addButton, subButton, mulButton, divButton, equalsButton, clearButton;

    private String operator = "";
    private double firstOperand = 0;
    private double secondOperand = 0;

    public CalculatorApp() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        JLabel firstNumberLabel = new JLabel("First Number:");
        firstNumberField = new JTextField();
        inputPanel.add(firstNumberLabel);
        inputPanel.add(firstNumberField);

        JLabel secondNumberLabel = new JLabel("Second Number:");
        secondNumberField = new JTextField();
        inputPanel.add(secondNumberLabel);
        inputPanel.add(secondNumberField);

        JLabel resultLabel = new JLabel("Result:");
        resultField = new JTextField();
        resultField.setEditable(false);
        inputPanel.add(resultLabel);
        inputPanel.add(resultField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4));

        

        addButton = new JButton("+");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        subButton = new JButton("-");
        subButton.addActionListener(this);
        buttonPanel.add(subButton);

        mulButton = new JButton("*");
        mulButton.addActionListener(this);
        buttonPanel.add(mulButton);

        divButton = new JButton("/");
        divButton.addActionListener(this);
        buttonPanel.add(divButton);

        equalsButton = new JButton("=");
        equalsButton.addActionListener(this);
        buttonPanel.add(equalsButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("+") || actionCommand.equals("-") || actionCommand.equals("*") || actionCommand.equals("/")) {
            operator = actionCommand;
            firstOperand = Double.parseDouble(firstNumberField.getText());
            secondOperand = Double.parseDouble(secondNumberField.getText());
            resultField.setText(""); // Clear result field
        } else if (actionCommand.equals("=")) {
            double result = 0;
            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        resultField.setText("Error");
                    }
                    break;
            }
            resultField.setText(String.valueOf(result));
        } else if (actionCommand.equals("Clear")) {
            firstNumberField.setText("");
            secondNumberField.setText("");
            resultField.setText("");
            firstOperand = 0;
            secondOperand = 0;
            operator = "";
        } else {
            String currentText = resultField.getText();
            resultField.setText(currentText + actionCommand);
        }
    }

    public static void main(String[] args) {
        new CalculatorApp();
    }
}
