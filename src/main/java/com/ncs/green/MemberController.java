package com.ncs.green;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import criTest.PageMaker;
import criTest.SearchCriteria;
import service.MService;
import vo.MPageVO;
import vo.MemberVO;

@Controller
public class MemberController {
	@Autowired
	MService service;
	// MService service=new MServiceImpl();
	
// ** New 기능 추가   *****************************	

// ** JSON : 제이슨, JavaScript Object Notation
	//        자바스크립트의 객체 표기법으로, 데이터를 전달 할 때 사용하는 표준형식.
	//        속성(key) 과 값(value) 이 하나의 쌍을 이룸
	// ** JAVA객체 -> JSON 변환하기
	// 1) GSON
	//	: 자바 객체의 직렬화/역직렬화를 도와주는 라이브러리 (구글에서 만듦)
	//    즉, JAVA객체 -> JSON 또는 JSON -> JAVA객체
	// 2) @ResponseBody (매핑 메서드에 적용)
	// 	: 메서드의 리턴값이  View 를 통해 출력되지 않고 HTTP Response Body 에 직접 쓰여지게 됨.
	//	    이때 쓰여지기전, 리턴되는 데이터 타입에 따라 종류별 MessageConverter에서 변환이 이뤄진다.
	//    MappingJacksonHttpMessageConverter 를 사용하면 request, response 를 JSON 으로 변환     
	//    view (~.jsp) 가 아닌 Data 자체를 전달하기위한 용도        
	//    @JsonIgnore : VO 에 적용하면 변환에서 제외 
	
	// 3) jsonView
	// => Spring 에서  MappingJackson2JsonView를 사용해서 
	//		ModelAndView를 json 형식으로 반환해 준다.
	// => 방법
	//		-> pom dependency추가 , xml 에  bean 등록 
	// 		-> return할 ModelAndView 생성시 View를 "jsonView"로 설정	
	
	@RequestMapping(value = "/jslogin")
	public ModelAndView jslogin(HttpServletRequest request, ModelAndView mv, MemberVO vo) {

		String password = vo.getPassword();
		// => selectOne 하기전에 id, password 각각 보관 
		vo = service.selectOne(vo);
		if (vo != null &&  vo.getPassword().equals(password)) {
				// 로그인 성공 -> login 정보 보관 (id, name을 session에) -> loginSuccess
				request.getSession().setAttribute("logID", vo.getId());
				request.getSession().setAttribute("logName", vo.getName());
				mv.addObject("fCode", "200");
		}else { // 실패
				mv.addObject("fCode", "201");
		}
		mv.setViewName("jsonView");
		return mv;
		
	}// jslogin
	
