package com.ncs.green;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import criTest.PageMaker;
import criTest.SearchCriteria;
import service.BService;
import vo.BoardVO;
import vo.PageVO;

@Controller
public class BoardController {
	@Autowired
	BService service;
	
//** Form 출력	
	// 댓글 등록창
	@RequestMapping(value = "/rinsertf")
	public ModelAndView rinsertf(ModelAndView mv) {
		mv.setViewName("board/rinsertForm");
		return mv;
	}// rinsertf 
	
	// 새글 등록창
	@RequestMapping(value = "/binsertf")
	public ModelAndView binsertf(ModelAndView mv) {
		mv.setViewName("board/binsertForm");
		return mv;
	}// binsertf 
	
// ** New 기능 추가	
	// Ajax Json Test	
	@RequestMapping(value="/jsbdetail")
	public ModelAndView jsbdetail(ModelAndView mv, BoardVO vo,
					HttpServletRequest request, HttpServletResponse response) {
		
		// jsonView 사용시 한글 처리
		response.setContentType("text/html;charset=UTF-8");
		// 글번호로 글검색
		vo=service.selectOne(vo);
		if (vo == null) 
			 mv.addObject("content", "글번호에 해당하는 자료가 없습니다.~~");
		else mv.addObject("content", vo.getContent());
		
		mv.setViewName("jsonView");
		return mv ;
	} // jsbdetail	
	
	// Ajax Test 	
	@RequestMapping(value = "/ablist")
	public ModelAndView ablist(ModelAndView mv) {
		List<BoardVO> list = service.selectList();
		mv.addObject("Banana", list);
		mv.setViewName("ajaxTest/axBoardList");
		return mv;
	}// ablist
	
	// Page Criteria
	// @RequestMapping(value = "/board/listcri")
	@RequestMapping(value = "/listcri")
	public ModelAndView listcri(ModelAndView mv, SearchCriteria cri) {
		// 1) setSnoEno 출력할 Data Read & addObject
		cri.setSnoEno();
		// 1단계
		// mv.addObject("Banana",service.criList(cri));
		// 2단계
		mv.addObject("Banana",service.searchList(cri));
		
		// 2) Page 처리
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		// 2.1) 1단계  Criteria ( 검색조건 없음 )
		//pageMaker.setTotalRow(service.totalRowCount());
		
		// 2.2) 2단계  SearchCriteria ( 검색조건 포함 ) 
		pageMaker.setTotalRow(service.searchRowCount(cri));
		
		mv.addObject("pageMaker",pageMaker);
		// 3) view Name
		// => View name을 지정하지 않으면 자동으로 요청 url.jsp 즉  board/listcri.jsp 가 됨. 
		// => 간단해 보이지만 board/listcri.jsp 에서 경로 맞추기가 까다로움 (mv 와 비교시 단점)
		// => 모든 요청앞에  "board/" 가 포함 되므로 주의해야함. 
		// => resources, 모든 요청명 등등...
		mv.setViewName("board/listcri");
		return mv;
	} // listcri
	
