package jdbcUtil;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import criTest.Criteria;
import criTest.SearchCriteria;
import vo.BoardVO;
import vo.PageVO;

@Repository
public class BoardDAO {
	
	@Autowired
	private SqlSession sqlsession ;
	private static final String NS ="green.mappers.boardMapper." ;
	
	// Search Criteria
	public int searchRowCount(SearchCriteria cri) {  
		return sqlsession.selectOne(NS+"searchRowCount", cri);  
		// green.mappers.boardMapper.totalRowCount
	} // searchRowCount
	public List<BoardVO> searchList(SearchCriteria cri) {
		return sqlsession.selectList(NS+"searchList", cri) ;
	} // searchList()
	
	
	// Page Criteria
	public List<BoardVO> criList(Criteria cri) {
		return sqlsession.selectList(NS+"pageList", cri) ;
	} // criList()
	
	// Paging Test	
	public int totalRowCount() {  
		return sqlsession.selectOne(NS+"totalRowCount");  
		// green.mappers.boardMapper.totalRowCount
	} // totalRowCount
		
	public PageVO pageList(PageVO pvo) {
		pvo.setTotalCount(totalRowCount());
		pvo.setList(sqlsession.selectList(NS+"pageList", pvo));
		return pvo;
	} 
		
	public List<BoardVO> selectList() {
		return sqlsession.selectList(NS+"selectList");
	} // selectList
		
	public BoardVO selectOne(BoardVO vo) {
		return sqlsession.selectOne(NS+"selectOne",vo) ;
	} // selectOne
		
	public int insert(BoardVO vo) {
		return sqlsession.insert(NS+"insert",vo) ;
	} // insert
		
	public int update(BoardVO vo) {
		return sqlsession.update(NS+"update",vo) ;
	} // update
		
	public int delete(BoardVO vo) {
		return sqlsession.delete(NS+"delete",vo) ;
	} // delete
		
	public int countUp(BoardVO vo) {
		return sqlsession.update(NS+"countUp",vo) ;    
	} // countUp
		
	public int rinsert(BoardVO vo) {
		System.out.println("** rinsert StepUp count =>"+sqlsession.update(NS+"stepUpdate",vo));
		return sqlsession.insert(NS+"rinsert",vo) ;
	} // rinsert

} // class
