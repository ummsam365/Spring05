<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** PopUp Login Form **</title>
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
<style>
	body{   
  		background-color:#ADD8E6;
  		font-family: Verdana;
		}
	#wrap{     
 		margin: 0 20px;
		 }
	h1 {
 		font-family: "맑은고딕", Times, serif;
	  	font-size: 30px;
  		color: #696969;
  		font-weight: normal;
		}
	input[type=button], input[type=submit] {
  		float: left;
	}
</style>
</head>
<body>
<div id=wrap>
<h3>** PopUp Login Form **</h3>
<form action="plogin" method="post">
	User ID : <br>
	<input type="text" id="id" name="id" value="banana"><br>
	Password: <br>
	<input type="password" id=password name=password value="12345!"><br><br>
	<input type="submit" value="popLogin">&nbsp; 
    <input type="reset" value="Reset"><br>
</form>
<div  style="margin-top: 20px">
	<c:if test="${loginR=='T'}">
		부모창에 alert 으로 로그인 성공 메세지 출력 후 현재창 닫기<br>
		안녕 하세요 ~~~ ${id} 님 !!!<br>
		<script>
		opener.alert("${id} 님이  로그인 했습니다. !!!");
		opener.location.reload();
		self.close() ;
		</script>
	</c:if>
	<c:if test="${loginR=='F'}">
		=> ${id} 님은 id 또는  password 가 없습니다 !!!<br>
		=> 다시 하세요 ~~~<br>
	</c:if>
</div>
</div>
</body>
</html>