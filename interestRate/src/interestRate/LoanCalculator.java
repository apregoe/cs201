package interestRate;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class LoanCalculator extends JFrame {
	//member variables
	private static final long serialVersionUID = 1L;
	private JLabel principalLabel, interestLabel, amountLabel;
	private JTextField principalTF, interestTF;
	private JButton calculateButton;
	private JMenuBar jmb;
	private JMenu optionsMenu;
	private JMenuItem calculateMenuItem;
	private JPopupMenu jpm;
	private JMenuItem calculateMenuItemForPupUp;
	public LoanCalculator(){
		super("Loan Calculator");
		initializeCOmponents();
		createGUI();
		addEvents();
	}
	private void initializeCOmponents() {
		// TODO Auto-generated method stub
		principalLabel = new JLabel("Principal");
		interestLabel = new JLabel("Interest");
		amountLabel = new JLabel("Amount Paid: ");
		principalTF = new JTextField(20);
		interestTF = new JTextField(20);
		calculateButton = new JButton("Calculate");
		//initialize menu
		jmb = new JMenuBar();
		optionsMenu = new JMenu("Options");
		optionsMenu.setMnemonic('o');
		calculateMenuItem = new JMenuItem("Calculate");
		calculateMenuItem.setMnemonic('c');
		calculateMenuItem.setAccelerator(KeyStroke.getKeyStroke(
					KeyEvent.VK_P, ActionEvent.META_MASK));
		ImageIcon icon = new ImageIcon("usc.jpg");
		calculateMenuItem.setIcon(icon);
		
		jpm = new JPopupMenu();
		calculateMenuItemForPupUp = new JMenuItem("Calculate");
	}
	private void createGUI() {
		// TODO Auto-generated method stub
		setSize(300,300);
		setLocation(750, 50);
		super.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(principalLabel);
		add(principalTF);
		add(interestLabel);
		add(interestTF);
		add(calculateButton);
		add(amountLabel);
		//menu
		optionsMenu.add(calculateMenuItem);
		jmb.add(optionsMenu);
		jpm.add(calculateMenuItemForPupUp);
		setJMenuBar(jmb);
	}
	private void addEvents() {
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calculateButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				actionForCalculate();
				
			}
			
		});
		//menu
		//Calculate Action
		calculateMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				actionForCalculate();
			}
		});
		calculateMenuItemForPupUp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				actionForCalculate();
			}
		});
		
		principalTF.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me){
				if(me.getButton() == MouseEvent.BUTTON3){
						jpm.show(principalTF, me.getX(), me.getY());
				}
			}
		});
	}
	
	private void actionForCalculate(){
		double principal = Double.parseDouble(principalTF.getText());
		double interest = Double.parseDouble(interestTF.getText());
		double amount = principal+principal*interest;
		amountLabel.setText("Amount Paid: " + amount);
	}
	
	
	public static void main(String args[]){
		LoanCalculator lc = new LoanCalculator();
		lc.setVisible(true);
	}
}















