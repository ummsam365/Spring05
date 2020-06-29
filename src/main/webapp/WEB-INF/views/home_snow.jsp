<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>** Home **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script>
	function setClock() {
		var now = new Date() ;
		var t ='* NOW : '
			+ now.getFullYear()+'년'+(now.getMonth()+1)+'월'+now.getDate()+'일'
		 	+'_'+now.getHours()+':'+now.getMinutes()+':'+now.getSeconds() ;
		document.getElementById("clock").innerHTML=t;
		setTimeout("setClock()",1000)  // 1/1000 초 단위
	}
</script>
<!-- snowVillage Test ------------------- -->
<style>
#snowBox {
	width:350px ; height:250px;
	background-image : url("resources/image/snow.jpg");
	background-size : 100%;
	background-repeat : no-repeat;
}
div.snow {
	position : absolute;
	font-size : 40px;
	color : white;
	padding : 0px;
	margin : 0px;
}
</style>

<script>
var snow = new Array(30);					// 눈송이 30개에 대한 배열
var x = new Array(30);						// 눈송이 각각의 x좌표
var y = new Array(30);						// 눈송이 각각의 y좌표
var speedOfFall = new Array(30);            // 눈송이의 낙하하는 속도
var speedOfwind = new Array(30);          	// 떨어지며 풍속의 영향을 받는 정도
var step = new Array(30);                   // 풍속영향력의 변화도

function fall() {
	setClock();
	for(i=0; i<30; i++){
		y[i] += speedOfFall[i]              //눈송이의 다음 y좌표
		x[i] += Math.cos(speedOfwind[i]);  	//눈송이의 다음 x좌표, 코사인함수곡선을 이용
		if(y[i] >= window.innerHeight-60) {  //땅에 닿은 눈송이에 대해 새로운 눈송이를 생성
			x[i] = Math.floor(Math.random()*window.innerWidth);      
			y[i] = 0;
			speedOfFall[i] = Math.random()*2+2;               
		}

		if(x[i] >= window.innerWidth-50) x[i] = window.innerWidth - 50;  //브라우저의 크기를 넘어가는 눈송이에 대한 처리
		else if(x[i] < 0) x[i] = 50;
		
		snow[i].style.top = y[i] + "px";     
		snow[i].style.left = x[i] + "px";

		speedOfwind[i] += step[i]; 
	}
	setTimeout("fall()",50);
}
</script>
<!-- -------------------- ---------------- -->
</head> 

<body onload="fall()">
<!-- snowVillage Test -------------------- -->
<script>
makeSnow(); // 문서가 로딩되는 단계에서 눈(*)을 출력하는 <div> 객체 생성

function makeSnow() {
	// window.innerHeight는 현재 윈도우의 브라우저 영역의 높이
	// window.innerWidth는 현재 윈도우의 브라우저 영역의 폭
	
	for(var i=0; i<30; i++) { // 30 개의 눈송이 생성
		x[i] = Math.floor(Math.random()*window.innerWidth-10);       //눈송이의 x좌표 지정
		y[i] = Math.floor(Math.random()*window.innerHeight+10); 	//최초 눈송이의 y좌표 지정
		speedOfFall[i] = Math.random()*2+2;               //눈송이의 낙하속도 지정
		speedOfwind[i] = 1;                               //최초 바람의 영향
		step[i] = Math.random()*0.1+0.05;                 // 눈송이가 받는 바람의 영향의 변화도
		
		// <div id="snowobj012...">*</div> 생성
		var divtag = "<div class='snow' id=snowobj" + i
					+ " style='top:" + x[i] + "px;left:" + y[i] + "px'>*</div>";
		document.write(divtag);
		
		// <div> DOM 객체 기억
		snow[i] = document.getElementById("snowobj"+i)
	}
}
</script>
<!-- -------------------- ---------------- -->

<h2>** Spring04 MVC2 !!! **</h2>
<b><span id="clock" style="color:blue;"></span><br>
<c:if test="${logID != null}">
	* ${logID}, ${logName}님 안녕 하세요 ~~ <br>
</c:if>	
</b>
<hr>
<div id=snowBox></div>
<hr>
<h3>
<c:if test="${logID == null}">
	<a href="joinf">Join</a>&nbsp;&nbsp;
	<a href="loginf">Login</a>&nbsp;&nbsp;
</c:if>

<c:if test="${logID != null}">
	<a href="mdetail">MyInfo</a>&nbsp;&nbsp;
	<a href="logout">LogOut</a>&nbsp;&nbsp;
	<a href="mdelete">회원탈퇴</a><br>
</c:if>
<hr>
	<a href="mlist">MemberList</a>&nbsp;
	<a href="blist">BoardList</a>&nbsp;
	<a href="plist">PageList</a><br>
<hr>
<c:if test="${message != null}">=> ${message}</c:if>
</h3>
</body>
</html>
<!-- 
<a href="/Spring02/webapp/WEB-INF/views/login/loginForm.jsp">[Login]</a>
스프링 프로젝트에서  경로 "/webapp/WEB-INF/views/" 는
브라우져에서는 접근 할 수 없는 위치임.
그러므로 모든 view 들을 이곳에 두고,
반드시 모든 요청을 FrontController (DispatcherServlet) 를 통해 처리 하도록 함.  
-->

