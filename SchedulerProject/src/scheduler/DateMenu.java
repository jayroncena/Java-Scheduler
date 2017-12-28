package scheduler;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
class DateMenu extends Frame implements ActionListener {
	int year, month, Date;
	public DateMenu(){}
	public DateMenu(int year, int month, int Date){
		super("Select Menu");
		this.year = year;
		this.month = month;
		this.Date = Date;
		
		setBackground(new Color(221,221,255));
		setSize(200,300);
		addWindowListener(new WindowAdapter(){ //activate "x"button of window
			public void windowClosing(WindowEvent we){
				ScheduleMain.f.makeDates(year, month-1);
				dispose();
			}
		});
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		setBounds((int)width/2-125, (int)height/2-150, 250, 300);
		setLayout(null);
		
		Button editTodo = new Button("Edit");
		Button newTodo = new Button("New");
		Button showTodo = new Button("Show");
		
		editTodo.setSize(150,50);
		newTodo.setSize(150,50);
		showTodo.setSize(150,50);
		
		editTodo.setLocation(60, 53);
		newTodo.setLocation(60, 133);
		showTodo.setLocation(60, 213);
		
		editTodo.addActionListener(this);
		newTodo.addActionListener(this);
		showTodo.addActionListener(this);
		
		add(editTodo);
		add(newTodo);
		add(showTodo);
		
		setVisible(true);
		setResizable(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Edit"){
			new Memo(year,month,Date,false);
		}
		else if(e.getActionCommand() == "New"){
			new Memo(year,month,Date,true);
		}
		else if(e.getActionCommand() == "Show"){
			new Show(year,month,Date);
		}
	}
}
