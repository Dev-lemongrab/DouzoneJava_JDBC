package quiz.ex01;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Prob1 {
	public static void main(String[] args) {
		int seconds = 47567;
		System.out.println("<< " + seconds + " 초 변환 시간 >>");
		System.out.println(printTime(seconds));
		
		seconds = 11578;
		System.out.println("<< " + seconds + " 초 변환 시간 >>");
		System.out.println(printTime(seconds));
	}

	private static String printTime(int seconds) 
	{return seconds/(60*60)+" 시 "+(seconds%(60*60))/60+" 분 "+(seconds%(60*60))%60+" 초";}
	
}