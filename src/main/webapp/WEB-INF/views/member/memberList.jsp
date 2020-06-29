<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** MemberList Mybatis **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
</head>
<body>
<h2>** MemberList Mybatis **</h2>
<img src="resources/image/bar.gif">
<table width=800 border="1">
<tr align="center" height=30 bgcolor="pink">
	<td>I D</td><td>Password</td><td>Name</td>
	<td>Lev</td><td>BirthDay</td><td>Point</td>
	<td>Weight</td><td>추천인</td><td>Image</td>
</tr>
<c:forEach var="list" items="${Banana}">
<tr align="center" height=30>
	<td>${list.id}</td>
	<td>${list.password}</td>
	<td>${list.name}</td>
	<!-- => lev 등급표시하기   A:관리자   B:나무   C:잎새    D:새싹 -->
	<td>${list.lev}
	<%-- sql 구문에서 DECOE 적용 
	<c:choose>
		<c:when test="${list.lev=='A'}"> 관리자</c:when>
		<c:when test="${list.lev=='B'}"> 나무</c:when>
		<c:when test="${list.lev=='C'}"> 잎새</c:when>
		<c:when test="${list.lev=='D'}"> 새싹</c:when>
		<c:otherwise> Error</c:otherwise>
	</c:choose> --%>
	</td>
	<td>${list.birthd}</td>
	<td>${list.point}</td>
	<td>${list.weight}</td>
	<td>${list.rid}</td>
	<td><img src="${list.uploadfile}" width="70" height="80"></td>
</tr>
</c:forEach>
</table>
<hr>
<c:if test="${message != null}">
 => ${message}
</c:if>
<a href="home">[Home]</a>
</body>
</html>