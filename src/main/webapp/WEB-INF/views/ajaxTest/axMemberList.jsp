<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** MemberList Spring MVC2 **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<!-- 
	JS 의 function 은 Parent 에 Link 가 있으면 가능 하지만,
	JQ 는 개별 Tag를 인식할 수 있어야 실행 가능 하기 때문에 현재 Page에도 외부 문서를 선언 해 주어야 함. -->       
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
<script src="resources/jqLib/axTest01.js"></script>
<script src="resources/jqLib/axTest02.js"></script>

</head>
<body>
<h2>** MemberList Spring AjaxTest **</h2>
<table width=800 border="1">
<tr align="center" height=30 bgcolor="Orange">
	<td>I D</td><td>Password</td><td>Name</td>
	<td>Lev</td><td>BirthDay</td><td>Point</td>
	<td>Weight</td><td>추천인</td><td>Image</td><td>삭제</td>
</tr>
<c:forEach var="mm" items="${Apple}">
	<tr align="center" height=30>
	
<!-- **	반복문에 event 적용하기
	1) jQuery : class , this 이용
	<td><span class="cc textLink">${mm.id}</span></td>
	
	2) JScript : Tag의 onclick 이벤트 이용 
		=> function의 매개변수 이용 : aidList('banana') 
		=> a Tag 이용 : href="javascript:;" 또는  href="#"  비교
			<a href="#"            .... scroll 위치 이동 
				href에 #id를 주게되면 id에 해당하는 element에 포커스를 맞추게 되는데,
     			만약 #만 있고 id가 없을 경우에는 포커스가 top으로 올라감.
			<a href="javascript:;" .... 사용하면 해결       
	-->
	<td><a href="javascript:;" onclick="aidList('${mm.id}')" class="textLink">${mm.id}</a></td>
	<!-- => id 가 banana 인 글 목록 출력 : aidList('banana') 
	** 삭제 버튼 만들기 (관리자 모드)
		=> 삭제버튼 클릭하면  confirm Dialog 로 "정말로 삭제 하십니까 ?"
	 	      확인 후 삭제하기  	
	 	=> jQuery : class , this 이용 
	-->
	<td>${mm.password}</td><td>${mm.name}</td>
	<td>${mm.lev}</td>
	<td>${mm.birthd}</td>
	<td><fmt:formatNumber value="${mm.point}" pattern="##,###,###"/></td>
	<td>${mm.weight}</td>
	<td>${mm.rid}</td>
	<td><img src="${mm.uploadfile}" width="70" height="80"></td>
	<td><span class="del textLink" id="${mm.id}">Delete</span></td>
	</tr>
</c:forEach>
</table>
<hr>
<a href="home">Home</a><br>
</body>
</html>