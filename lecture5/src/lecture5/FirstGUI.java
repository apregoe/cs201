package lecture5;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FirstGUI extends JFrame {
	public static final long serialVersionUID = 1;
	public FirstGUI(){
		super("Default title");
		setSize(300,200);
		setLocation(10,20);
		setResizable(false);
		JPanel northPanel = new JPanel();
		//northPanel.setLayout((new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//It closes it, not minimizes it
		JButton helloButton = new JButton("Que telaraña");
		add(helloButton, BorderLayout.CENTER);//takes a component as a parameter
		JButton worldButton = new JButton("Te la chupooo");
		northPanel.add(worldButton, BorderLayout.CENTER);//at this point the button is stacked over the other. We need to specify the layout
		//Default layout manager of JFrame is border layout
		JButton northButton = new JButton("el bananero");
		northPanel.add(northButton, BorderLayout.NORTH);
		add(northPanel, BorderLayout.NORTH);
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FirstGUI fGui = new FirstGUI();
		fGui.setVisible(true);
	}

}
