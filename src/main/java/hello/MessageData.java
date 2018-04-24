package hello;

public class MessageData {
	private String userName;
	private String message;
	
	
	public MessageData(String UserName,String Message) {
		this.userName=UserName;
		this.message=Message;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
