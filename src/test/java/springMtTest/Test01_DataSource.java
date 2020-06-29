package springMtTest;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jdbcUtil.DBConnection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class Test01_DataSource {
	// DataSource : 위의 설정파일을 이용하여 스프링이 생성, 자동 주입 Test
	@Autowired
	private DataSource ds;
	// 계층도 확인 ( Ctrl+T )
	// => DataSource (interface) 
	//		->  AbstractDataSource
	//		->  AbstractDriverBasedDataSource
	//		->  DriverManagerDataSource
	@Test
	public void connectionTest() throws Exception {
		try {
			Connection cn = ds.getConnection();
			System.out.println("** DB 연결 성공 **");
		} catch (Exception e) {
			System.out.println("** DB 연결 실패 **");
			System.out.println("** Exception => "+e.toString());
		}
	} // connectionTest()
} // class
