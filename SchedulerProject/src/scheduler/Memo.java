package scheduler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.StringTokenizer;
import javax.swing.*;

@SuppressWarnings("serial")
class Memo extends Frame implements ActionListener {
	public static JTextArea ta = new JTextArea("",15,30);
	private Choice choice_from = new Choice();
	private Choice choice_to = new Choice();
	private int year, month, Date;

	public Memo(){}
	public Memo(int year, int month, int Date, boolean flag){
		this.year = year;
		this.month = month;
		this.Date = Date;
		int from = 0, to = 0;
		addWindowListener(new WindowAdapter(){ //activate "x"button of window
			public void windowClosing(WindowEvent we){
				dispose();
			}
		});
		ta.setFont(new Font("",0,12));
		ta.setForeground(Color.BLACK);
		ta.setLineWrap(true);
		JScrollPane scroller = new JScrollPane(ta,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
		
		for(int i = 0; i<ScheduleMain.ym.size(); i++){
			currYearMonth tmp = ScheduleMain.ym.get(i);
			if(tmp.getYear() == year && tmp.getMonth() == month-1){
				if(flag){
					ScheduleMain.ym.get(i).setSchedule(Date, 0, 0, "","",0,0,0,0,12);
					ta.setText("");
					setTitle("New schedule");
				}
				else{
					Schedule getter = tmp.getSchedule(Date);
					setTitle("Edit schedule");
					if(getter.getTodo() == null) ta.setText("");
					else {
						ta.setText(getter.getTodo());
						ta.setFont(new Font(getter.getFont(),getter.getStyle(),getter.getFontsize()));
						ta.setForeground(new Color(getter.getR(),getter.getG(),getter.getB()));
						from = getter.getFrom();
						to = getter.getTo();
					}
				}
			}
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		setBounds((int)width/2-185, (int)height/2-185, 370, 370);
		
		Panel btns = new Panel();
		btns.setBackground(new Color(221,221,255));
		
		Button save = new Button("save");
		Button exit = new Button("exit");
		Button font = new Button("font");
		
		save.addActionListener(this);
		exit.addActionListener(this);
		font.addActionListener(this);
		
		btns.add(save);
		btns.add(exit);
		btns.add(font);
		
		
		for(int i = 0 ;i<=24;i++)
			choice_from.addItem(i+":00");
		for(int i = 0 ;i<=24;i++)
			choice_to.addItem(i+":00");
		
		Panel choice = new Panel();
		choice.setBackground(new Color(221,221,255));
		choice.add(new Label("from : "));
		choice.add(choice_from);
		choice.add(new Label("to : "));
		choice.add(choice_to);
		choice_from.select(from);
		choice_to.select(to);
		
		add(choice,BorderLayout.NORTH);
		add(btns,BorderLayout.SOUTH);
		ta.setEditable(true);
		add(scroller,BorderLayout.CENTER);	
		setResizable(false);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "save"){
			String content = new String(ta.getText());
			int from,to;
			Font font = ta.getFont();
			Color color = ta.getForeground();
			int r = color.getRed();
			int g = color.getGreen();
			int b = color.getBlue();
			
			StringTokenizer token = new StringTokenizer(choice_from.getItem(choice_from.getSelectedIndex()),":");
			from = Integer.parseInt(token.nextToken());
			token = new StringTokenizer(choice_to.getItem(choice_to.getSelectedIndex()),":");
			to=Integer.parseInt(token.nextToken());
			
			if(from>to){JOptionPane.showMessageDialog(this,
					"The starting time must be earlier than the end time!");}
			else{
				for(int i = 0; i<ScheduleMain.ym.size(); i++){
					currYearMonth tmp = ScheduleMain.ym.get(i);
					if(tmp.getYear() == year && tmp.getMonth() == month-1){
						ScheduleMain.ym.get(i)
						.setSchedule(Date, from, to, content,font.getFamily()
									,r,g,b,font.getStyle(),font.getSize());
					}
				}
				
				dispose();
			}
			
		}

		//exit memo frame
		else if(e.getActionCommand() == "exit"){
			dispose();
		}
		
		else if(e.getActionCommand() == "font"){
			new SetFont();
		}
	}
	
}
