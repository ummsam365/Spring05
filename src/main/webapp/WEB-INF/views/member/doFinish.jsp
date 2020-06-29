<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** DoFinish Spring MVC2 **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
</head>
<body>
<h3>** DoFinish Spring MVC2 **</h3>

<c:if test="${fCode=='JS'}">
	<h3>${joinID} 님 회원 가입 되었습니다~~</h3>
	<a href="loginf">로그인 하기</a>
</c:if>
<c:if test="${fCode=='JF'}">
	<h3>회원 가입 실패~~</h3>
	<h3>다시 하시겠습니까 ?</h3>
	<a href="joinf">[회원가입]</a>&nbsp;&nbsp;
	<a href="#" onclick="history.back()">[다시 하기]</a> 
</c:if>
<c:if test="${fCode=='DS'}">
	<h3>${deleteID} 님 회원 탈되 되었습니다~~<br>
		1 개월 후 재 가입 가능 합니다 ~~<br>
	</h3>
</c:if>
<c:if test="${fCode=='DF'}">
	<h3>회원 가입 실패~~</h3>
	<h3>다시 하시겠습니까 ?</h3>
	<a href="#" onclick="history.back()">[다시 하기]</a> 
</c:if>
<c:if test="${fCode=='BN'}">
	<h3>글 내용이 없습니다 ~~</h3>
	<h3>다시 하시겠습니까 ?</h3>
	<a href="#" onclick="history.back()">[다시 하기]</a> 
</c:if>

<c:if test="${fCode=='BI'}">
	<h3>새글 등록에 오류가 있어서 실패했습니다~~~</h3>
	<h3>다시 하시겠습니까 ?</h3>
	===> <a href="binsertf">[새글 등록]</a>
</c:if>

<c:if test="${fCode=='BU'}">
	<h3>글 수정에 오류가 있어서 실패했습니다~~~</h3>
	<h3>다시 하시겠습니까 ?</h3>
	===> <a href="javascript:history.go(-1)">[다시 하기]</a> 
</c:if>

<c:if test="${fCode=='BD'}">
	<h3>글 삭제에 오류가 있어서 실패했습니다~~~</h3>
	<h3>다시 하시겠습니까 ?</h3>
	===> <a href="#" onclick="history.back()">[다시 하기]</a> 
</c:if>

<c:if test="${fCode=='BR'}">
	<h3>댓글 등록에 오류가 있어서 실패했습니다~~~</h3>
	<h3>다시 하시겠습니까 ?</h3>
	===> <a href="rinsertf">[댓글 등록]</a>
</c:if>


<hr>
<a href="home">HOME</a>
</body>
</html>