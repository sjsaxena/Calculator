import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Accumulator implements ActionListener
{
	// Main - loads program object
	public static void main(String[] args){
		new Accumulator();
		}

	// newLine
	private String newLine = System.getProperty("line.separator");
	
	//GUI Objects
	private JFrame       window          = new JFrame("Calculator");
	private JTextField   inputTextField  = new JTextField(20);
	private JTextField   outputTextField = new JTextField(20);
	private JTextArea    logTextArea     = new JTextArea();
	private JScrollPane  logScrollPane   = new JScrollPane(logTextArea);
	private JLabel       inputLabel      = new JLabel("Enter input");
	private JLabel       outputLabel     = new JLabel(" Result -> ");
	private JLabel       errorLabel      = new JLabel("");
	private JButton      clearButton     = new JButton("Clear");
	private JPanel       topPanel        = new JPanel(new GridBagLayout());
	GridBagConstraints   c               = new GridBagConstraints();
	private JRadioButton accumulatorMode = new JRadioButton("Accumulator", true);
	private JRadioButton calculatorMode  = new JRadioButton("Calculator", false);
	private JRadioButton testMode        = new JRadioButton("Test Mode", false);
	private ButtonGroup  bGroup          = new ButtonGroup();
	private JPanel       bottomPanel     = new JPanel();
	
	public Accumulator()
		{
		// Build GUI
		
		//topPanel is created using GridBag to make error message appear on new row
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		topPanel.add(inputLabel, c);	
		c.gridx = 1;
		topPanel.add(inputTextField, c);
		c.gridx = 2;
		topPanel.add(outputLabel, c);
		c.gridx = 3;
		topPanel.add(outputTextField, c);
		c.gridx = 4;
		topPanel.add(clearButton, c);	
		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 1;
		topPanel.add(errorLabel,c);
		
		
		
		bottomPanel.setLayout(new GridLayout(1,3));
		bottomPanel.add(accumulatorMode);
		bottomPanel.add(calculatorMode);
		bottomPanel.add(testMode);
		bottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Mode Select"));
		
		window.getContentPane().add(topPanel, "North");
		window.getContentPane().add(logScrollPane, "Center");
		window.getContentPane().add(bottomPanel, "South");
		
		// Set GUI attributes
		bGroup.add(accumulatorMode);
		bGroup.add(calculatorMode);
		bGroup.add(testMode);
		logTextArea.setEditable(false);
		logTextArea.setFont(new Font("default", Font.BOLD, 20));
		clearButton.setBackground((Color.black));
		clearButton.setForeground(Color.yellow);
		errorLabel.setForeground(Color.red);
		inputTextField.setEditable(true);
		outputTextField.setEditable(false);
		window.setLocation(10, 10); // horizontal, vertical
		window.setSize(800, 500); // width,height in pixels
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setVisible(true);
	    logTextArea.setText(""); //  clear log
		
		// Set event notification
		inputTextField.addActionListener(this);
		clearButton.addActionListener(this);
		accumulatorMode.addActionListener(this);
		calculatorMode.addActionListener(this);
		testMode.addActionListener(this);
		
		}

	String entry;
	String mode = "accumulator";
	public void actionPerformed(ActionEvent ae)
		{
		// When input is entered...
		if(ae.getSource() == inputTextField)
			{
			// error message is removed
			errorLabel.setText("");
			entry = inputTextField.getText();
			// add text to log
			logTextArea.append(mode + ": " + entry + newLine);
			outputTextField.setText(entry);
			}
		
		// When the clear button is pressed...
		if(ae.getSource() == clearButton)
			{
			// clear fields
			inputTextField.setText("");
			outputTextField.setText("");
			}
		
		// If a mode radio button is pressed, clear the fields and set the mode
		if(ae.getSource() == accumulatorMode)
			{
			inputTextField.setText("");
			outputTextField.setText("");
			mode = "accumulator";
			}
		if(ae.getSource() == calculatorMode)
			{
			inputTextField.setText("");
			outputTextField.setText("");
			mode = "calculator";
			errorLabel.setText("Error! You have done something very stupid!");
			}
		if(ae.getSource() == testMode)
			{
			inputTextField.setText("");
			outputTextField.setText("");
			mode = "test";
			}
		}

	
}
