package quiz.ex01;

public class Prob3 {
	public static void main(String[] args) {
		String url = "http://localhost:8080/order.do?prdId=PROD-0001&prdName=iPhone5&price=850000&maker=Apple";
		System.out.println("제품 번호 : " + getParameter(url, "prdId"));
		System.out.println("제품 이름 : " + getParameter(url, "prdName"));
		System.out.println("제품 가격 : " + getParameter(url, "price"));
		System.out.println("제조 회사 : " + getParameter(url, "maker"));
	}

	private static String getParameter(String url, String paramName) {
		String[] arr = url.split("\\?");//http Get메서드 방식에서 ?로 주소와 파라미터값을 구분함 
		String[] answer = arr[1].split("\\&");//각 파라미터는 &으로 나뉘어져 있음
		String value = "";
		for(int i = 0 ; i < answer.length ; i++) {
			if (answer[i].contains(paramName)) { //&으로 나눈 각 파라미터 변수를 담고 있는 배열들 중 원하는 변수 이름을 포함하고 있으면
				value = answer[i].split("\\=")[1];//=으로 변수와 값을 구분해준 뒤 값을 넣어줌
				break;
			}
		}
		return value;
	}	
}