	// Paging 1.
	@RequestMapping(value = "/plist")
	public ModelAndView plist(ModelAndView mv, PageVO pvo) {
		// 1) Paging 준비 
		// => DAO 의  pageList 를 처리하기 위해 필요한 값을 계산
		// => currPage, sno, eno
		int currPage=1;
		if (pvo.getCurrPage()>1 ) // 클라이언트의 요청 Page 존재함
			 currPage=pvo.getCurrPage();
		else pvo.setCurrPage(currPage);
		
		int startRno=(currPage-1)*pvo.getPerPage()+1;
		int endRno=(startRno+pvo.getPerPage())-1;	
		pvo.setSno(startRno);
		pvo.setEno(endRno);
		
		// 2) Service
		// => DB 에서 필요한 값들을 set 
		// => 출력할 Row List, totalCount (totalRowCount) 
		pvo = service.pageList(pvo);
		
		// 3) 결과 처리
		// => totalCount 를 이용해서 totalPageNo 계산 
		//    totalCount 가 70 이면  totalPageNo 는 ?  ( 1page당 5개씩 출력 ) 
		//    73/5 = 14, 나머지가 있으면 +1 
		int totalPageNo = pvo.getTotalCount()/pvo.getPerPage();
		if (pvo.getTotalCount()%pvo.getPerPage() > 0) 
			totalPageNo +=1;
		
		// ** Paging2 : Page Block
		// => sPageNo, ePageNo 계산 
		// => 필요한 값 : currPage, perPageNo 
		// => 유형   
		//    1) 항상 현재 Page가 중앙에 위치할때 
		// 			sPage :  currPage  -  perPageNo/2  
		//          epage :  currPage  +  perPageNo/2   
		//			
		//    2) Naver 카페 글, 11번가 상품리스트 Type
		//          sPage : (((currPage-1)/perPageNo)*perPageNo) +1  
		
		// 예를들어 currPage=3 이고 perPageNo 가 3 이면 1,2,3 page까지 출력 되어야 하므로
		// 아래 처럼 currPage-1 을 perPageNo 으로 나눈후 다시 곱하고 +1 
		// currPage=11 -> 10,11,12, => (11-1)/3 * 3 +1 = 10
		// 연습 ( perPageNo=5 )
		// -> currPage=11 인경우 : 11,12,13,14,15 -> ((11-1)/5)*5 +1 : 11 
		// -> currPage=7   인경우 : 6,7,8,9,10     -> ((7-1)/5)*5 +1 : 6
		
		int sPageNo=((currPage-1)/pvo.getPerPageNO())*pvo.getPerPageNO()+1;
		int ePageNo=sPageNo+pvo.getPerPageNO()-1;
		
		// 계산된 ePage 가 totalPageNo 보다 큰 경우에는 
		// totalPageNo 를  ePage 로 함.
		// => 예를 들면 totalPageNo=8 인데, ePage=9 인 경우 등등...
		if (ePageNo>totalPageNo)  ePageNo=totalPageNo ;
		
		// ** view 처리
		// Paging2 추가
		mv.addObject("sPage",sPageNo);
		mv.addObject("ePage",ePageNo);
		mv.addObject("perPageNo",pvo.getPerPageNO());
		// Paging1
		mv.addObject("totalPageNo",totalPageNo);
		mv.addObject("currPage",currPage);
		if ( pvo.getList() != null) {
			mv.addObject("Banana", pvo.getList());  
		} else {
			mv.addObject("message", "~~ 검색된 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("board/pageList");
		return mv;
	} // plist
	
	// 댓글 등록
	@RequestMapping(value = "/rinsert")
	public ModelAndView rinsert(HttpServletRequest request, ModelAndView mv, BoardVO vo) {
		
		// root, step, indent 로 관리
		// => 이 값 들은 원글(부모글) 의 값에서 계산됨.
		// => 그러므로 원글의  root, step, indent 값이 필요함.
		// => 원글의 detail 출력시에 이값들을 session에 보관했다가 사용  
		// => 답글의 root, step, indent 를 계산
		//    root 는 그대로 사용하고,
		//    step, indent 은 1씩 증가 시켜주면 됨.
		int proot=0, pstep=0, pindent=0;
		HttpSession session = request.getSession(false);
		if (session!=null && session.getAttribute("proot")!=null && 
			session.getAttribute("pstep")!=null && 
			session.getAttribute("pindent")!=null ) {
			proot=  (Integer)session.getAttribute("proot");
			pstep=  (Integer)session.getAttribute("pstep");
			pindent=(Integer)session.getAttribute("pindent");
		}
		vo.setRoot(proot);
		vo.setStep(++pstep);
		vo.setIndent(++pindent);
		
		if (service.rinsert(vo)>0) {
			// 글등록 성공 -> boardList 로 
			mv.setViewName("redirect:blist");
		}else {
			// 글등록 실패
			//	-> doFinish 로
			//     메시지 출력 , 다시 하기 ===> 댓글등록 
			mv.addObject("fCode","BR");
			mv.setViewName("member/doFinish");
		}
		return mv;
	}// rinsert
	
// ** Basic 기능 : CRUD	
	
	@RequestMapping(value = "/blist")
	public ModelAndView blist(ModelAndView mv) {
		List<BoardVO> list = service.selectList();
		if (list != null) {
			mv.addObject("Banana", list);  
		} else {
			mv.addObject("message", "~~ 검색된 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("board/boardList");
		return mv;
	} // blist
	
	@RequestMapping(value = "/bdetail")
	public ModelAndView bdetail(HttpServletRequest request, ModelAndView mv, BoardVO vo) {
		// 글 조회수 증가
		// => 내글(session 의 logID)인지 남이 쓴 글 (boardList 에서 클릭 한 글의 ID) 인지 확인 
		
		// 1) session 에서 logID get 
		HttpSession session = request.getSession(false); 
		// false : session 이 없을떄 null 을 return
		//         -> null 이 아님을 확인후 사용해야함.
		String logID = "";
		if (session != null && session.getAttribute("logID") != null)  {
			logID = (String)session.getAttribute("logID");
		} else System.out.println("~~ session is null 또는 login ID is null ~~");
		
		// 2) countUp
		if (!logID.equals(vo.getId())) service.countUp(vo);
		
		// 3) selectOne
		vo = service.selectOne(vo);
		
		if (vo!=null) {
			// 4) 댓글 입력을 위한 기본값(root,step,indent) 보관
			session.setAttribute("proot",vo.getRoot());
			session.setAttribute("pstep",vo.getStep());
			session.setAttribute("pindent",vo.getIndent());
			
			mv.addObject("Detail", vo);
			mv.setViewName("board/boardDetail");
		}else {
			mv.addObject("fCode","BN");
			mv.setViewName("member/doFinish");
		}
		return mv;
	}// bdetail
	
	// 새글등록 , 수정, 삭제 => login 후에만 가능
	// boardList, boardDetail -> login 한 경우에만 [새글등록]
	// boardDetail 
	// -> login 한 본인의 글 인경우에만 [수정, 삭제]
	// -> login 했고 본인글 아닌경우 [새글등록] 만가능
	@RequestMapping(value = "/binsert")
	public ModelAndView binsert(ModelAndView mv, BoardVO vo) {
		
		if (service.insert(vo)>0) {
			// 글등록 성공 -> boardList 로 
			mv.setViewName("redirect:blist");
		}else {
			// 글등록 실패
			//	-> doFinish 로
			//     메시지 출력 , 다시 하기 ===> 새글등록 
			mv.addObject("fCode","BI");
			mv.setViewName("member/doFinish");
		}
		return mv;
	}// binsert
	
	// 수정 가능 화면 출력하기
	@RequestMapping(value = "/bupdatef")
	public ModelAndView bupdatef(HttpServletRequest request, ModelAndView mv, BoardVO vo) {
		// 1) selectOne
		vo = service.selectOne(vo);
		
		if (vo!=null) {
			mv.addObject("Detail", vo);
			mv.setViewName("board/bupdateForm");
		}else {
			mv.addObject("fCode","BN");
			mv.setViewName("member/doFinish");
		}
		return mv;
	}// bupdatef
	
	@RequestMapping(value="/bupdate")
	public ModelAndView bupdate(ModelAndView mv, BoardVO vo) {
		
		if (service.update(vo)>0) //성공 => 글목록 출력 (blist)
			mv.setViewName("redirect:blist");
		else { // 실패 => doFinish.jsp
			mv.addObject("fCode","BU");
			mv.setViewName("member/doFinish");
		}	
		return mv ;	
	} //bupdate
	
	@RequestMapping(value="/bdelete")
	public ModelAndView bdelete(HttpServletRequest request, ModelAndView mv, BoardVO vo) {
		// 답글 추가 이후
		// ** 원글인지 댓글인지 구별 가능해야 함
		//	   원글: root==seq or step=0 or indent=0 
		// => 원글의 삭제 시에는 모든 답글들 같이 삭제 : 모든 답글을 찾기위해서는 root값 필요
		// => 답글의 삭제시에는 해당 답글만 삭제 : 해당 댓글의 seq 값 필요
		// ** 위의 필요값들은 이미 댓글 기능 구현을 위해서 session에 보관
		// -> 이를 get 해서 사용 또는 form에서 전송받음 
		//    또는 sql 구문으로 자신의 root 값을 읽어서 처리
		
		HttpSession session = request.getSession(false);
		if (session!=null && session.getAttribute("proot")!=null ) {
			vo.setRoot((Integer)session.getAttribute("proot")); 
		}
		
		if (service.delete(vo)>0) //성공 => 글목록 출력 (blist)
			mv.setViewName("redirect:blist");
		else { // 실패 => doFinish.jsp
			mv.addObject("fCode","BD");
			mv.setViewName("member/doFinish");
		}	
		return mv ;	
	} //bdelete

} // class
