<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** ID 중복 확인 **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
<script src="resources/jqLib/memberCheck.js"></script>
<script>
$(function() {
	$('#id').focusout(function() {
		idCheck();
	}).click(function() {
		$('#msgBlock').html('');
	}); // id_focusout_click
}); // ready

function idOK() {
// 부모창 (joinForm, parent_window -> opener) 으로 id를 전달하고
// 현재의 창은 닫는다.
	opener.document.getElementById('id').value="${ID}";
	// <script> 에서 EL을 문자열 Type 내부에 사용 가능함. 
	// => id 가 확정된 경우 더이상 id는 입력하지 못하도록 하고, 
	//    password 부터 입력 할 수 있도록 해줌.  
	//    joinForm의 submit 은 사용가능으로, ID 중복확인 버튼은 사용불가로.
	opener.document.getElementById('submit').disabled="";
	opener.document.getElementById('idDup').disabled="disabled";
	opener.$('#id').attr("readonly","readonly");
	//opener.$('#id').prop("readonly",true);
	//=> attr()는 HTML의 속성(Attribute) ,기능, 입력된 값  을 취급 
	//=> prop()는 JavaScript의 프로퍼티(Property), 실제값, property가 가지는 본연의 값  을  취급
	opener.$('#password').focus();
	self.close();
}; // idOK()

function propTest() {
	alert("attr => "+$('#ptest').attr('href')
			+"\n prop => "+$('#ptest').prop('href'));
}; // propTest()

</script>
<style>
	body{   
  		background-color: #E6E6FA ;
  		font-family: 맑은고딕;
		}
	#wrap{     
 		margin-left: 0;
 		text-align: center;
		 }
	h3 {
 		/* font-family: 맑은고딕, Times, serif; */
	  	font-size: 30px;
  		color: #00008B;
  		font-weight: normal;
		}
/* 	input[type=button], input[type=submit] {
  		float: right;
	} */
</style>

</head>
<body>
<div id=wrap>
<h3>** ID 중복 확인 **</h3>
<form action="idDupCheck" method="post">
UserID : 
	<input type="text" id="id" name="id" value="">
	<input type="submit" value="ID 중복확인" onclick="return idCheck()"><br>
	<span id="iMessage" class="eMessage"></span>
</form>
<br><br><hr><br>
<div id="msgBlock">
	<c:if test="${idUse=='T'}">
		${ID}는 사용 가능한 ID 입니다. ~~
		<input type="button" value="idOK" onclick="idOK()"> 
	</c:if>
	<c:if test="${idUse=='F'}">
		${ID}는 이미 존재 합니다 ~~ <br>
		사용 할 수 없으니 다시 입력 하세요 ~~
		<!-- 부모창(joinForm)에 남아있는 id 값은 지워주고,
		         현재(this) 창의 id 에 focus 를 준다 ~~~ => JS 코드 필요함 -->  
		<script>
			$('#id').focus();
			opener.document.getElementById('id').value='';
		</script>         
	</c:if>
</div></div>
<hr>
<pre>
** 속성을 취급하는 메서드 비교
=> attr()는 HTML의 속성(Attribute) ,기능, 입력된 값  을 취급 
=> prop()는 JavaScript의 프로퍼티(Property), 실제값, property가 가지는 본연의 값  을  취급
=> aTag href 값에 
   1) # 사용 -> 이동없이 제자리로
   2) url 사용 -> url 로 이동
</pre>
<button onclick="propTest()">attr&prop Test</button><br> 
<a href="#" id="ptest">attr&prop Test</a>
<!-- <a href="http://www.naver.com" id="ptest">attr&prop Test</a> -->

</body>
</html>