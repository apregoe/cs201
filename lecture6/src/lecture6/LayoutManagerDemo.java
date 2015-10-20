package lecture6;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class LayoutManagerDemo extends JFrame {
	public static final long serialVersionUID = 1;
	//members
	private JTabbedPane jtp;
	private JPanel borderPanel, gridPanel, boxLayout, gridBagPanel;
	private JPanel flowPanel;
	public LayoutManagerDemo(){
		super("Layout Manager Demo");
		initializeComponents();
		createGUI();
		addEvents();
	}
	private void initializeComponents(){
		jtp = new JTabbedPane();
		borderPanel = getBorderPanelTab();
		flowPanel = getFlowPanelTab();
		gridPanel = getGridPanelTab();
		boxLayout = getBoxPanelTab();
		gridBagPanel = getGridBagPanelTab();
	}
	private void createGUI(){
		setSize(300,400);
		setLocation(1000, 100);
		jtp.add("BorderLayout", borderPanel);
		jtp.add("FlowLayout", flowPanel);
		jtp.add("GridLayout", gridPanel);
		jtp.add("BoxLayout", boxLayout);
		jtp.add("GridBagPanel", gridBagPanel);
		add(jtp, BorderLayout.CENTER);
		
	}
	private void addEvents(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private JPanel getBorderPanelTab(){
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		JTextField jtf1 = new JTextField("This is the north");
		JTextField jtf2 = new JTextField("This is the south");
		JButton jb = new JButton("CenterButton");
		jp.add(jtf1, BorderLayout.NORTH);
		jp.add(jtf2, BorderLayout.SOUTH);
		jp.add(jb, BorderLayout.CENTER);
		return jp;
	}
	private JPanel getFlowPanelTab(){
		JPanel jp = new JPanel();
		//jp.setLayout(new FlowLayout());
		JButton jb1 = new JButton("Hello");
		JButton jb2 = new JButton("CSCI");
		JButton jb3 = new JButton("201");
		jp.add(jb1);
		jp.add(jb2);
		jp.add(jb3);
		return jp;
		
	}
	private JPanel getGridPanelTab(){
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(4,3));
		for(int i = 1; i < 10; ++i){
			JButton jb = new JButton("" + i);
			jp.add(jb);
		}
		//adding preferred sizes
		jp.add(new JLabel(""));
		JPanel jpl = new JPanel();
		jpl.add(jp);
		jp.add(new JButton("0"));
		return jp;
	}
	private JPanel getBoxPanelTab(){
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		JButton jb1 = new JButton("button 1");
		JButton jb2 = new JButton("button 2");
		JButton jb3 = new JButton("button 3");
		jp.add(jb1);
		jp.add(Box.createGlue());
		jp.add(jb2);
		jp.add(Box.createGlue());
		jp.add(jb3);
		return jp;
	}
	
	private JPanel getGridBagPanelTab(){
		JPanel jp = new JPanel();
		jp.setLayout(new GridBagLayout());
		JButton jb1 = new JButton("button 1");
		JButton jb2 = new JButton("button 2");
		JButton jb3 = new JButton("button 3");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		jp.add(jb1, gbc);
		gbc.gridx = 1;
		jp.add(jb2,  gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		jp.add(jb3, gbc);
		jp.add(jb3, gbc);
		return jp;
	}
	public static void main(String [] args){
		LayoutManagerDemo lmd = new LayoutManagerDemo();
		lmd.setVisible(true);
	}
}









