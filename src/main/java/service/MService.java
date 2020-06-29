package service;

import java.util.List;

import criTest.SearchCriteria;
import vo.MPageVO;
import vo.MemberVO;

public interface MService {
	
	// Page SearchCriteria
	int searchRowCount(SearchCriteria cri);
	List<MemberVO> searchList(SearchCriteria cri);
	
	MPageVO pageList(MPageVO pvo); // pageList()
	
	List<MemberVO> selectList(); // selectList()
	MemberVO selectOne(MemberVO vo); // selectOne
	int insert(MemberVO vo); // insert
	int update(MemberVO vo); // update
	int delete(MemberVO vo); // delete
} // interface