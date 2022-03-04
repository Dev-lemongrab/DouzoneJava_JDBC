package quiz.ex01;

import java.util.Vector;

public class BookManager {
	public static int getRentalPrice(Vector<Book> bookList, String kind) {
		int sum = 0;
		for (int i = 0; i < bookList.size(); i++) {
			if(bookList.get(i).getKind().equals(kind)) {
				sum += bookList.get(i).getRentalPrice();
			}
		}
		return sum;
	}
}
