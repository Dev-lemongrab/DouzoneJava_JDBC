package quiz.ex01;

import java.util.Vector;

public class Prob4 {
	public static void main(String[] args) {
		Vector<Book> bookList = new Vector<Book>();
		bookList.add(new Book("Java Programming", "컴퓨터", 1400));
		bookList.add(new Book("SQL Programming", "컴퓨터", 1700));
		bookList.add(new Book("Servlet Programming", "컴퓨터", 2300));
		bookList.add(new Book("JDBC Programming", "컴퓨터", 700));
		bookList.add(new Book("EJB Programming", "컴퓨터", 4200));
		bookList.add(new Book("아버지", "소설", 1500));
		bookList.add(new Book("고등어", "소설", 1700));
		bookList.add(new Book("논리야 놀자", "소설", 1700));
		bookList.add(new Book("개미", "소설", 1300));
		bookList.add(new Book("찔레꽃", "소설", 1000));
		bookList.add(new Book("톰 과 제리", "만화", 2000));
		
		System.out.println("컴퓨터책 대여수입");
		System.out.println("---> " + BookManager.getRentalPrice(bookList, "컴퓨터"));
		
		System.out.println("소설책 대여수입");
		System.out.println("---> " + BookManager.getRentalPrice(bookList, "소설"));
		
		System.out.println("만화책 대여수입");
		System.out.println("---> " + BookManager.getRentalPrice(bookList, "만화"));
	}
}