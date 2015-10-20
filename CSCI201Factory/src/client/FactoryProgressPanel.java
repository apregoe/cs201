package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class FactoryProgressPanel extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private final String title = "Factory Progress";
	private JTable mTable;
	private TableModel mModel;
	public FactoryProgressPanel(JTable inTable) {
		// TODO Auto-generated constructor stub
		mTable = inTable;
		mModel = mTable.getModel();
		new Thread(this).start();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		int w = this.getWidth();
		int h = this.getHeight();
		Font font = new Font("Times New Roman", Font.BOLD | Font.ITALIC, w/24);
		g.setFont(font);
		
		int strWidth = g.getFontMetrics(font).stringWidth(title);
		g.drawString(title, (w - strWidth)/2, g.getFontMetrics(font).getHeight());
		
		double total = 0;
		double started = 0;
		double completed = 0;
		for(int i = 0; i < mModel.getRowCount(); ++i){
			total += (int)mModel.getValueAt(i, Constants.totalNameIndex);
			started += (int)mModel.getValueAt(i, Constants.startedIndex);
			completed += (int)mModel.getValueAt(i, Constants.completedIndex);
		}
		final int border = 20;
		int frameX = border;
		int frameY = border + g.getFontMetrics(font).getHeight();
		int frameW = w - border - border;
		int frameH = h - border - border - g.getFontMetrics(font).getHeight();
		
		g.drawRect(frameX - 1, frameY - 1, frameW -1, frameH -1);
		
		int startedWidth = (int)((started/total) * frameW);
		int completeWidth = (int)((completed/total) * frameW);
		int nonStatusWidth = frameW - (startedWidth + completeWidth);
		
		g.setColor(Color.GREEN);
		g.fillRect(frameX, frameY, completeWidth, frameH);
		g.setColor(Color.YELLOW);
		g.fillRect(frameX + completeWidth, frameY, startedWidth, frameH);
		g.setColor(Color.RED);
		g.fillRect(frameX + completeWidth + startedWidth, frameY, nonStatusWidth, frameH);	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				Thread.sleep(33);
				repaint();
			}catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}
	
}







