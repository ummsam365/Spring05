package springMtTest;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vo.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class Test03_MemberMapper {
	@Autowired
	private SqlSession sqlsession;
	
	private static final String NS="green.mappers.memberMapper.";
	
	// ** Mapper의 각 sql 구문 동작 Test 
	//@Test
	public void selectOneTest() {
		MemberVO vo = new MemberVO();
		// 존재하는 id 사용시 해당 Row return
		// 없는 id 사용시 null return
		vo.setId("bababa");
		vo=sqlsession.selectOne(NS+"selectOne",vo);
		if (vo!=null) 
			 System.out.println("** Find : "+vo);
		else System.out.println("** !Find : "+vo);
	} // selectOneTest()
	
	@Test
	public void totalRowCountTest() {
		int count = sqlsession.selectOne(NS+"totalRowCount") ;
		System.out.println("** Member 전체 Record count : "+count);
	} // totalRowCountTest()
	
	public void joinTest() {
		MemberVO vo = new MemberVO() ;
		vo.setId("mybatis33");
		vo.setPassword("password");
		vo.setName("유니트");
		vo.setLev("A");
		vo.setBirthd("1999-09-09");
		vo.setPoint(100);
		vo.setWeight(23.45);
		vo.setRid("banana");
		int cnt = sqlsession.insert(NS+"insert",vo) ;
		// 입력 오류시 => red Line (insert 처리가 완료 되지못함으로)
		if (cnt>0) 
			 System.out.println("***** join 성공 *****");
		else System.out.println("***** join 실패 *****");
		
	} // joinTest
	
	
	

}// class
