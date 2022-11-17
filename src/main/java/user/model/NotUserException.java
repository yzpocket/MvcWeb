package user.model;

public class NotUserException extends Exception {
	public NotUserException() {
		super("NotUserException");
	}
	public NotUserException(String msg) {
		super(msg);//이 메시지가 출력된다.
	}
}////////////////////
