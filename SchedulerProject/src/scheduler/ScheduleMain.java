package scheduler;

import java.util.ArrayList;
import java.util.Calendar;

class currYearMonth {
	private int year;
	private int month; //saves 0~11. 0 : January
	private ArrayList<Schedule> sched = new ArrayList<Schedule>(); //generate sched as number of date
	public currYearMonth(int year, int month){
		this.year = year;
		this.month = month;
		Calendar c = Calendar.getInstance();
		c.set(year,month,1);
		//generate sched
		for(int i = 1; i<=c.getActualMaximum(Calendar.DATE);i++){
			sched.add(new Schedule(i));
		}
	}
	
	//if you need, add more methods
	public int getYear(){return year;}
	public int getMonth(){return month;}
	public Schedule getSchedule(int date){return sched.get(date-1);}
	public void setSchedule(int date, int from, int to, String todo, String font, int r, int g, int b, int style, int fontsize){ 
		sched.get(date-1).addTodo(from, to, todo,font,r,g,b,style,fontsize);
	}
	public int getSchedNum(){return sched.size();}
	public void setYear(int year){this.year = year;}
	public void setMonth(int month){this.month = month;}
}

class Schedule{
	private int date;
	private String todo;
	private int from, to, fontsize;
	private String font;
	private int r, g, b, style;
	
	public Schedule(){}
	public Schedule(int date){
		this.date = date; 
		todo = "";
		fontsize = 12;
		r = 0;
		g = 0;
		b = 0;
		style = 0;
		font = "";
	}
	

	public int getDate(){return date;}
	public void addTodo(int from, int to, String todo, String font, int r, int g, int b, int style, int fontsize){
		this.from = from;
		this.to = to;
		this.todo = todo;
		this.font = font;
		this.r = r;
		this.g = g;
		this.b = b;
		this.fontsize = fontsize;
		this.style = style;
	}
	public String getTodo(){return todo;}
	
	public int getFontsize() {return fontsize;}
	public String getFont() {return font;}
	public int getR() {return r;}
	public int getG() {return g;}
	public int getB() {return b;}
	public int getStyle(){return style;}
	public int getFrom(){return from;}
	public int getTo(){return to;}
}

public class ScheduleMain{
	static ArrayList<currYearMonth> ym = new ArrayList<currYearMonth>();
	static MainFrame f;
	public static void main(String args[]){
		ym.add(new currYearMonth(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)));
		FileProcessing FileProc = new FileProcessing();
		FileProc.ReadFile();
		f = new MainFrame(); //make frame
	}
}