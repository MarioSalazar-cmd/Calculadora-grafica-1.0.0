import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CalculadoraVisor implements ActionListener {
	protected JFrame frame;
	
	protected JTabbedPane tabs;
	
	protected JPanel displayPanel;
	protected JPanel buttonPanel;
	protected JPanel graphPanel;
	protected JPanel graphDisplayPanel;
	
	protected JTextArea inputEquation;
	protected JTextArea equationDisplay;
	protected JTextArea graphEquation;
	
	protected CalculadoraControlador calcControl;
	
	protected Graphics2D g;
	
	protected Font displayFont;
	
	public CalculadoraVisor() {

		displayFont = new Font("Dialogo", Font.PLAIN, 18);
		
		createFrame();
		createGraphPanel();
		createDisplayPanel();
		createButtonPanel();
		createTabs();
		

		calcControl = new CalculadoraControlador();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		g = (Graphics2D) graphDisplayPanel.getGraphics();
	}
	

	protected void createFrame() {
		frame = new JFrame("Calculadora Grafica");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 2));
		frame.setSize(1250, 720);
	}

	protected void createDisplayPanel() {
		displayPanel = new JPanel();
		displayPanel.setLayout(null);
		frame.add(displayPanel, BorderLayout.WEST);
		addToDisplayPanel();
	}

	protected void createButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(6, 6));
		frame.add(buttonPanel, BorderLayout.EAST);
		addButtonsToButtonPanel();
	}
	

	protected void createGraphPanel() {
		graphPanel = new JPanel();
		graphPanel.setLayout(null);
		graphPanel.setVisible(true);
		frame.add(graphPanel, BorderLayout.WEST);
		addToGraphPanel();
		
	 }
	

	protected void createTabs() {
		tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		tabs.addTab("Ecuacion", displayPanel);
		tabs.addTab("Graf", graphPanel);
		tabs.setVisible(true);
		frame.add(tabs);
		
		tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		tabs.addTab("Teclado", buttonPanel);
		tabs.setVisible(true);
		frame.add(tabs);
	}
	

	protected void addToDisplayPanel() {		
		inputEquation = new JTextArea("Ingresa la ecuacion aqui: ", 3, 5);
		inputEquation.setLineWrap(true);
		inputEquation.setEditable(false);
		inputEquation.setFont(displayFont);
		inputEquation.setBounds(0, 0, 600, 50);
		displayPanel.add(inputEquation);

		equationDisplay = new JTextArea("Ecuaciones previas: \n", 3, 5);
		equationDisplay.setLineWrap(true);
		equationDisplay.setEditable(false);
		equationDisplay.setFont(displayFont);
		equationDisplay.setBounds(0, 60, 600, 600);
		displayPanel.add(equationDisplay);	 
	}

	protected void addButtonsToButtonPanel() {
		ArrayList<JButton> buttonList= new ArrayList<JButton>();
		
		JButton plus = new JButton("+");
		buttonList.add(plus); 
		JButton minus = new JButton("-");
		buttonList.add(minus);
		JButton multiply = new JButton("*");
		buttonList.add(multiply);
		JButton divide = new JButton("/");
		buttonList.add(divide);
		JButton openParen = new JButton("(");
		buttonList.add(openParen);
		JButton closeParen = new JButton(")");
		buttonList.add(closeParen);
		
		JButton seven = new JButton(Integer.toString(7));
		buttonList.add(seven);
		JButton eight = new JButton(Integer.toString(8));
		buttonList.add(eight);
		JButton nine = new JButton(Integer.toString(9));
		buttonList.add(nine);
		JButton power = new JButton("^");
		buttonList.add(power);
		JButton squared = new JButton("x^2");
		buttonList.add(squared);
		JButton sqrt = new JButton("sqrt");
		buttonList.add(sqrt);

		JButton four = new JButton(Integer.toString(4));
		buttonList.add(four);
		JButton five = new JButton(Integer.toString(5));
		buttonList.add(five);
		JButton six = new JButton(Integer.toString(6));
		buttonList.add(six);
		JButton sine = new JButton("sin()");
		buttonList.add(sine);
		JButton cosine = new JButton("cos()");
		buttonList.add(cosine);
		JButton tan = new JButton("tan()");
		buttonList.add(tan);
		
		JButton one = new JButton(Integer.toString(1));
		buttonList.add(one);
		JButton two = new JButton(Integer.toString(2));
		buttonList.add(two);
		JButton three = new JButton(Integer.toString(3));
		buttonList.add(three);
		JButton pi = new JButton("pi");
		buttonList.add(pi);
		JButton ln = new JButton("ln()");
		buttonList.add(ln);
		JButton e = new JButton("e");
		buttonList.add(e);
		
		JButton period = new JButton(".");
		buttonList.add(period);
		JButton zero = new JButton(Integer.toString(0));
		buttonList.add(zero);
		JButton negative = new JButton("(-)");
		buttonList.add(negative);
		JButton x = new JButton("x");
		buttonList.add(x);
		JButton enter = new JButton("Enter");
		buttonList.add(enter);
		JButton graph = new JButton("Graf");
		buttonList.add(graph);
		
		JButton delete = new JButton("Delete");
		buttonList.add(delete);
		JButton clear = new JButton("Clear");
		buttonList.add(clear);
		JButton clearAll = new JButton("<html>"+"Clear"+"<br>"+"All"+"<html>");
		buttonList.add(clearAll);
		JButton clearGraph = new JButton("<html>"+"Clear"+"<br>"+"Graf"+"<html>");
		buttonList.add(clearGraph);
		
		Font f = new Font("Dialogue", Font.PLAIN, 22);
		
		for(int j = 0; j < buttonList.size(); j++){
			JButton temp = buttonList.get(j);
			temp.setFont(f);
			temp.setActionCommand(temp.getText());
			temp.addActionListener(this);
			buttonPanel.add(temp);
		}
	}
	
	protected void addToGraphPanel() {
		graphEquation = new JTextArea("Graf: Y = ", 600, 50);
		graphEquation.setEditable(false);
		graphEquation.setFont(displayFont);
		graphEquation.setBounds(0, 0, 600, 50);
		graphPanel.add(graphEquation);
		
		graphDisplayPanel = new JPanel();
		graphDisplayPanel.setVisible(true);
		graphDisplayPanel.setLayout(null);
		graphDisplayPanel.setBounds(0, 50, 650, 650);
		graphPanel.add(graphDisplayPanel);
	}
	
	

	public void actionPerformed(ActionEvent arg0) {
		String result = arg0.getActionCommand();
		String[] fullEquation;
		String[] newText;
		
		if (result.equals("Enter")) {

			fullEquation = calcControl.update("Enter");
			String eq = fullEquation[0];
			String sol = fullEquation[1];
			
			equationDisplay.insert("\n", 22);
			equationDisplay.insert(sol, 22);
			equationDisplay.insert(" = ", 22);
			equationDisplay.insert(eq, 22);
			equationDisplay.insert("\n", 22);
			equationDisplay.insert("\n", 22);
			inputEquation.setText("");

			if (equationDisplay.getLineCount() > 24) {
				equationDisplay.setText(eq + " = " + sol);
				equationDisplay.append("\n");
			}
		}
		else if (result.equals("Graf")) {
			if (graphDisplayPanel.isShowing()) {
				String[] coordinates = calcControl.update("Graf");
				drawPoints(coordinates);	
			}
		}
		else if (result.equals("<html>"+"Clear"+"<br>"+"All"+"<html>")) {
			newText = calcControl.update(result);
			inputEquation.setText(newText[0]);
			graphEquation.setText(newText[0]);
			equationDisplay.setText("Previous equations: ");
		}
		else if (result.equals("<html>"+"Clear"+"<br>"+"Graf"+"<html>")) {
			if (graphDisplayPanel.isShowing()) {
				clearGraph();
				drawGrid();
			}
		}
		else {
			newText = calcControl.update(result);
			inputEquation.setText(newText[0]);
			graphEquation.setText(newText[0]);
		}	
	}
	

	public void drawGrid() {
		g.setColor(Color.gray);
		int boxSize = 30;
		
		for (int i=0; i<=20; i++) {
			if (i%10==0) g.setStroke(new BasicStroke(3));
			g.drawLine(boxSize*i, 0, boxSize*i, 600);
			g.drawLine(0, boxSize*i, 600, boxSize*i);
			g.setStroke(new BasicStroke(1));
		}
	}
	

	public void drawPoints(String[] coordinates) {
        drawGrid();
        for (int j=0; j<coordinates.length-1; j++){
        	g.setColor(Color.red);
        	g.drawLine(j,300-Double.valueOf(coordinates[j]).intValue(),j+1,300-Double.valueOf(coordinates[j+1]).intValue());
        }
	}

	public void clearGraph() {
		g.clearRect(0,0,600,600);
	}
}

