package com.ncs.green;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//** 스프링MVC 2단계 : 스프링 DispatcherServlet 사용II 
//=> Ano 기반 , MemberService 적용 

//** @Component (bean 생성 @)  의 세분화 
//=> 스프링 프레임워크에서는 클래스들을 기능별로 분류하기 위해 @ 을 추가함.
//=>  @Controller:사용자요청을 제어하는 Controller 클래스
//			      DispatcherServlet이 해당 객체를 Controller객체로 인식하게해줌. 	
//=>  @Service : 비즈니스로직을 담당하는 Service 클래스
//=>  @Repository:DB 연동을 담당하는 DAO 클래스
//			   DB 연동과정에서 발생하는 예외를 변환 해주는 기능 추가 	
//** 실습
//1. @Controller 사용함
//=> implements Controller 를 대신함.
//=> 아래와 관련된 import 삭제 해야 함.
//public class LoginController implements Controller {
//=> 메서드명, 매개변수, 리턴값 에 자유로워짐 
//	-> 메서드명은 handleRequest 사용안해도 됨
//	-> 매개변수 다양한 정의 가능 (메서드내에서 생성할 필요 없어짐)
//	-> 리턴값은 ModelAndView 또는 String 가능함

//=> 요청별 Controller 를 한 클래스내에 메서드로 구현할 수 있게 됨  
//=> 요청 과 메서드 연결 은 @RequestMapping 으로

//* @RequestMapping
//=> 요청과 매핑메서드 1:1 mapping 
//=> value="/mlist" : 이때 호출되는 메서드명과 동일하면 value 생략가능

//* 매핑메서드 의 매개변수	
//=> 매개변수로 정의 하면 메서드 내에서 생성할 필요 없음
//=> request.getParameter값  VO에 담기 => 자동화됨 
//  -> vo를 매핑 메서드의 매개변수로 선언하면 자동으로 대입됨
//  -> 단, form 의 input Tag의 name과 vo 의 변수명이 동일한 경우만 자동 대입됨. 
//=> Parameter처리 다른방법 -> @RequestParam
// public ModelAndView plogin(ModelAndView mv,
//			@RequestParam("id")String id, @RequestParam("pw")String pw) {……	
//=> @RequestParam
//		-> String id=request.getParameter("id") 와 동일,
//			그러나 매개변수 로 VO 를 사용하는것이 가장 간편 

//* Model & ModelAndView
//Model : 
//=> Model(interface)
//	-> controller처리 후 데이터를 view에 전달하는 역할 
//	-> 구현클래스 : ModelMap
//=> ModelAndView (class)
//	-> controller처리 후 데이터와 출력할 view를 지정하는 역할

//2. servlet-context.xml 수정
//=> HandlerMapping 
//	@RequestMapping 으로 대체되므로 삭제
//=> ViewResolver 는 사용해도 되고 안해도 됨
//	-> 삭제하고 사용안하려면 소스의 viewPath 의  .jsp 경로 수정 해야함 

//3. Service Layer 


@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
// ** Exception Test ****************************
	
	// Exception
	@RequestMapping(value = "/etest")
	public ModelAndView etest(ModelAndView mv) {
	// ArithmeticException Test
		double d=100.0/0.0; 
		// infinity -> d=100/0 : 우측 연산은 정수연산이므로 ArithmeticException 발생 
 		System.out.println("ArithmeticException Test d= "+d);
		int i=100/10 ; // test : i=100/0
		System.out.println("ArithmeticException Test i= "+i);
		
	// IllegalArgumentException	
		String s="abc" ; // IllegalArgumentException  
		s=null; // IllegalArgumentException 
		s="123";
		i += Integer.parseInt(s) ;
		System.out.println("IllegalArgumentException Test i= "+i);
		
	// NullPointerException	
		s=null;
		//if (s.equals("test")) 
		//	System.out.println("NullPointerException Test s= "+s.toUpperCase());
		
		// NullPointerException 를 예방 할 수 있는 equals
		if ("test".equals(s))
			System.out.println("** Exception 예방 True !!! **");
		else
			System.out.println("** Exception 예방 False !!! **");	
		
		mv.setViewName("home");
		return mv;
	}// sngreen
	
// ** kakaoMap Test ****************************
	
	// Map_주소
	@RequestMapping(value = "/sngreen")
	public ModelAndView sngreen(ModelAndView mv) {
		mv.setViewName("map01_snGreen");
		return mv;
	}// sngreen
	
	// Map_주소  : 여러 주소 -> 좌표전환  
	@RequestMapping(value = "/allgreen")
	public ModelAndView allgreen(ModelAndView mv) {
		mv.setViewName("map02_green");
		return mv;
	}// allgreen
	
	// Map_주소  : 여러 주소 -> 좌표전환  
	@RequestMapping(value = "/jeju")
	public ModelAndView jeju(ModelAndView mv) {
		mv.setViewName("map03_jeju");
		return mv;
	}// jeju
	
	@RequestMapping(value = "/atestf")
	public ModelAndView atestf(ModelAndView mv) {
		mv.setViewName("ajaxTest/axTestForm");
		return mv;
	}// atestf
	
	@RequestMapping(value = {"/","home"}, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
	 
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		 
		return "home";
	} // home

} // class
