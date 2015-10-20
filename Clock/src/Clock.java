import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Clock  extends JFrame{
	public static final long serialVersionUID = 1;
	public static void main(String args[]){
		@SuppressWarnings("unused")
		Clock c = new Clock();
	}
	
	
	//members
	private ClockPanel clockPanel;
	public Clock(){
		super("Clock");
		initializeComponents();
		createGUI();
		addEvents();
		super.setVisible(true);
		while(true){
			clockPanel.repaint();
			try{
				Thread.sleep(1000);
			} catch(InterruptedException ie){
				System.out.println("IE: " + ie.getMessage());
			}
		}
	}

	private void initializeComponents() {
		// TODO Auto-generated method stub
		clockPanel = new ClockPanel();
	}

	private void createGUI() {
		// TODO Auto-generated method stub
		setSize(300,300);
		setLocation(750,50);
		add(clockPanel, BorderLayout.CENTER);
	}
	
	private void addEvents() {
		// TODO Auto-generated method stub
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
