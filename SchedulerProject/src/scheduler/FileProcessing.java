package scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class FileProcessing {
	public void ReadFile(){
		File file = new File("Scheduler.txt");
		if(!file.exists()) return;
		int curr_index;
		Scanner s;
		try {
			s = new Scanner(file);
			int total_month = s.nextInt();
			for(int i = 0; i<total_month; i++){
				int year = s.nextInt();
				int month = s.nextInt();
				int schednum = s.nextInt();
				boolean existflag = false;
				for(curr_index = 0; curr_index<ScheduleMain.ym.size(); curr_index++){
					currYearMonth tmp = ScheduleMain.ym.get(curr_index);
					if(tmp.getYear() == year && tmp.getMonth()+1 == month){
						existflag = true;
						break;
					}
				}
				if(!existflag){
					ScheduleMain.ym.add(new currYearMonth(year,month-1));
				}
				for(int j = 0; j<schednum; j++){
					int date = s.nextInt();
					int from = s.nextInt();
					int to = s.nextInt();
					int r = s.nextInt();
					int g = s.nextInt();
					int b = s.nextInt();
					int style = s.nextInt();
					int fontsize = s.nextInt();
					String font = s.next();
					String todo = s.nextLine().trim();
					ScheduleMain.ym.get(curr_index).setSchedule(date, from, to, todo,font,r,g,b,style,fontsize);
				}
			}
		}catch(Exception e){}
	}
	
	public void WriteFile() throws IOException{
		FileWriter fw = null;
		int total_month = ScheduleMain.ym.size();
		
		try {
			fw = new FileWriter("Scheduler.txt");

		} catch (IOException e) {}
		//first line : total month
		//second line ~ : year month
		//next : date todo
		// ...
		fw.write(Integer.toString(total_month)+"\r\n");
		for(int i = 0; i < total_month; i++){
			currYearMonth tmp = ScheduleMain.ym.get(i);
			int year = tmp.getYear();
			int month = tmp.getMonth()+1;
			int totalsched = 0;
			for(int j = 1; j <= tmp.getSchedNum(); j++){
				Schedule schedtmp = tmp.getSchedule(j);
				if(!schedtmp.getTodo().isEmpty()){
					totalsched++;
				}
			}
			fw.write(year+" "+month+" "+totalsched+"\r\n");
			for(int j = 1; j <= tmp.getSchedNum(); j++){
				Schedule schedtmp = tmp.getSchedule(j);
				if(!schedtmp.getTodo().isEmpty()){
					fw.write(schedtmp.getDate()+" "+schedtmp.getFrom()+" "+schedtmp.getTo()+" "
							+schedtmp.getR()+" "+schedtmp.getG()+" "+schedtmp.getB()
							+" "+schedtmp.getStyle()+" "+schedtmp.getFontsize()+" "
							+schedtmp.getFont()+" "+schedtmp.getTodo()+"\r\n");
				}
			}
		}
		fw.close();
	}
}
