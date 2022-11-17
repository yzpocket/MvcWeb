package board.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BoardDAOMyBatis {
	
	private final String NS="board.model.BoardMapper"; //변경안되게 맵퍼 위치 네임 스페이스 위치 final로 선언

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
	
	public void close() {
		if(ses!=null) ses.close();

	}
	
	//[검색2] 검색으로 String type, String keyword 파라미터가 추가되었다.
	public int getTotalCount(String type, String keyword) {
		//[검색2-1]검색 기능 추가에서 맵을 생성하고 String, String을 받도록 했다.
		Map<String, String> map=new HashMap<>(); 
		map.put("findType", type); //[검색2-2] 검색으로 추가된 findType, findKeyword를 맵에 저장 put하였다.
		map.put("findKeyword", keyword);
		
		ses=this.getSessionFactory().openSession(); //[8.3] SqlSession 객체(ses)를 얻어 올 수 있다.
		int count=ses.selectOne(NS+".totalCount", map); 		//단일행을 가져 올 때는 selectOne()을 쓰면 된다
		close();					//[검색2-3]위 put된 값들이 arr에 저장되도록 map 파라미터가 추가한다.boardMapper이동
		return count;
	}
	
	public int insertBoard(BoardVO vo) {
		ses=this.getSessionFactory().openSession(); //디폴트가 수동 커밋이다.
		System.out.println("insert전: vo의 num="+vo.getNum()); //맵퍼에서 <selectKey> 적용 테스
		
		int n=ses.insert(NS+".insertBoard", vo); //이 vo 매개변수가 boardMapper.xml로 

		System.out.println("insert후: vo의 num="+vo.getNum()); //맵퍼에서 <selectKey> 적용 테스
		if(n>0) {
			ses.commit();
		}else {
			ses.rollback();
		}
		close();
		return n;
	}
	//--------- [6-3] 페이징 처리로 파라미터 start, end이 받아졌다. 이것을 처리하기 위한 해쉬맵 map이 추가되었다.
							//[검색 3] 검색으로 String type, String keyword 파라미터가 추가되었다.
	public List<BoardVO> listBoard(int start, int end, String type, String keyword) {
		ses=this.getSessionFactory().openSession();
		//다중행을 가져올 때는 selectList()를 쓰면 된다
		//단일행을 가져 올 때는 selectOne()을 쓰면 된다
		// [6-3] 페이징 처리로 해쉬맵 map이 생성, 파라미터 start, end를 키값(String)으로 저장.
		// mapper에서 사용하기 위해서 키값("start","end")으로 사용되고 그 밸류값은 mapper에서 ${키}로 사용 할 수 있다
		Map<String,String> map=new HashMap<>(); //[검색3-1] 검색 기능 추가에서 맵이 String, String으로 변경되었다.
		map.put("start", String.valueOf(start)); //[검색3-2] 기존 페이징처리로 이미 맵은 생성되어있지만 타입이 변경되어 타입이 변경되어 기존 페이징 처리용 integer타입인 start, end를 String으로 형변환했다
		map.put("end", String.valueOf(end));
		map.put("findType", type); //[검색3-3] 검색으로 추가된 findType, findKeyword를 맵에 저장 put하였다. 
		map.put("findKeyword", keyword);
		
		//--------- [6-3] 페이징 처리로 파라미터 map이 추가되었다. //[검색3-4] 페이징 처리로 이미 map파라미터는 추가되어 있어 재확인만 했다.검색으로 위 put된 값들이 arr에 저장되도록 map 파라미터가 추가한다.boardMapper이동
		List<BoardVO> arr=ses.selectList(NS+".listBoard", map);
		close();
		return arr;
	}

	//[6]
	public BoardVO viewBoard(int num) {
		try {
			ses=this.getSessionFactory().openSession();
			BoardVO vo=ses.selectOne(NS+".viewBoard", num);
			return vo;
		} finally {
			close();
		}
	}
		
	public int deleteBoard(int num) {
		try {
			ses=this.getSessionFactory().openSession(true);
			//디폴트가 수동커밋이지만 매개변수로 true를 넘기면 auto commit된다.
			int n=ses.delete(NS+".deleteBoard", num);
			return n;
		}finally {
			close();
		}
	}
	
	public int updateBoard(BoardVO vo) {
		try {
			ses=this.getSessionFactory().openSession(true);
			//디폴트가 수동커밋이지만 매개변수로 true를 넘기면 auto commit된다.
			int n=ses.update(NS+".updateBoard", vo);
			return n;
		} finally {
			close();
		}
	}
}
