package jdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import vo.BoardVO;
import vo.PageVO;

@Repository
public class BoardDAO_OLD {
	static Connection cn = DBConnection.getConnection();
	static Statement st;
	static PreparedStatement pst;
	static ResultSet rs;
	String sql;
	
	// Paging 처리
	// => TotalRowCount : 전체 Row 갯수 계산
	// => ArrayList<BoardVO> list : 출력할 row select
	public int totalRowCount() {
		sql="select count(*) from board";
		try {
			st=cn.createStatement();
			rs=st.executeQuery(sql);
			if (rs.next())  return rs.getInt(1);
		} catch (Exception e) {
			System.out.println("totalRowCount Exception => "+e.toString());
		}
		return 0;
	} // totalRowCount
	
	public PageVO pageList(PageVO pvo) {
		
		// 1) totalCount (totalRowCount) 
		// => 전체 Row 갯수 set
		pvo.setTotalCount(totalRowCount());
		
		// 2) 출력할 row select ( sno ~ eno )
		// Mysql
		// => limit 적용 (sno 만 필요)
		// Oracle 
		// => 1) ResultSet 에서 처리 
		// => 2) SQL문에서 처리
		//		 ROW_NUMBER(), OVER() 함수 이용	
		
		sql="select * from "  
			+ "(select b.* , ROW_NUMBER() OVER(order by root desc, step asc) rnum from board b)"
			+ " where rnum between ? and ?";
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1,pvo.getSno());
			pst.setInt(2,pvo.getEno());
			rs=pst.executeQuery();
			if (rs.next()) {
				do {
					BoardVO vo = new BoardVO();
					vo.setSeq(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setId(rs.getString(3));
					vo.setContent(rs.getString(4));
					vo.setRegdate(rs.getString(5));
					vo.setCnt(rs.getInt(6));
					vo.setRoot(rs.getInt(7));
					vo.setStep(rs.getInt(8));
					vo.setIndent(rs.getInt(9));
					list.add(vo);
				} while (rs.next());
			} else {
				list = null;
			}
		} catch (Exception e) {
			System.out.println("** pageList Exception=>" + e.toString());
			list = null;
		}  
		pvo.setList(list);
		return pvo;
	} // pageList()

	// 1. Board Table 의 CRUD 구현
	// => selectList, selectOne, insert, update, delete 
	
	// 2. 조회수 증가 ( countUp )
	public int countUp(BoardVO vo) {
		sql = "update board set cnt=cnt+1 where seq=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1, vo.getSeq());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** countUp Exception => " + e.toString());
			return 0;
		}
	} // countUp
	
	// 3. 댓글 등록
	// => 원글 입력과의 차이점
	//	   동일한 root 를 가지는 먼저 입력되어있는 댓글들의 step 값이 조정 되어야 함.
	//=> 그러므로 stepUpdate (1씩 증가) 가 필요함.	
	//=> stepUpdate 의 조건 : root는 같고, step은 현재 입력되는 자료의 step 와 같거나 큰 값 	
	public int rinsert(BoardVO vo) {
		// stepUpdate
		System.out.println("** Reply Insert StepUpdate Count => "+stepUpdate(vo));
		
		// seq : 자동 넘버링 -> nvl 함수 적용
		// 댓글  : root, step, indent 는 ? 로 처리 
		sql = "insert into board values" + 
			  "((select nvl(max(seq), 0)+1 from board),?,?,?,sysdate,0,?,?,?)";
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, vo.getTitle());
			pst.setString(2, vo.getId());
			pst.setString(3, vo.getContent());
			pst.setInt(4, vo.getRoot());
			pst.setInt(5,vo.getStep());
			pst.setInt(6,vo.getIndent());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** Reply_insert Exception=>" + e.toString());
			return 0;
		}
	} // rinsert
	public int stepUpdate(BoardVO vo) {
		sql = "update board set step=step+1 where root=? and step>=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setInt(1, vo.getRoot());
			pst.setInt(2, vo.getStep());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** stepUpdate Exception=>" + e.toString());
			return 0;
		}
	} // stepUpdate
	
	public ArrayList<BoardVO> selectList() {
		sql = "select * from board order by root desc, step asc";
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				do {
					BoardVO vo = new BoardVO();
					vo.setSeq(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setId(rs.getString(3));
					vo.setContent(rs.getString(4));
					vo.setRegdate(rs.getString(5));
					vo.setCnt(rs.getInt(6));
					vo.setRoot(rs.getInt(7));
					vo.setStep(rs.getInt(8));
					vo.setIndent(rs.getInt(9));
					list.add(vo);
				} while (rs.next());
			} else {
				list = null;
			}
		} catch (Exception e) {
			System.out.println("** selectList Exception=>" + e.toString());
			list = null;
		}
		return list;
	} // selectList()
	
	public BoardVO selectOne(BoardVO vo) {
		sql = "select * from board where seq=?";
		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, vo.getSeq());
			rs = pst.executeQuery();
			if (rs.next()) {
				vo.setTitle(rs.getString(2));
				vo.setId(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setRegdate(rs.getString(5));
				vo.setCnt(rs.getInt(6));
				vo.setRoot(rs.getInt(7));
				vo.setStep(rs.getInt(8));
				vo.setIndent(rs.getInt(9));
			} else {
				vo = null;
			}
		} catch (Exception e) {
			System.out.println("** selectOne Exception=>" + e.toString());
			vo = null;
		}
		return vo;
	} // selectOne

	public int insert(BoardVO vo) {
		// seq : 자동 넘버링 -> nvl 함수 적용
		// 원글 : seq와 root 값 동일, step 과  indent 는 0 
		sql = "insert into board values" + 
			  "((select nvl(max(seq), 0)+1 from board),?,?,?,sysdate,0,"+
			  "(select nvl(max(seq), 0)+1 from board),0,0)"  ;
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, vo.getTitle());
			pst.setString(2, vo.getId());
			pst.setString(3, vo.getContent());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** insert Exception=>" + e.toString());
			return 0;
		}
	} // insert

	public int update(BoardVO vo) {
		sql = "update board set title=?, content=? where seq=?";
		try {
			pst=cn.prepareStatement(sql);
			pst.setString(1, vo.getTitle());
			pst.setString(2,vo.getContent());
			pst.setInt(3, vo.getSeq());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** update Exception=>" + e.toString());
			return 0;
		}
	} // update

	public int delete(BoardVO vo) {
		// 원글의 삭제 시에는 모든 답글들 같이 삭제, ( root =? )
		// 답글의 삭제 시에는 해당 답글만 삭제 ( seq = ? ) 
		// => 1) 조건 비교  seq==root : 원글 ,  else : 답글 
		// => 2) 해당 sql 구문 작성	
		
		if (vo.getSeq()==vo.getRoot()) 
			 // 원글 -> 원글과 답글 모두 삭제
			 // seq==root 이므로 vo.getSeq() 로 비교 가능 
			 sql="delete board where root=?";
		else // 답글
			 sql="delete board where seq=?";
		
		try {
			pst = cn.prepareStatement(sql);
			pst.setInt(1, vo.getSeq());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** delete Exception=>" + e.toString());
			return 0;
		}
	} // delete

} // class
