package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import criTest.Criteria;
import criTest.SearchCriteria;
import jdbcUtil.BoardDAO;
import vo.BoardVO;
import vo.PageVO;

//** interface 자동완성 
//=> Shift + Alt + T

@Service
public class BServiceImpl implements BService {
	@Autowired
	BoardDAO dao;
	
	// Search Criteria
	@Override
	public int searchRowCount(SearchCriteria cri) {  
		return dao.searchRowCount(cri);  
	} // searchRowCount
	@Override
	public List<BoardVO> searchList(SearchCriteria cri) {
		return dao.searchList(cri) ;
	} // searchList()
	
	// Page Criteria
	@Override
	public List<BoardVO> criList(Criteria cri) {
		return dao.criList(cri) ;
	} // criList()
	
	// Paging
	@Override
	public int totalRowCount() {  
		return dao.totalRowCount();  
	} // totalRowCount
	
	@Override
	public PageVO pageList(PageVO pvo) {
		return dao.pageList(pvo) ;
	} // pageList()
	
	// 조회수 증가 ( countUp )
	@Override
	public int countUp(BoardVO vo) {  
		return dao.countUp(vo);  
	} // countUp
	// 댓글 등록
	@Override
	public int rinsert(BoardVO vo) {  
		return dao.rinsert(vo);   	 
	} // rinsert
	
	
	@Override
	public List<BoardVO> selectList() {
		return dao.selectList() ;
	} // selectList()
	
	@Override
	public BoardVO selectOne(BoardVO vo) {
		return dao.selectOne(vo);
	} // selectOne
	
	@Override
	public int insert(BoardVO vo) {  
		return dao.insert(vo);  // 처리된 row 갯수 return	 
	} // insert

	@Override
	public int update(BoardVO vo) { // row(vo) 전달 받아 수정
		return dao.update(vo);  // 처리된 row 갯수 return
	} // update

	@Override
	public int delete(BoardVO vo) {
		return dao.delete(vo);  // 처리된 row 갯수 return
	} // delete

} // class
