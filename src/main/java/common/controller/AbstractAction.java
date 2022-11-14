package common.controller;

public abstract class AbstractAction implements Action {
	//implement Action으로부터 execute()추상 메서드 가지고 있음 
	private String viewPage; //보여줄 View페이지 이름
	
	private boolean isRedirect;
	//redirect 방식 이동이면 true
	//forward 방식 이동이면 false값을 갖는다.
	
	//setter,getter 구성
	public String getViewPage() {
		return viewPage;
	}
	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	

	
	
}
