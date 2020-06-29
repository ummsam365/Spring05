package springMtTest;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class Test02_SqlSession {
	@Autowired
	private SqlSessionFactory sqlFactory;
	// 계층도: SqlSessionFactory (interface)  
	// 		 -> FactoryBean<SqlSessionFactory> (interface) -> SqlSessionFactoryBean
	
	//@Before(value="testSqlSession()") 
	// import org.aspectj.lang.annotation.Before;
	public void testSqlFactory() {
		// SqlSessionFactory 자동주입 확인 ->  sqlFactory is Not Null
		System.out.println("** SqlSessionFactory 자동주입 확인: sqlFactory \n=> "+sqlFactory+'\n');
	} //testSqlFactory() 
	
	@Test
	public void testSqlSession() {
		// SqlSession -> 실제 DB 연결, Mapper의 Sql 구문을 이용해 DAO의 요청을 처리.	
		try {
			SqlSession sqlsession = sqlFactory.openSession();
			// 계층도 : SqlSession (interface) -> SqlSessionTemplate
			
			System.out.println("**  SqlSession 주입 Test SqlSession => \n"+sqlsession+"\n");
		} catch (Exception e) {
			System.out.println("** testSqlSession Exception => "+e.toString());
		}
	} //testSqlFactory() 
	
} // class
