package quiz.customer;

public class Customer {//Model
	public final String ClassName = "CUSTOMERS";
	private int code;
	private String name;
	private String email;
	private String phone;
	
	
	public String getClassName() {
		return ClassName;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
