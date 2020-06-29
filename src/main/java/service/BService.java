package service;

import java.util.List;

import criTest.Criteria;
import criTest.SearchCriteria;
import vo.BoardVO;
import vo.PageVO;

public interface BService {
	
	// Search Criteria
	int searchRowCount(SearchCriteria cri);
	List<BoardVO> searchList(SearchCriteria cri);
	// Page Criteria
	List<BoardVO> criList(Criteria cri); 
	
	int totalRowCount();
	PageVO pageList(PageVO pvo); // pageList()
	int countUp(BoardVO vo); // countUp
	int rinsert(BoardVO vo); // Reply_insert

	List<BoardVO> selectList(); // selectList()

	BoardVO selectOne(BoardVO vo); // selectOne

	int insert(BoardVO vo); // insert

	int update(BoardVO vo); // update

	int delete(BoardVO vo); // delete

}