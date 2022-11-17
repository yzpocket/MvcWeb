package user.model;
//VO(Value Object), DTO(Data Transfer Object)라고 한다.
//Domain 객체라고 함
//domain 객체는 db table의 record 를 클래스로 옮겨 구성. 
//컬럼명을 멤버변수 선언. 가능하면 이름을 맞춰서 구성한다.
import java.sql.Date;

public class UserVO {
	//jsp에서는 property라고함 html의 input name을 일치시켜주면 편리하다 => useBean액션 사용시 자동으로 값을 전달 할 수 있다.
	//property명과 html의 input name을 일치시켜주면 편리=>useBean액션 사용시 자동으로 값을 전달할 수 있다 
	private int idx;
	private String name;
	private String userid;
	private String pwd;
	private String hp1;
	private String hp2;
	private String hp3;
	private String post;
	private String addr1;
	private String addr2;
	
	private java.sql.Date indate;
	private int mileage;
	private int status; //회원상태정보 (0:일반 ,-1:정지, -2:탈퇴)
	private String statusStr;
	
		/** VO 기본 생성자 */

	public UserVO() {
		
	}
	/** 생성자 오버로드 Source/Generate Constructor Using Fields 로 생성 가능. */

	public UserVO(int idx, String name, String userid, String pwd, String hp1, String hp2, String hp3, String post,
			String addr1, String addr2, Date indate, int mileage, int status, String statusStr) {
		this.idx = idx;
		this.name = name;
		this.userid = userid;
		this.pwd = pwd;
		this.hp1 = hp1;
		this.hp2 = hp2;
		this.hp3 = hp3;
		this.post = post;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.indate = indate;
		this.mileage = mileage;
		this.status = status;
		this.statusStr = statusStr;
	}


	/** Setter/Getter Source/Generate Getters/Setters 로 생성 가능. */

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getHp1() {
		return hp1;
	}

	public void setHp1(String hp1) {
		this.hp1 = hp1;
	}

	public String getHp2() {
		return hp2;
	}

	public void setHp2(String hp2) {
		this.hp2 = hp2;
	}

	public String getHp3() {
		return hp3;
	}

	public void setHp3(String hp3) {
		this.hp3 = hp3;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public java.sql.Date getIndate() {
		return indate;
	}

	public void setIndate(java.sql.Date indate) {
		this.indate = indate;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	//핸드폰 번호 합친거

	public String getAllHp() {
		return hp1+"-"+hp2+"-"+hp3;
	}
	
	//[우편]주소 합친거

	public String getAllAddr() {
		return "["+post+"]"+addr1+" "+addr2;
	}
	
}
