package dsg_calculator;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class Calculator {
	static String infix = "";
	static String postfix = new String();
	static int[] isp = new int[]{12,12,13,13};
	static boolean btn_click_equal = false;
	static String[] postfix_arr = new String[20];
	//static char[] operators = new char[10];
	static Frame frm = new Frame("DSG Calculator");
	static Panel digit_panel = new Panel(new GridLayout(4,3));
	static Panel operator_panel = new Panel(new GridLayout(4,1));
	static Label label1 = new Label("0",Label.RIGHT);
	static Label label2 = new Label("DSG Calculator",Label.LEFT);
	static Stack<String> stack = new Stack<String>();
	static Stack<Double> stack1 = new Stack<Double>();
	
	public static void main(String[] args) {
		/*Layout*/
		frm.setLayout(null);
		frm.setLocation(700, 350);
		frm.setSize(480,240);
		frm.setResizable(false);
		
		label1.setBounds(50, 30, 250, 30); //300,60
		label1.setBackground(new Color(255, 119, 255));
		label1.setAlignment(Label.RIGHT);
		label1.setFont(new Font("Serief",Font.BOLD,18));
		label2.setBounds(310, 35, 200, 30);
		label2.setForeground(Color.darkGray);
		label2.setFont(new Font("Serief",Font.ITALIC + Font.BOLD,12));
		digit_panel.setBounds(50, 70, 180, 160);//50~230,280
		operator_panel.setBounds(240, 70, 60, 160);//250~320,
		
		frm.add(label1);
		frm.add(label2);
		Button btn;
		
		/*number digit*/
		for(int i = 1; i <= 9; i++) {
			btn = new Button(Integer.toString(i));
			btn.setSize(60, 40);
			btn.setForeground(Color.darkGray);
			btn.setFont(new Font(null,Font.ROMAN_BASELINE,18));
			btn.addActionListener(new ActLis());
			digit_panel.add(btn);
		}
		btn = new Button("0");
		btn.setBounds(50, 190, 120, 40);
		btn.setForeground(Color.darkGray);
		btn.setFont(new Font(null,Font.ROMAN_BASELINE,18));
		btn.addActionListener(new ActLis());
		frm.add(btn);
		btn = new Button(".");
		btn.setBounds(170, 190, 60, 40);
		btn.setForeground(Color.darkGray);
		btn.setFont(new Font(null,Font.ROMAN_BASELINE,18));
		btn.addActionListener(new ActLis());
		frm.add(btn);
		frm.add(digit_panel);
		
		/*operator digit*/
		btn = new Button("+");
		btn.setSize(60, 40);
		btn.setForeground(Color.BLUE);
		btn.setFont(new Font(null,Font.ROMAN_BASELINE,18));
		btn.addActionListener(new ActLis());
		operator_panel.add(btn);
		btn = new Button("-");
		btn.setSize(60, 40);
		btn.setForeground(Color.BLUE);
		btn.setFont(new Font(null,Font.ROMAN_BASELINE,18));
		btn.addActionListener(new ActLis());
		operator_panel.add(btn);
		btn = new Button("กั");
		btn.setSize(60, 40);
		btn.setForeground(Color.BLUE);
		btn.setFont(new Font(null,Font.ROMAN_BASELINE,18));
		btn.addActionListener(new ActLis());
		operator_panel.add(btn);
		btn = new Button("กา");
		btn.setSize(60, 40);
		btn.setForeground(Color.BLUE);
		btn.setFont(new Font(null,Font.ROMAN_BASELINE,18));
		btn.addActionListener(new ActLis());
		operator_panel.add(btn);
		frm.add(operator_panel);
		
		btn = new Button("=");
		btn.setBounds(310,70, 80, 80);
		btn.setForeground(Color.RED);
		btn.setFont(new Font(null,Font.ROMAN_BASELINE,18));
		btn.addActionListener(new ActLis());
		frm.add(btn);
		btn = new Button("Clear");
		btn.setBounds(310,150, 80, 80);
		btn.setForeground(Color.RED);
		btn.setFont(new Font(null,Font.ROMAN_BASELINE,18));
		btn.addActionListener(new ActLis());
		frm.add(btn);
		
		frm.addWindowListener(new WindowAdapter() {    
            public void windowClosing (WindowEvent e) {    
                frm.dispose();    
            }    
        });
		frm.setVisible(true);	
	}//End Main
	
	static class ActLis implements ActionListener{
		
		/*IsDigit return true*/
		public boolean IsDigit(char token) {
			switch(token) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case '.':
				return true;
			default:
				return false;
			}
		}//End IsDigit
		
		/*IsOperator*/
		public boolean IsOperator(String token) {
			try {
				switch(token.charAt(0)) {
				case '+':
				case '-':
				case 'กั':
				case 'กา':
					return true;
				default:
					return false;
				}
			}catch(NullPointerException e) {
				return true;
			}
			
		}
		
		/*Precedence of operators in stack which has been used by method InfixToPostfix*/
		public int precedence(char token) {
			switch(token) {
			case '+':
				return isp[0]; //12
			case '-':
				return isp[1]; //12
			case 'กั':
				return isp[2]; //13
			case 'กา':
				return isp[3]; //13
			default:
				return -1;
			}
		}
		
		/*Transform infix_Expression to posfix_Expression by stack*/
		public void InfixToPostfix() {
			int index = 0;
			String symbol;
			String temp;
			char token = infix.charAt(index++);
			char next_token = infix.charAt(index);
			int postfix_arr_index = 0;
			while(token != '\0') {
				if(IsDigit(token)) { //token is operand -> output to postfix_
					symbol = new String();
					symbol += token;
					while(IsDigit(next_token)) {
						symbol += next_token;		
						try {
							next_token = infix.charAt(++index);
						}catch(StringIndexOutOfBoundsException e) {
							next_token = 0;
						}
					}	
					//operands[oprnd_i++] = Double.parseDouble(symbol);
					postfix_arr[postfix_arr_index++] = symbol;
					postfix.concat(symbol);
					token = next_token;
				}else {	//token is operator -> push into stack
					symbol = new String();
					symbol += token;
					if(!stack.empty()) {
						while(precedence(stack.peek().charAt(0)) >= precedence(token)) {
							temp = stack.pop();
							postfix_arr[postfix_arr_index++] = temp;
							postfix.concat(temp);
							if(stack.empty()) {
								break;
							}
						}
					}					
					stack.push(symbol);
				}//End If-Else	
				
				try {
					if(next_token == 0) {
						break;
					}
					token = next_token;
					next_token = infix.charAt(++index);
				}catch(StringIndexOutOfBoundsException e) {
					next_token = 0;
				}
			}//End While
			while(!stack.isEmpty()) {
				temp = stack.pop();
				postfix_arr[postfix_arr_index++] = temp;
				postfix.concat(temp);
			}
			stack.clear();
		}
		
		/*Evaluate Posfix_Expression*/
		public double eval() {
			InfixToPostfix();
			int index = 0;
			
			String Token = new String();
 			Token = postfix_arr[index++];
			double op1;
			double op2;
			
			while(Token != null) {
				if(!IsOperator(Token)) { //token is operand
					stack1.push(Double.parseDouble(Token));
				}else {	//token is operator
					op2 = stack1.pop();
					op1 = stack1.pop();
					switch(Token.charAt(0)) {
					case '+':
						stack1.push(op1+op2);
						break;
					case '-':
						stack1.push(op1-op2);
						break;
					case 'กั':
						stack1.push(op1*op2);
						break;
					case 'กา':
						try {
							stack1.push(op1/op2);
						}catch(ArithmeticException e) {
							stack1.push(op1/1.0);
						}						
						break;
					default:
						break;
					}
				}//End If-Else
				Token = postfix_arr[index++];
			}//End While			
			return stack1.pop();
		}//End eval_
		
		/*Method Button Click*/
		public void actionPerformed(ActionEvent e) {
			String ans;
			Button btn = (Button)e.getSource();
			if(btn_click_equal) {
				infix = "";
				btn_click_equal = false;
			}
			switch(btn.getLabel()) {
			case "0":
				if(infix.length() == 0) {
					break;
				}
				infix += '0';
				label1.setText(infix);
				break;
			case "1":
				infix += '1';
				label1.setText(infix);
				break;
			case "2":
				infix += '2';
				label1.setText(infix);
				break;
			case "3":
				infix += '3';
				label1.setText(infix);
				break;
			case "4":
				infix += '4';
				label1.setText(infix);
				break;
			case "5":
				infix += '5';
				label1.setText(infix);
				break;
			case "6":
				infix += '6';
				label1.setText(infix);
				break;
			case "7":
				infix += '7';
				label1.setText(infix);
				break;
			case "8":
				infix += '8';
				label1.setText(infix);
				break;
			case "9":
				infix += '9';
				label1.setText(infix);
				break;
			case ".":
				infix += '.';
				label1.setText(infix);
				break;
			case "+":
				if(infix.length() == 0) {
					break;
				}
				infix += '+';
				label1.setText(infix);
				break;
			case "-":
				if(infix.length() == 0) {
					break;
				}
				infix += '-';
				label1.setText(infix);
				break;
			case "กั":
				if(infix.length() == 0) {
					break;
				}
				infix += 'กั';
				label1.setText(infix);
				break;
			case "กา":
				if(infix.length() == 0) {
					break;
				}
				infix += 'กา';
				label1.setText(infix);
				break;
			case "Clear":
				infix = "";
				label1.setText("0");
				stack1.clear();
				postfix_arr = new String[20];
				break;
			case "=":
				ans = Double.toString(eval());
				stack1.clear();
				label1.setText(ans);
				infix = "";
				postfix_arr = new String[20];
				btn_click_equal = true;
				break;
			default:
				break;
			}//End switch
		}//End actionPerformed Method

	}//End ActLis Class

}//End Class
