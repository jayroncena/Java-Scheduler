package scheduler;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
//import java.util.StringTokenizer;
//import javax.swing.*;

@SuppressWarnings("serial")
class MainFrame extends Frame implements ActionListener/*,ItemListener*/{
	private int curryear;
	private int currmonth;
	private Panel[][] datepn = new Panel[6][7];
	private currYearMonth current;
	private Label ShowYearMonth = new Label();
/*	private Frame memo = new Frame();
	private Choice choi_from = new Choice();
	private Choice choi_to = new Choice();
	private TextArea ta = new TextArea("",15,30,TextArea.SCROLLBARS_VERTICAL_ONLY);
	private int pressedDate;*/

	public MainFrame() {
		super("Scheduler");
		
		addWindowListener(new WindowAdapter(){ //activate "x"button of window
			public void windowClosing(WindowEvent we)
			{
				FileProcessing FileProc = new FileProcessing();
				try {
					FileProc.WriteFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.exit(0);
			}
		});
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		setBounds((int)width/2-350, (int)height/2-200, 700, 400);
		current = ScheduleMain.ym.get(0);
		//make upper menu(< year.month >)
		Panel MonthPanel = new Panel();
		add(MonthPanel, BorderLayout.NORTH);
		MonthPanel.setFont(new Font("",Font.BOLD,25));

		Button PrevMonth = new Button("<");
		PrevMonth.addActionListener(this);
		MonthPanel.add(PrevMonth);

		MonthPanel.add(ShowYearMonth);

		Button NextMonth = new Button(">");
		NextMonth.addActionListener(this);
		MonthPanel.add(NextMonth);

		//make Date Panel as grid 7*7
		Panel DatePanel = new Panel();
		add(DatePanel, BorderLayout.CENTER);
		DatePanel.setLayout(new GridLayout(7, 7, 1, 1));

		String[] day = {"SUN","MON","TUE","WED","THU","FRI","SAT"};
		Font day_fnt = new Font("",Font.PLAIN,18);

		//make day panel
		Panel[] daypanel = new Panel[7];
		for(int i = 0; i < 7; i++){
			Label l = new Label(day[i]);
			l.setAlignment(Label.CENTER);
			daypanel[i] = new Panel();
			daypanel[i].setFont(day_fnt);
			daypanel[i].add(l,BorderLayout.CENTER);
			if(i == 0) daypanel[i].setForeground(Color.RED);
			if(i == 6) daypanel[i].setForeground(Color.BLUE);
			DatePanel.add(daypanel[i]);
		}

		curryear = current.getYear();
		currmonth = current.getMonth()+1; //January : 0, December : 11 -> January : 1, December :12

		//make date panel
		for(int i = 0; i<6; i++){
			for(int j = 0; j<7; j++){
				datepn[i][j] = new Panel();
				datepn[i][j].setFont(new Font("",Font.BOLD,20));
				datepn[i][j].setLayout(new BorderLayout());
				if(j == 0) datepn[i][j].setForeground(Color.RED);
				if(j == 6) datepn[i][j].setForeground(Color.BLUE);
				DatePanel.add(datepn[i][j],BorderLayout.CENTER);
			}
		}
		makeDates(curryear,currmonth-1);
		setResizable(false);
		setVisible(true); //visible at screen		
	}
	public void makeDates(int year, int month){ //make Date Buttons
		Calendar c = Calendar.getInstance();
		c.set(year,month,1);

		int date = 1;
		int lastdate = c.getActualMaximum(Calendar.DATE);
		int firstday = c.get(Calendar.DAY_OF_WEEK); //SUNDAY : 1, SATURDAY : 7
		currYearMonth current = null;
		
		for(int i = 0; i<ScheduleMain.ym.size(); i++){
			currYearMonth tmp = ScheduleMain.ym.get(i);
			if(tmp.getYear() == year && tmp.getMonth() == month){
				current = ScheduleMain.ym.get(i);
			}
		}
		ShowYearMonth.setText(" "+Integer.toString(curryear)+"."+Integer.toString(currmonth));

		//remove previous date buttons
		for(int i = 0; i<6; i++){
			for(int j = 0; j<7; j++){
				if(datepn[i][j].getComponentCount() != 0){
					datepn[i][j].remove(0);
					datepn[i][j].setName("0");
				}
			}
		}

		//make date buttons
		for (int i = 0; i < c.getActualMaximum(Calendar.WEEK_OF_MONTH); i++) { //week
			int j = firstday;
			do{
				datepn[i][j-1].setName(Integer.toString(date));
				Button b = new Button(Integer.toString(date));
				b.addActionListener(this);
				b.setBackground(Color.LIGHT_GRAY);
				if(!current.getSchedule(date).getTodo().isEmpty()) 
					b.setBackground(new Color(224,255,255));
				if(year == Calendar.getInstance().get(Calendar.YEAR) && 
						month == Calendar.getInstance().get(Calendar.MONTH) &&
						date == Calendar.getInstance().get(Calendar.DATE))
					b.setBackground(Color.ORANGE);
				datepn[i][j-1].add(b,BorderLayout.CENTER);
				j++;
				date++;
			}while(j<=7 && date <= lastdate);
			firstday = 1;
		}
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		boolean existflag = true; 
		// < Button Pressed
		if(e.getActionCommand() == "<"){
			if(curryear == Calendar.getInstance().get(Calendar.YEAR) 
					&& currmonth == Calendar.getInstance().get(Calendar.MONTH)+1) 
				return; //minimum
			if(currmonth != 1) --currmonth;
			else{
				--curryear;
				currmonth = 12;
			}
			for(int i = 0; i<ScheduleMain.ym.size(); i++){
				currYearMonth tmp = ScheduleMain.ym.get(i);
				if(tmp.getYear() == curryear && tmp.getMonth()+1 == currmonth){
					current = ScheduleMain.ym.get(i);
					existflag = false;
				}
			}
			if(existflag)
				ScheduleMain.ym.add(new currYearMonth(curryear,currmonth-1));
			makeDates(curryear,currmonth-1);
		}
		// > Button Pressed
		else if(e.getActionCommand() == ">"){
			//maximum
			if(curryear == Calendar.getInstance().get(Calendar.YEAR)+9 && currmonth == 12) return; 
			if(currmonth != 12) ++currmonth;
			else{
				++curryear;
				currmonth = 1;
			}
			for(int i = 0; i<ScheduleMain.ym.size(); i++){
				currYearMonth tmp = ScheduleMain.ym.get(i);
				if(tmp.getYear() == curryear && tmp.getMonth()+1 == currmonth){
					current = ScheduleMain.ym.get(i);
					existflag = false;
				}
			}
			if(existflag)
				ScheduleMain.ym.add(new currYearMonth(curryear,currmonth-1));
			makeDates(curryear,currmonth-1);
		}
		else if(Integer.parseInt(e.getActionCommand()) > 0 && Integer.parseInt(e.getActionCommand())<=31){
			new DateMenu(curryear,currmonth,Integer.parseInt(e.getActionCommand()));
			//new Memo(curryear, currmonth, Integer.parseInt(e.getActionCommand()));
		}
	}
}