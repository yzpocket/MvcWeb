package user.model;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class UserDAOMyBatis {
	private final String NS="user.model.UserMapper"; //변경안되게 맵퍼 위치 네임 스페이스 위치 final로 선언

	private String resource="common/config/mybatis-config.xml"; //[설계도]가 있는 경로
	private SqlSession ses;
	public SqlSessionFactory getSessionFactory() { //ses를 얻기
		try {
			InputStream is=Resources.getResourceAsStream(resource);
			SqlSessionFactory factory=new SqlSessionFactoryBuilder().build(is); //[8.1] SqlSessionFactoryBuilder 객체를 생성하고
			return factory; //[8.2] 위에서 SqlSessionFactory 객체를 먼저 얻어온다.
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}//---------
	
	public void close() {
		if(ses!=null) ses.close();

	}

	public int insertUser(UserVO user) {
		try {
			ses=this.getSessionFactory().openSession(true); //디폴트가 수동 커밋이다. true로 자동커밋설정함.
			int n=ses.insert (NS+".insertUser", user);
		return n;
		} finally{
			close();
		}
	}

	public boolean idCheck(String userid) {
		try {
			ses=this.getSessionFactory().openSession();
			Integer idx=ses.selectOne(NS+".idCheck", userid); //아이디를 integer로 받음
			if(idx==null) { //해당 회원이 없을때 true로 id사용 가능. 
				return true;
			}				//해당 회원이 있을때 false로 id사용 불가.
			return false;
		} finally {
			close();
		}
	}

	
	
	public UserVO loginCheck(String userid, String pwd) throws NotUserException //5. 사용자 예외는 NotUserException으로 이동
	{ 
		UserVO user=this.selectUserByUserid(userid); //3. 아이디가 여기로 들어옴
		if(user==null) {
			throw new NotUserException(userid+"란 아이디는 없어요"); //4. 그 아이디가없는경우 사용자 예외 발생
		}
		//6. 아이디가 있는경우에는
		String dbPwd=user.getPwd(); //db에서 패스워드를 받는다
		if(!dbPwd.equals(pwd)) { //db에서 받은 pwd와 입력된 pwd가 같지 않은 경우
			throw new NotUserException("비밀번호가 틀려요");
		}
		//7. 위 아이디와 유효성체크 모두 정상이면(db의 id,pwd와 입력된 id,pwd가 맞으면) user를 반환.
		return user;
		
	}
	//+세트
	public UserVO selectUserByUserid(String userid) { // 1.여기서 받은 아이디로
		try {
			ses=this.getSessionFactory().openSession(true);
			UserVO user=ses.selectOne(NS+".selectUserByUserid", userid); // 2.아이디가 있는지 확인.
			return user;
		} finally {
			close();
		}
	}


}
