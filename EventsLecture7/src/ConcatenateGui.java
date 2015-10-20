import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConcatenateGui extends JFrame{
	public static final long serialVersionUID = 1;
	private JLabel text1Label, text2Label;
	private JTextField text1TF, text2TF, concatenateTF;
	private JButton concatenateButton;
	
	public ConcatenateGui(){
		super("ConcatenateGUI");
		initializeComponents();
		createGUI();
		addEvents();
	}

	private void initializeComponents() {
		// TODO Auto-generated method stub
		text1Label = new JLabel("Text 1");
		text2Label = new JLabel("Text 2");
		text1TF = new JTextField(20);
		text2TF = new JTextField(20);
		concatenateTF = new JTextField(25);
		concatenateButton = new JButton("Concatenate");
	}
	private void createGUI() {
		// TODO Auto-generated method stub
		setSize(300, 200);
		setLocation(750, 50);
		setLayout(new GridLayout(4,1));
		JPanel firstPanel = new JPanel();
		firstPanel.add(text1Label);
		firstPanel.add(text1TF);
		JPanel secondPanel = new JPanel();
		secondPanel.add(text2Label);
		secondPanel.add(text2TF);
		JPanel thirdPanel = new JPanel();
		thirdPanel.add(concatenateButton);
		JPanel fourthPanel = new JPanel();
		fourthPanel.add(concatenateTF);
		add(firstPanel);
		add(secondPanel);
		add(thirdPanel);
		add(fourthPanel);
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//class within a method
		/*
		class ConcatenareAdapter implements ActionListener {//can only be accessed within the package
			public void actionPerformed(ActionEvent ae){
				concatenateTF.setText(text1TF.getText() + text2TF.getText());
			}
		}*/
		//ActionListener ca = new ConcatenareAdapter();
		concatenateButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				concatenateTF.setText(text1TF.getText() + text2TF.getText());
			}
		});
	}

	public static void main(String args[]){
		ConcatenateGui cgui = new ConcatenateGui();
		cgui.setVisible(true);
	}
	//only accessible within ConcatenateGUI
	/*
	private class ConcatenareAdapter implements ActionListener {//can only be accessed within the package
		public void actionPerformed(ActionEvent ae){
			concatenateTF.setText(text1TF.getText() + text2TF.getText());
		}
	}
	*/
}

//Will also compile if the would be without comments
/*
//many classes within a file.. No "public class" but "class"
class ConcatenareAdapter implements ActionListener {//can only be accessed within the package
	private JTextField jtf1, jtf2, jtf3;
	public ConcatenareAdapter(JTextField jtf1,JTextField jtf2,JTextField jtf3){
		this.jtf1 = jtf1;
		this.jtf2 = jtf2;
		this.jtf3 = jtf3;
	}
	public void actionPerformed(ActionEvent ae){
		jtf3.setText(jtf1.getText() + this.jtf2.getText());
	}
}*/
