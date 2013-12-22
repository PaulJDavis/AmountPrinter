package amountPrinter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class AmountPrinter extends JFrame implements ActionListener{
	private static final long serialVersionUID = -7957484529568103050L;

	//Text entry field for the amount to convert
	private JTextField myTextEntry = new JTextField();
	private JLabel myTextEntryLabel= new JLabel("Please enter a monetary value without commas:");
	private JButton myConvertBtn = new JButton("Convert");
	private JTextArea myOutputArea = new JTextArea("");
	
	//Map of numbers to their English equivalent 
	List<String> onesList = new ArrayList<String>();
	List<String> tensList = new ArrayList<String>();
	List<String> teensList = new ArrayList<String>();
	
	public AmountPrinter() {
		setTitle("Amount Printer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Initialize the one's list
		onesList.add("one");
		onesList.add("two");
		onesList.add("three");
		onesList.add("four");
		onesList.add("five");
		onesList.add("six");
		onesList.add("seven");
		onesList.add("eight");
		onesList.add("nine");
		
		//Initialize the ten's list
		tensList.add("teen");
		tensList.add("twenty");
		tensList.add("thirty");
		tensList.add("fourty");
		tensList.add("fifty");
		tensList.add("sixty");
		tensList.add("seventy");
		tensList.add("eighty");
		tensList.add("ninety");
		
		//Initialize the teens list
		teensList.add("eleven");
		teensList.add("twelve");
		teensList.add("thirteen");
		teensList.add("fourteen");
		teensList.add("fifteen");
		teensList.add("sixteen");
		teensList.add("seventeen");
		teensList.add("eighteen");
		teensList.add("nineteen");
		
		//Add the instruction area
		add(myTextEntryLabel, BorderLayout.NORTH);
		
		//Construct the text entry section
		JPanel entryPanel = new JPanel();
		entryPanel.setLayout(new FlowLayout());
		myTextEntry.setColumns(30);
		entryPanel.add(myTextEntry);
		myConvertBtn.addActionListener(this);
		entryPanel.add(myConvertBtn);
		add(entryPanel, BorderLayout.CENTER);
		
		//Add the number output area
		myOutputArea.setSize(100, 20);
		add(myOutputArea, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		boolean inputValid = false;
		String text = myTextEntry.getText();
        try {//Verify that the operator entered a monetary value (#.##)
            BigDecimal value = new BigDecimal(text);
            if(value.scale() == 2 && text.length() > 3)
            	inputValid = true;
        } 
        catch (NumberFormatException e) {
        	//Number exception
        }
        
        if(inputValid) {
            myTextEntryLabel.setText("Please enter a monetary value without commas:");
            myTextEntryLabel.setForeground(Color.black);
            
            //String to be displayed
            String outputString = "";
            
            boolean dontProcessWholeNum = true;
            for(int i = 0; i < text.length(); i++) {//Checking for need to process the whole number part of the value
            	dontProcessWholeNum = dontProcessWholeNum && (text.charAt(i) != '0');
            	if(text.charAt(i) == '.')
            		break;
            }
            if(dontProcessWholeNum) {//If there is a need to process things to the left of the decimal
            	for(int i = 0; i <= text.length() - 4; i++) {//Everything to the left of decimal
            		if(text.charAt(i) != '0') {//Skip all zeros
            			if(i <= text.length() - 5 && (text.length() - i + 1)%3 == 0) {//Ten's place
            				if(text.charAt(i) == '1') {//Handling the teens
            					outputString += " " + teensList.get(Integer.parseInt(String.valueOf(text.charAt(++i))) - 1);
            				}
            				else {//20s, 30s, 40s, etc
            					outputString += " " + tensList.get(Integer.parseInt(String.valueOf(text.charAt(i))) - 1);
            				}
            			}
            			else {//Singular character
            				outputString +=  " " + onesList.get(Integer.parseInt(String.valueOf(text.charAt(i))) - 1);
            			}
            			
            			if(i == 0) {//Capitalizing the first character
            				outputString = outputString.substring(1); //Remove the first space char
            				outputString = Character.toUpperCase(outputString.charAt(0)) + outputString.substring(1);
            			}
            			
            			if(i <= text.length() - 6 && (text.length() - i)%3 == 0) {//Hundred's place
            				outputString += " hundred";
            			}
            			else if(i == text.length() - 7) {//thousands place
            				outputString += " thousand";
            			}
            			else if(i == text.length() - 10) {//millions place
            				outputString += " million";
            			}
            			else if(i == text.length() - 13) {//billions place
            				outputString += " billion";
            			}
            			else if(i == text.length() - 16) {//trillions place
            				outputString += " trillion";
            			}
            			//This pattern could be expanded if necessary into a list form
            		}
            	}
            	outputString += " and ";
            }
            
            //Adding everything after the decimal
            outputString += String.valueOf(text.charAt(text.length() - 2)) + String.valueOf(text.charAt(text.length() - 1));
            outputString += "/100 dollars";
            myOutputArea.setText(outputString);
        }
        else {
        	//Alert the operator
            myTextEntryLabel.setText("Please enter a valid monetary value without commas:");
            myTextEntryLabel.setForeground(Color.red);
            myOutputArea.setText("");
        }
	}
	
	public static void main(String[] args) {
		JFrame display = new AmountPrinter();
		display.pack();
		display.setVisible(true);
	}

}
