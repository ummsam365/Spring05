<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** BoardList Spring_ver02 **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
<script src="resources/jqLib/axTest01.js"></script>
<script src="resources/jqLib/axTest03.js"></script>
<style>
	.result { color:blue; }
	
	#content{
		display:none; 
		/* jQuery 메서드 => show() / hide() 적용됨 */
		width:300px;
		height:100px;
		background:MintCream ;
		z-index:100;
		position:absolute;
	}
</style>


</head>
<body>
<h2>** BoardList Spring_Ajax **</h2>
<img  src="resources/image/bar.gif"><br>
<table width=800 border="0">
<tr align="center" height=30 bgcolor="GreenYellow">
	<td>Seq</td><td>Title</td><td>ID</td>
	<td>RegDate</td><td>Count</td>
</tr>
<!--  반복문에 이벤트 적용 
=> 매개변수처리에 varStatus 활용, ajax, json Test  
=> Login 여부에 무관하게 처리함.
// Test 1. 타이틀 클릭하면, 하단(resultArea2)에 글 내용 출력하기  => => JS, aTag, axbDetail( , ) 
// Test 2. 타이틀 클릭하면, 글목록의 아랫쪽(span result)에 글 내용 출력하기 => JS, aTag, jsbDetail( , ) 
// Test 3. seq 에 마우스 오버시에 별도의 DIV에 글내용 표시 되도록 하기 => jQuery
// 			=> seq 에 마우스 오버 이벤트
//			=> content 를 표시할 div : 표시/사라짐 
//			=> 마우스 포인터의 위치 를 이용해서   div 의 표시위치 지정
-->
<c:forEach var="mm" items="${Banana}" varStatus="vs">
	<tr align="center" height=30>
	<td class="cseq textLink" id="${mm.seq}">${mm.seq}</td>
	<td  align="left">
		<!-- 댓글 들여쓰기      -->
		<c:if test="${mm.indent>0}">
			<c:forEach begin="1" end="${mm.indent}">
				<span>&nbsp;&nbsp;</span>
			</c:forEach>
			<span style="color:orange">re..</span>
		</c:if>
		<!-- Test1 ----------- 
		<a href="javascript:;" onclick="axBDetail(${mm.seq},'${mm.id}')">${mm.title}</a>-->
		<!-- Test2 -----------
			=> jsonView 로 content 값만 서버로부터 가져와서 result span 에 출력     
			** 이벤트 객체 전달
			=> 이벤트 리스너 함수의 첫 번째 매개변수에 event 라는 이름으로 전달
			-->
		<a href="javascript:;" onclick="jsBDetail(event,${mm.seq},${vs.index})">${mm.title}</a>
	</td>
	<td>${mm.id}</td>
	<td>${mm.regdate}</td><td>${mm.cnt}</td>
	</tr>
	<tr><td></td>
		<td colspan="4"><span id="result${vs.index}" class="result"></span></td>
	</tr>
</c:forEach>
</table>
<div id="content"></div>
<hr>
<c:if test="${message != null}">
 => ${message}
</c:if>
<c:if test="${logID!=null}">
	<a href="binsertf">[새글등록]</a>&nbsp;
</c:if>
<c:if test="${logID==null}">
	<a href="loginf">[Login]</a>&nbsp;
</c:if>
<a href="home">Home</a><br>
</body>
</html>