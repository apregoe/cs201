import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Calendar;

import javax.swing.JPanel;

public class ClockPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.blue);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString("12", 132, 35);
		g.drawString("3", 225, 125);
		g.drawString("6", 135, 215);
		g.drawString("9", 45, 125);
		//
		g.setColor(Color.black);
		g.drawOval(40, 20, 200, 200);
		
		//date and time
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		
		//
		String strTime = getTimeAsString(hour, min, sec);
		g.drawString(strTime, 112, 255);
		
		//drawing everything
		//seconds*6 since every second is 6 degrees
		g.fillArc(44, 24, 190, 190, 90 - (sec*6), 3);
		g.fillArc(67, 50, 140, 140, 90 - (min*6), 3);
		g.fillArc(95, 75, 100, 100, 90 - (hour*30), 3);
	}
	private String getTimeAsString(int hour, int min, int sec) {
		String time = "";
		if(hour == 0){
			hour = 12;
		}
		time += hour;
		
		time += ":";
		
		if(min < 10){
			time += "0";
		};
		time += min;
		time += ":";
		if(sec < 10){
			time += "0";
		}
		time += sec;
		return time;
	}
	
}
