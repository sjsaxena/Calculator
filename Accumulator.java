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

	String mode = "accumulator";
	public void actionPerformed(ActionEvent ae)
		{
		String input = null;
		String result = null;
		String logEntry = null;
		// When input is entered...
		if(ae.getSource() == inputTextField)
			{
			// error message is removed
			errorLabel.setText("");
			// input is received
			input = inputTextField.getText().trim();
			input = input.replace(" ", "");
			// input cannot contain a letter
			if(containsLetters(input))
				{
				errorLabel.setText("Input cannot contain letters");
				}
			// input cannot begin with operator
			else if( input.startsWith("*") || input.startsWith("/") )
				{
				errorLabel.setText("Cannot begin expression with an operator");
				}
			// otherwise, send to processing
			else
				{
				if(mode.equals("accumulator"))
					{
					//check for stuff
					if(input.contains("*") || input.contains("/"))
						{
						errorLabel.setText("For * and /, please use calculator mode");
						}
					// if it is okay, send to accumulator
					else
						{
						result = accumulatorFunction(input);
						}
					}
				if(mode.equals("calculator"))
					{
					//check for stuff
					if(input.contains("="))
						{
						errorLabel.setText("For = comparisons, please use test mode");
						}
					// if it is okay, send to accumulator
					else
						{
						result = calculatorFunction(input);
						}
					}
				if(mode.equals("test"))
					{
					//check for stuff
					// if the string is okay
					result = testFunction(input);
					}
				}
			// display result
			outputTextField.setText(result);
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
				}
			if(ae.getSource() == testMode)
				{
				inputTextField.setText("");
				outputTextField.setText("");
				mode = "test";
				}
			}
		
	
	// instance variable for running total
	Double runningTotal = 0.00;
	
	public String accumulatorFunction(String entry)
		{
		Double temp = Double.parseDouble(entry);
		runningTotal += temp;
		String result = Double.toString(runningTotal);
		return result;
		}
	
	
	  
	
	public String calculatorFunction(String entry)
	  {
	  String result = "";
	  
	  CharSequence negOps[] = {"+-","--"};
	  CharSequence negOps2[] = {"-","+"};
	 
	  for(int x = 0; x < 2;x++){
		  while(entry.contains(negOps[x])){
		  entry = entry.replace(negOps[x],negOps2[x]);
		  }
		  }
	
	  
          // count our operator occurrences
		  int opCount = 0;
		  
		  // array of acceptable operators
		  // note 2 arrays for left/right precedence in order of operations
		  char ops[]  = {'*','+'};
		  char ops2[] = {'/','-'};
		  
		  // operand
		  char desiredOp = '0';
		  
		  // variables for operators
		  String op1 = null;
		  String op2 = null;
		  
		  // boolean for first operator
		 // boolean firstOp = true;
		  
		// initialize operator indexes
		  int j = 0,i = 0, k = 0,left = 0,middle = 0,right = 0;	  

		  
		  // count number of operators
		  for(i=1;i<entry.length();i++)
		    {
			for(j=0;j<ops.length;j++)
			  {
			  if((entry.charAt(i) == ops[j])||(entry.charAt(i) == ops2[j]))
			    {
				opCount += 1;  
				break;
			    }
			  }
		    }
		  System.out.println("Total number of operators in entry = " + opCount);

	  if(opCount <= 0) // no operators found
	    {
		result = entry;
		return result;
	    }
	  while(opCount > 0) // operators in expression
	    {
		
	    for(j=0;j<ops.length;j++)
	      { // loop through operators to accomplish "order of operations"
	    	
	    	
	    	for(int x = 0; x < 2;x++){
	    		while(entry.contains(negOps[x])){
	    		entry = entry.replace(negOps[x],negOps2[x]);
	    		}
	    		}
	    	
	    	
	    	if(opCount==0) break;
	  	System.out.println("entry is: " + entry);
	  	System.out.println("op count is currently at: " + opCount);
	    	char desiredOp1 = ops[j];
	    	char desiredOp2 = ops2[j];
	  	System.out.println("Desired operator on this iteration is: " + desiredOp1 + " or " + desiredOp2);
	  	
	  	System.out.println("j = " + j);
	  	System.out.println("opCount = " + opCount);
	  	
	 
	  	
	  	
	  	
	  	
	  	
	  	
	  	
		    if(!(entry.contains(Character.toString(desiredOp1))||(entry.contains(Character.toString(desiredOp2)))))
		    {    
		    continue;
		    }
		    	System.out.println("Entry does indeed contain the desired operator");
	  	for(i=1;i<entry.length();i++)
	  	  {

	  		  
	  	    
	  	  char A = entry.charAt(i);
	  	  
	  	  if((A == '*')||(A == '/')||(A == '+')||(A == '-'))
	  	    { // an operator has been found, don't know which operator
	  	    
	  		if((A != desiredOp1)&&(A != desiredOp2))
	  	      {
	  	      left = middle;
	  	      middle = i;
	  	      System.out.println("Cycling through non op, left = " + left + ", mid = " + middle);
	  	      if(opCount == 1)
	  	        {
	  	    	right = entry.length();
	  	    	break;
	  	        }
	  	      
	  	      continue;
	  	      }
	  		
	  		else if ((A == desiredOp1)||(A == desiredOp2))
	  		  {
	  		  desiredOp = A;
	  		  System.out.println("just found out our desired op is: " + desiredOp);
	  		  left = middle;
	  		  middle = i;
	  		  
	  		  System.out.println("found desired op, left = " + left + ", mid = " + middle);
	  		// System.out.println("haven't set firstOp yet, = " + firstOp);
	  		 //if(left == 0) firstOp = true;
	  		 // System.out.println("first op affected? = " + firstOp);
	  		  for(k = i+1;k<entry.length();k++)
	  		    {
	  			  char B = entry.charAt(k);
	  			  if((B == '*')||(B == '/')||(B == '+')||(B == '-'))
	  			    {
	  				if((k-middle)==1){
	  					opCount--;
	  					continue;
	  				}
	  				right = k; // third operator
	  				
	  				System.out.println("expression is: " + entry.substring(left+1,right));
	  				break;
	  			    }
	  		    }
	  		  
	  		  // reached end of entry, desiredOp must be last operand in entry
	  		  right = k;
	  		  break;
	  		  }
	  	
	  		 
	  		
	  	    }
	  	  }// for loop through entry
	  	
	      
	  	
	  	// call calculate function and update entry string with answer
	  	
	  	if(left==0)
	  	  { 
	  		//System.out.println("getting ready to find op1, desiredOp first op in entry");

	  	    System.out.println("op1" + "     " + entry.substring(0,middle));
	        op1 = entry.substring(0,middle);
	        //firstOp = false;
	  	  }
	  	else 
	  		{
	  		System.out.println(entry.charAt(left+1));
	  		System.out.println(entry.charAt(middle));
	  		System.out.println("getting ready to find op1, desiredOp not first in entry");
	  		System.out.println("op1" + "       "  +entry.substring(left+1,middle));
	  		op1 = entry.substring(left+1,middle);
	  		}
	  	System.out.println("getting ready to calculate op2");
	  	System.out.println("right = " + right);
	  	System.out.println("op2" + "      " + entry.substring(middle+1,right));
	  	op2 = entry.substring(middle+1,right);
	  	System.out.println("opCount before decrement = " + opCount);
	  	
	  	opCount--;
        System.out.println("opCount after decrement now  = " + opCount);
	  	String answer = calculate(op1,op2,desiredOp);

	  	if(left==0)
	  	{
	  	entry = entry.substring(0,left) + answer + entry.substring(right,entry.length());	
	  	}
	  	else{
	  	entry = entry.substring(0,left+1) + answer + entry.substring(right,entry.length());
	  	}
	  	
	  	
	  	
	  	left = 0;
	  	middle = 0;
	  	right = 0;
	  	i = 0;
	  	j = -1;
	  	k = 0;
	      }// for loop through ops
      }
    result = entry;
    System.out.println("Final result is: " + result);

	return result;
	}
	
	public String testFunction(String entry)
	{
	String result = "lolcatz";
	int i = entry.indexOf("=");
	String left = entry.substring(0,i);
	String right = entry.substring(i+1,entry.length());
	System.out.println("*******LEFT = " + left);
	System.out.println("*******RIGHT = " + right);
	left = calculatorFunction(entry.substring(0,i));
	right = calculatorFunction(entry.substring(i+1,entry.length()));
	if(left.equals(right)){
	result = "CORRECT!";
	System.out.println("got here");
	}
	else{
	result = "OOPS!";
	}
	return result;
	}
	
	// istance variable for handling negative numbers
	boolean negative = false;
	public String calculate(String op1, String op2, char operator)
	  {
		String answer = null;
		  Double result = 0.0;
		  System.out.println("Getting ready to parse ops to double in calculate()");
		  System.out.println("Our current desired operator is: " + operator);
		  Double leftNumber = Double.parseDouble(op1);
		  Double rightNumber = Double.parseDouble(op2);
		  System.out.println("leftNumber in calculate() is: " + leftNumber);
		  System.out.println("rightNumber in calculate() is: " + rightNumber);
		  switch(operator)
		    {
		    case '*' : result = leftNumber * rightNumber; break;
		    case '/' : result = leftNumber / rightNumber; break;
		    case '+' : result = leftNumber + rightNumber; break;
		    case '-' : result = leftNumber - rightNumber; break;	    
		    }
		/*  if(result < 0)
		    {
			negative = true;
			result = result*-1;
			answer = Double.toString(result);
			
		    }*/
		  answer = Double.toString(result);
		  System.out.println("Result of calculate = " + answer);
		  return answer;
	  }
	
	public boolean containsLetters(String input)
	{
    char[] chars = input.toCharArray();

    for (char c : chars)
    	{
        if(!(c == '0'))
        {
        	if(!(c == '1'))
	        {
        		if(!(c == '2'))
    	        {
        			if(!(c == '3'))
        	        {
        				if(!(c == '4'))
        		        {
        					if(!(c == '5'))
        			        {
        						if(!(c == '6'))
        				        {
        							if(!(c == '7'))
        					        {
        								if(!(c == '8'))
        						        {
        									if(!(c == '9'))
        							        {
        										if(!(c == '.'))
        								        {
        											if(!(c == '+'))
        									        {
        												if(!(c == '-'))
        										        {
        													if(!(c == '*'))
        											        {
        														if(!(c == '/'))
        												        {
        															if(!(c == '='))
        															{
        												        	return true;
        															}
        														}
        											        }
        										        }
        									        }
        								        }
        							        }
        						        }
        					        }
        				        }
        			        }
        		        }
        	        }
    	        }
	        }
        }
    	}
    return false;
	}
	
}
