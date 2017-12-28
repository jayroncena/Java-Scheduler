package scheduler;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

@SuppressWarnings("serial")
class Show extends Frame {
	Show(){}
	Show(int year, int month, int Date){
		super("Show Schedule");
		JTextArea jta = new JTextArea();
		JScrollPane scroller = new JScrollPane(jta,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jta.setEditable(false);
		jta.setLineWrap(true);
		
		addWindowListener(new WindowAdapter(){ //activate "x"button of window
			public void windowClosing(WindowEvent we){
				dispose();
			}
		});
		
		for(int i = 0; i<ScheduleMain.ym.size(); i++){
			currYearMonth tmp = ScheduleMain.ym.get(i);
			if(tmp.getYear() == year && tmp.getMonth() == month-1){
				Schedule getter = tmp.getSchedule(Date);
				if(getter.getTodo().isEmpty()) jta.setText("There is no schedule.");
				else {
					jta.setText(year+"."+month+"."+getter.getDate()+"\r\n"+
								getter.getFrom()+":00 ~ "+getter.getTo()+":00\r\n"+getter.getTodo());
					jta.setFont(new Font(getter.getFont(),getter.getStyle(),getter.getFontsize()));
					jta.setForeground(new Color(getter.getR(),getter.getG(),getter.getB()));
				}
			}
		}
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		setBounds((int)width/2-185, (int)height/2-185, 370, 370);
		add(scroller,BorderLayout.CENTER);
		setVisible(true);
		setResizable(false);
	}
}
