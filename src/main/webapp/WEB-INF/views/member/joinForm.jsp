<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Member Join Form **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
<script src="resources/jqLib/memberCheck.js"></script>
<script src="resources/jqLib/axTest01.js"></script>
<!-- 
** 입력값 확인 하기
=> join, update, login 
=> 외부문서 (memberCheck.js) 에 작성 후 활용
=> 처리방식 : inCheck05 방식 ( focusout(), submit 적용) -->
<script>
// ** 1. 입력 오류 확인 ( inCheck )
// 1.1) 개별적 오류 확인을 위한 switch 변수 정의
var iCheck=false;
var pCheck=false;
var nCheck=false;
var bCheck=false;
var oCheck=false;  // point
var wCheck=false;  // weight

// 1.2) 개별적 focusout 이벤트리스너 function 작성 : JQuery,
$(function(){
	$('#id').focus();
	$('#id').focusout(function(){
		iCheck=idCheck();
	}); // id_focusout
	
	$('#password').focusout(function(){
		pCheck=pwCheck();
	}); // password_focusout
	
	$('#name').focusout(function(){
		nCheck=nmCheck();
	}); // name_focusout
	
	$('#birthd').focusout(function(){
		bCheck=bdCheck();
	}); // id_focusout
	
	$('#point').focusout(function(){
		oCheck=poCheck();
	}); // password_focusout
	
	$('#weight').focusout(function(){
		wCheck=weCheck();
	}); // name_focusout
	
}); // ready	

// 1.3) 전체적으로 입력 오류 가 없음을 확인하고 submit 여부를 판단

function inCheck() {
	if (iCheck==true && pCheck==true && nCheck==true &&
		bCheck==true && oCheck==true && wCheck==true )	
		return true;  
	else {
		alert('입력 오류 확인을 하지 않은 항목이 있습니다. 확인 후 전송 하세요~~ '); 	
		return false;
	} // else
} // inCheck()

// ** 2. ID 중복 확인하기 ( idDupCheck )
// 2.1) ID 입력 오류 확인 : 길이, 영문과 숫자만 입력 가능
// 2.2) 중복 확인 요청 (서버로) -> window.open()
// 2.3) 요청 결과에 따른 처리
// => 중복이면 : id 다시입력 중복확인 ..반	복
// => 사용가능 : 사용 여부 확인 
//     -> yes : joinForm 에서  pw입력 ..,
//     -> no : 재 선택
// 2),3) 실행 => 요청 
// -> 서버의 서블릿 (Controller) -> DB 확인 -> 결과 Page

// ** window.open()
//=> 자바스크립트에서 새창을 띄워주는 함수
//=> window.open ("[요청명]", "[창이름 또는 로딩되는 창]", "[창 속성]");

function idDupCheck() {
	if (iCheck==false) { iCheck=idCheck(); }
	else {
		// url 요청이 서버로 전달되고 그 결과가 아래 윈도우로 Open됨. 
		var url="idDupCheck?id="+$('#id').val();
		window.open(url,"_blank",
			"toolbar=no,menubar=yes,scrollbars=yes,resizable=yes,width=500,height=400");
	}
} // idDupCheck()

</script>

</head>
<body>
<h3>** Member Join Mybatis **</h3>
<h3>
<form action="join" method="post" id="myForm" enctype="multipart/form-data">
<table>
	<tr height="40"><td bgcolor="yellow">I D</td>
	<td><input type="text" name="id" id=id size="10">&nbsp;
		<input type="button" value="ID 중복확인" id="idDup" onclick="idDupCheck()"><br>
		<span  id="iMessage" class="eMessage"></span></td>
	</tr>
	<tr height="40"><td bgcolor="yellow">Password</td>
	<td><input type="password" name="password" id="password" size="10"><br>
		<span  id="pMessage" class="eMessage"></span></td>
	</tr>
	<tr height="40"><td bgcolor="yellow">Name</td>
	<td><input type="text" name="name" id="name" value="" size="10"><br>
		<span  id="nMessage" class="eMessage"></span></td>
	</tr>
	<tr height="40"><td bgcolor="yellow">Level</td>
	<td><select name="lev" id="lev">
		<option value="A">VIP</option>
		<option value="B">나무</option>
		<option value="C">잎새</option>
		<option value="D" selected="selected">새싹</option>
	</select></td>
	</tr>
	<tr height="40"><td bgcolor="yellow">BirthDay</td>
	<td><input type="date" name="birthd" id="birthd" ><br>
		<span id="bMessage" class="eMessage"></span></td>
	</tr>
	<tr height="40"><td bgcolor="yellow">Point</td>
	<td><input type="text" name="point" id="point" value="" size="10"><br>
		<span id="oMessage" class="eMessage"></span></td>
	</tr>
	<tr height="40"><td bgcolor="yellow">Weight</td>
	<td><input type="text" name="weight" id="weight" value="" size="10"><br>
			<span id="wMessage" class="eMessage"></span></td>
	</tr>
	<tr height="40"><td bgcolor="yellow">추천인</td>
	<td><input type="text" name="rid" id="rid"></td>
	</tr>
	<tr height="40"><td bgcolor="yellow">Image</td>
	<td><input type="file" name="uploadfilef" id="uploadfilef"><br>
		<img src=""  class="select_img"/>
			<script>
			// 이미지를 업로드할 페이지는 server에 존재하기 때문에,
			// 아무리 해당 파일의 경로를 src로 지정해도 이미지는 표시될 수 없기 때문에
			// 이를 해결하기 위해 FileReader이라는 Web API 를 사용
			// => 이 를 통해 url data를 얻을 수 있음.
			//    ( https://developer.mozilla.org/ko/docs/Web/API/FileReader)
			// ** FileReader
			// => 웹 애플리케이션이 비동기적으로 데이터를 읽기 위하여 읽을 파일을 가리키는File
			//    혹은 Blob 객체를 이용해 파일의 내용을(혹은 raw data버퍼로) 읽고 
			//    사용자의 컴퓨터에 저장하는 것을 가능하게 해줌.	
			// => FileReader.onloadload 이벤트의 핸들러.
			//    읽기 동작이 성공적으로 완료 되었을 때마다 발생.
			// => e.target : 이벤트를 유발시킨 DOM 객체

 				$("#uploadfilef").change(function(){
   					if(this.files && this.files[0]) {
    					var reader = new FileReader;
   			 			reader.onload = function(e) {
    		 				$(".select_img").attr("src", e.target.result)
    		 					.width(70).height(100); 
    		 				} // onload_function
   		 				reader.readAsDataURL(this.files[0]);
    		 		} // if
  				}); // change 
 			</script>
	</td>
	</tr>
	<tr height="40"><td></td>
	<td><input type="submit" id="submit" value="가입" onclick="return inCheck()" disabled>&nbsp;
	    <input type="reset"  value="취소">&nbsp;
	    <input type="button" value="AxJoin" id="ajoin">
	</tr>
</table>
</form></h3>
<a href="home"> [Home]</a>
</body>
</html>