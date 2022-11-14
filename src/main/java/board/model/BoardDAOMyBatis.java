package board.model;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BoardDAOMyBatis {
	
	private final String NS="board.model.BoardMapper"; //변경안되게 final로 선언

	private String resource="common/config/mybatis-config.xml"; //[설계도]가 있는 경로
	private SqlSession ses; //이것을 얻어와야 한다. 
	
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
	public int getTotalCount() {
		ses=this.getSessionFactory().openSession(); //[8.3] SqlSession 객체(ses)를 얻어 올 수 있다.
		int count=ses.selectOne(NS+".totalCount");
		if(ses!=null) ses.close();
		return count;
	}
	public int insertBoard(BoardVO vo) {
		ses=this.getSessionFactory().openSession(); //디폴트가 수동 커밋이다.
		int n=ses.insert(NS+".insertBoard", vo); //이 vo 매개변수가 boardMapper.xml로 
		if(n>0) {
			ses.commit();
		}else {
			ses.rollback();
		}
		return n;
	}
}
