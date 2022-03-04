package quiz.ex01;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Prob5 {
	public static void main(String[] args) throws FileNotFoundException {
		String fileName = "/Users/macbookpro/YunSeongOh/이공계-FinalTerm/prob5/data.txt";
		makeVariable(fileName);
	}

	private static void makeVariable(String fileName) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File(fileName));//스캐너로 파일 읽기
		while(sc.hasNext()){
			String str = sc.next();
			str = str.toLowerCase();
			if(str.contains("_")){// "_"면 삭제 및 여기 다음 글자 대문자로 바꿔주기 
				String[] strArr = str.split("_");
				strArr[1] = strArr[1].replace(strArr[1].charAt(0), Character.toUpperCase(strArr[1].charAt(0)));//_으로 나눈 다음 문자열 첫글자 uppercase로 바꿔주기 
				str = strArr[0]+strArr[1];
			}
			System.out.println(str);
		}
		
		
	}
}