	@RequestMapping(value = "/jsdelete")
	public ModelAndView jsdelete(ModelAndView mv, MemberVO vo) {
		
		if (service.delete(vo) > 0) {
			mv.addObject("fCode", "DS");
		} else {
			// Delete 실패
			mv.addObject("fCode", "DF");
		}
		mv.setViewName("jsonView");
		return mv;
	}// axdelete
	
// ** Ajax Delete	
	@RequestMapping(value = "/axdelete")
	public ModelAndView axdelete(ModelAndView mv, MemberVO vo) {
		if (service.delete(vo) > 0) {
			mv.addObject("fCode", "DS");
		} else {
			// Delete 실패
			mv.addObject("fCode", "DF");
		}
		mv.setViewName("member/doFinish");
		//mv.setViewName("redirect:amlist");
		return mv;
	}// axdelete
	
// ** Ajax MemberList 
	@RequestMapping(value = "/amlist")
	public ModelAndView amlist(ModelAndView mv) {
		List<MemberVO> list = service.selectList();
		mv.addObject("Apple", list);
		mv.setViewName("ajaxTest/axMemberList");
		return mv;
	}// amlist
	
// ** plogin 
	@RequestMapping(value="/ploginf")
	public ModelAndView ploginf(ModelAndView mv) {
		mv.setViewName("ajaxTest/ploginForm");
		return mv ;
	}
	@RequestMapping(value = "/plogin")
	public ModelAndView plogin(HttpServletRequest request, ModelAndView mv, MemberVO vo) {
		// id, password 확인
		// -> 성공 : login 정보, session에 보관, 성공표시
		// -> 실패 : 실패표시
		// -> view : ajaxTest/ploginForm.jsp
		
		mv.addObject("id", vo.getId());
		String password = vo.getPassword();
		// => selectOne 하기전에 id, password 각각 보관 
		vo = service.selectOne(vo);
		if (vo != null &&  vo.getPassword().equals(password)) {
				// 로그인 성공 -> login 정보 보관 (id, name을 session에) -> loginSuccess
				request.getSession().setAttribute("logID", vo.getId());
				request.getSession().setAttribute("logName", vo.getName());
				mv.addObject("loginR", "T");
		}else { // 실패
				mv.addObject("loginR", "F");
		}
		mv.setViewName("ajaxTest/ploginForm");
		return mv;
	} // plogin
	
// ** Page SearchCriteria
	// => 매핑메서드:mlistcri() , Service, DAO, Mapper, mlistcri.jsp
	@RequestMapping(value = "/mlistcri")
	public ModelAndView mlistcri(ModelAndView mv, SearchCriteria cri) {
		// 1) setSnoEno 출력할 Data Read & addObject
		cri.setSnoEno();
		mv.addObject("Banana",service.searchList(cri));
		
		// 2) Page 처리
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalRow(service.searchRowCount(cri));
		// 3) view Name
		mv.addObject("pageMaker",pageMaker);
		mv.setViewName("member/mlistcri");
		return mv;
	} // mlistcri
	
// ** Member Paging List
	@RequestMapping(value = "/mplist")
	public ModelAndView mplist(ModelAndView mv, MPageVO pvo) {
		// Paging 처리
		// 1) currPage, sno, eno
		int currPage = 1;
		if (pvo.getCurrPage() > 1) // 클라이언트의 요청 Page 존재함
			currPage = pvo.getCurrPage();
		else
			pvo.setCurrPage(currPage);

		int startRno = (currPage - 1) * pvo.getPerPage() + 1;
		int endRno = (startRno + pvo.getPerPage()) - 1;
		pvo.setSno(startRno);
		pvo.setEno(endRno);
		// 2) Service
		pvo = service.pageList(pvo);

		// 3) 결과 처리
		// => totalCount 를 이용해서 totalPageNo (LastPageNo) 계산
		// => sPageNo, ePageNo 계산
		int totalPageNo = pvo.getTotalCount() / pvo.getPerPage();
		if (pvo.getTotalCount() % pvo.getPerPage() > 0)
			totalPageNo += 1;

		int sPageNo = ((currPage - 1) / pvo.getPerPageNO()) * pvo.getPerPageNO() + 1;
		int ePageNo = sPageNo + pvo.getPerPageNO() - 1;
		// 계산된 ePage 가 totalPageNo 보다 큰 경우에는
		// totalPageNo 를 ePage 로 함.
		if (ePageNo > totalPageNo)
			ePageNo = totalPageNo;

		// ** view 처리
		mv.addObject("currPage", currPage);
		mv.addObject("sPage", sPageNo);
		mv.addObject("ePage", ePageNo);
		mv.addObject("perPageNo",pvo.getPerPageNO());
		mv.addObject("totalPageNo", totalPageNo);
		
		if (pvo.getList() != null) {
			mv.addObject("Banana", pvo.getList());
		} else {
			mv.addObject("message", "~~ 검색된 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("member/mpageList");
		return mv;
	} // mplist
	
// ** Id Dup Check
	@RequestMapping(value = "/idDupCheck")
	public ModelAndView idDupCheck(ModelAndView mv, MemberVO vo) {
		// client 로 부터 전달된 id 의 존재 여부 확인 : selectOne()
		// => notNull (존재하면) : 사용불가
		// => Null (존재하지 않으면) : 사용가능 (먼저 입력한 ID 보관 )
		mv.addObject("ID", vo.getId());
		vo = service.selectOne(vo);
		if (vo != null) // 사용불가
			mv.addObject("idUse", "F");
		else
			mv.addObject("idUse", "T");

		mv.setViewName("member/idDupCheck");
		return mv;
	}// idDupCheck
	
// ********************************

	@RequestMapping(value = "/loginf")
	public ModelAndView loginf(ModelAndView mv) {
		mv.setViewName("login/loginForm");
		return mv;
	} // loginf

	@RequestMapping(value = "/joinf")
	public ModelAndView joinf(ModelAndView mv) {
		mv.setViewName("member/joinForm");
		return mv;
	}// joinf
	
	@RequestMapping(value = "/mlist")
	public ModelAndView mlist(ModelAndView mv) {
		List<MemberVO> list = service.selectList();
		if (list != null) {
			mv.addObject("Banana", list); // scope 이 request 와 동일
		} else {
			mv.addObject("message", "~~ 검색된 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("member/memberList");
		return mv;
	} // mlist

	// request.getParameter값 VO에 담기 => 자동화됨
	// => vo를 매핑 메서드의 매개변수로 선언하면 자동으로 대입됨
	// => 단, form 의 input Tag의 name과 vo 의 변수명이 동일한 경우만 자동 대입됨.
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request, ModelAndView mv, MemberVO vo) {

		String password = vo.getPassword();
		mv.setViewName("login/loginForm");

		vo = service.selectOne(vo);
		if (vo != null) { // id 존재
			if (vo.getPassword().equals(password)) {
				// 로그인 성공 -> login 정보 보관 (id, name을 session에) -> loginSuccess
				request.getSession().setAttribute("logID", vo.getId());
				request.getSession().setAttribute("logName", vo.getName());
				mv.setViewName("login/loginOn");
			} else {
				// Password 오류 -> 재로그인
				mv.addObject("message", " Password 오류 !! ~~ 다시 하세요 ~~");
			}
		} else { // ID 오류 -> 재로그인
			mv.addObject("message", " ID 오류 !! 다시 하세요 ~~");
		}
		return mv;
	} // login

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, ModelAndView mv, MemberVO vo) {
		request.getSession().invalidate();
		mv.setViewName("redirect:home"); // url 요청명 home
		// mv.setViewName("home"); // home.jsp 로 forward
		return mv;
	}// logout
		// "redirect:home"
		// => viewRevolver 활용하면서, 확장자 안붙이고, redirect 함

	@RequestMapping(value = "/mdetail")
	public ModelAndView mdetail(HttpServletRequest request, ModelAndView mv, MemberVO vo) {

		// 1) login 여부 확인
		String id = "";
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("logID") != null) {
			id = (String) session.getAttribute("logID");
		} else {
			// login 하도록 유도 후에 메서드 return 으로 종료
			mv.addObject("message", "~~ 로그인 후에 하세요 ~~");
			mv.setViewName("login/loginForm");
			return mv;
		}
		vo.setId(id);
		vo = service.selectOne(vo);
		mv.addObject("myInfo", vo);

		// 4) 결과 ( Detail or Update 인지 )
		// => request.getParameter("code") 가 U 인지 확인
		mv.setViewName("member/memberDetail");
		if ("U".equals(request.getParameter("code"))) {
			// 내정보 수정화면으로
			mv.setViewName("member/updateForm");
		} else if ("E".equals(request.getParameter("code"))) { // 내정보 수정에서 오류 상황
			mv.addObject("message", "~~ 내정보 수정 오류  !!! 다시 하세요 ~~");
		}
		return mv;

	}// mdetail
	
	@RequestMapping(value = "/join")
	public ModelAndView join(ModelAndView mv, MemberVO vo) throws IOException {
		
		System.out.println("AjaxTest =>"+vo);
		mv.setViewName("member/doFinish");
		
		// uploadfile 처리 
		// => uploadfilef 의 정보에서  화일명을 get,
		// => upload된 image 를 서버의 정해진 폴더에 저장 하고, -> file1
		// => 이 위치에 대한 정보를 table 에 저장 (vo에 set)   -> file2
		
		// ** MultipartFile
		// => 업로드한 파일에 대한 모든 정보를 가지고 있으며 이를 위한 메서드를 제공한다.
		//    String getOriginalFilename(), void transferTo(File destFile), boolean isEmpty()
		
		MultipartFile uploadfilef;   
		String file1, file2="No Image";
		
		//if (!uploadfilef.isEmpty())  {
		// => Ajax 의  FormData 는  이미지를 선택하지 않으면 append시 오류 발생
		//    하기 때문에 이를 확인 후 append 하도록 함
		//    이때 append 를 하지 않으면  서버의 vo.uploadfilef 에는  null 값이 전달됨.
		//    그래서 vo.getUploadfilef() 를 null Check 하는것으로 수정함. : != null
		// => submit 으로 전송시 선택하지 않은경우 '' 전달 : isEmpty() 
		if (vo.getUploadfilef() !=  null ) {
			uploadfilef = vo.getUploadfilef();
			if (!uploadfilef.isEmpty())  {
			// 실제 저장 경로(file1) 생성 하고 저장
				file1="D:/MTest/MyWork/Spring05/src/main/webapp/resources/uploadImage/"
					+ uploadfilef.getOriginalFilename(); // 드라이브에 저장되는 실제경로
				uploadfilef.transferTo(new File(file1));
				file2="resources/uploadImage/"+uploadfilef.getOriginalFilename();
			} // !uploadfilef.isEmpty()
		}
		vo.setUploadfile(file2);
		
		// ** Transaction Test 
				// 1) Aop xml 적용전 => 첫번째 insert 는 입력되고, 두번째 insert에서 오류발생 
				// 2) Aop xml 적용후 => 두번째 insert에서 오류발생시 rollback 되어 모두 입력 안됨  
				//int cnt = service.insert(vo);
				//int cnt2= service.insert(vo);  // id 중복 무결성 오류
		
		// Transaction Test
		// => 동일 자료 insert 2번 -> rollback 확인
		// => Test1) Transaction 없는경우 (Aop xml 적용전)
		//		: insert 1 은 저장 되고 , insert 2 에서 오류 발생 (500)
		// => Test2) Transaction 있는경우 (Aop xml 적용후)
		//		: insert 1 은 저장 되고 , insert 2 에서 오류 발생 
		//			-> RollBack 됨으로 둘다 insert 안됨. (500)
		// 			-> 500 은 Exception 처리 해주면 RollBack 확인 가능 
		// insert1 : 
		service.insert(vo);
		// insert2
		if (service.insert(vo) > 0) {
			// Join 성공
			mv.addObject("joinID", vo.getId());
			mv.addObject("fCode", "JS");
		} else {
			// Join 실패
			mv.addObject("fCode", "JF");
		}
		return mv;
	}// join
	
	@RequestMapping(value = "/mupdate")
	public ModelAndView update(HttpServletRequest request, ModelAndView mv, MemberVO vo)
				throws IOException {
		// Image 처리
		// 1. uploadfilef 처리 
		// 2. New Image 선택여부
		// => 선택을 한 경우 : new Image 처리
		// => 선택을 안한 경우 : old Image를 vo에 set
		// 해결
		// => 수정전의 이미지 화일명을 updateForm 에 hidden으로 보관했다가 사용함 
		// => vo 에 uploadfile 은 담겨있으므로,  
		// => new Image를 선택 한 경우에만 uploadfilef를 이용하여 처리하면 됨
		
		MultipartFile uploadfilef = vo.getUploadfilef();
		String file1, file2 ;
		if (!uploadfilef.isEmpty())  {
			// 실제 저장 경로(file1) 생성 하고 저장
			file1="D:/MTest/MyWork/Spring05/src/main/webapp/resources/uploadImage/"
					+ uploadfilef.getOriginalFilename(); // 드라이브에 저장되는 실제경로
			uploadfilef.transferTo(new File(file1));
			file2="resources/uploadImage/"+uploadfilef.getOriginalFilename();
			vo.setUploadfile(file2);
		} // !uploadfilef.isEmpty()
		
		if (service.update(vo) > 0) {
			// 회원수정 성공 -> memberList 출력
			// session 의 Attribute logName 도 변경
			request.getSession().setAttribute("loginName", vo.getName());
			mv.setViewName("redirect:mlist");
		} else {
			// 회원수정 실패 -> 내정보 보기 화면으로
			mv.setViewName("redirect:mdetail?code=E");
		} // if
		return mv;
	}// mupdate

	@RequestMapping(value = "/mdelete")
	public ModelAndView mdelete(HttpServletRequest request, ModelAndView mv, MemberVO vo) {

		// 1) login 여부 확인
		String id = "";
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("logID") != null) {
			id = (String) session.getAttribute("logID");
		} else {
			// login 하도록 유도 후에 메서드 return 으로 종료
			mv.addObject("message", "~~ 로그인 후에 하세요 ~~");
			mv.setViewName("login/loginForm");
			return mv;
		}
		// 2) Login OK -> delete
		vo.setId(id);
		mv.setViewName("member/doFinish");
		mv.addObject("deleteID", id);

		if (service.delete(vo) > 0) {
			// Delete 성공
			mv.addObject("fCode", "DS");
			session.invalidate(); // 삭제 되면 session 도 삭제해야함.
		} else {
			// Delete 실패
			mv.addObject("fCode", "DF");
		}
		return mv;
	}// mdelete
	
} // class
