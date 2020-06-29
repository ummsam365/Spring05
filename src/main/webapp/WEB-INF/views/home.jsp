<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>** Home **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
<script src="resources/jqLib/axTest01.js"></script>
<script>
	function setClock() {
		var now = new Date() ;
		var t ='* Now   : '
			+ now.getFullYear()+'년'+(now.getMonth()+1)+'월'+now.getDate()+'일'
		 	+'_'+now.getHours()+':'+now.getMinutes()+':'+now.getSeconds() ;
		document.getElementById("clock").innerHTML=t;
		setTimeout("setClock()",1000)  // 1/1000 초 단위
	}
</script>

</head> 

<body onload="setClock()">
<h2>** Spring05 Mybatis !!! **</h2>
<b><span>* Start  : ${serverTime}.</span><br>
<span id="clock" style="color:blue;"></span><br>
<c:if test="${logID != null}">
	* ${logID}, ${logName}님 안녕 하세요 ~~ <br>
</c:if>	
</b>
<hr>
<img src="resources/image/letsgo.png" width="300" height="300">
<hr>
<div id="resultArea"></div>
<h3>
<c:if test="${logID == null}">
	<a href="joinf">Join</a>&nbsp;&nbsp;
	<a href="loginf">Login</a>&nbsp;&nbsp;
	<span id="aloginf" class="textLink">AxLogin</span>&nbsp;&nbsp;
	<span id="ploginf" class="textLink">PopLogin</span><br>
</c:if>

<c:if test="${logID != null}">
	<a href="mdetail">MyInfo</a>&nbsp;&nbsp;
	<a href="logout">LogOut</a>&nbsp;&nbsp;
	<a href="mdelete">회원탈퇴</a><br>
</c:if>
<hr>
	<a href="mlist">Member</a>&nbsp;
	<a href="mplist">MPage</a>&nbsp;
	<a href="mlistcri">MCri</a><br>
	<a href="blist">Board</a>&nbsp;
	<a href="plist">BPage</a>&nbsp;
	<a href="listcri">BCri</a><br>
	<!-- <a href="board/listcri">BCri</a><br><br> -->
	<a href="atestf">AjaxTest</a>&nbsp;
	<a href="etest">ExceptionTest</a>&nbsp;
	<a href="sngreen">성남그린</a>&nbsp;
	<a href="allgreen">그린</a>&nbsp;
	<a href="jeju">제주</a><br><br>
	
<hr>
<c:if test="${message != null}">=> ${message}</c:if>
</h3>
</body>
</html>
