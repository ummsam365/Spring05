<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** MemberList Paging **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
</head>
<body>
<h2>** MemberList Mybatis Paging **</h2>
<img src="resources/image/bar.gif">
<table width=800 border="1">
<tr align="center" height=30 bgcolor="Lavender">
	<td>I D</td><td>Password</td><td>Name</td>
	<td>Lev</td><td>BirthDay</td><td>Point</td>
	<td>Weight</td><td>추천인</td>
</tr>
<c:forEach var="list" items="${Banana}">
<tr align="center" height=30>
	<td>${list.id}</td>
	<td>${list.password}</td>
	<td>${list.name}</td>
	<!-- => lev 등급표시하기   A:관리자   B:나무   C:잎새    D:새싹 -->
	<td>${list.lev}:
	<c:choose>
		<c:when test="${list.lev=='A'}"> 관리자</c:when>
		<c:when test="${list.lev=='B'}"> 나무</c:when>
		<c:when test="${list.lev=='C'}"> 잎새</c:when>
		<c:when test="${list.lev=='D'}"> 새싹</c:when>
		<c:otherwise> Error</c:otherwise>
	</c:choose>
	</td>
	<td>${list.birthd}</td>
	<td>${list.point}</td>
	<td>${list.weight}</td>
	<td>${list.rid}</td>
</tr>
</c:forEach>
</table>
<hr>
<c:if test="${message != null}">
 => ${message}
</c:if>
<hr>
<div align="center">
<!-- 1) -->
<c:choose>
	<c:when test="${sPage>perPageNo}">
		<a href="mplist?currPage=1">First</a>&nbsp;
		<a href="mplist?currPage=${sPage-1}">Prev</a>&nbsp;&nbsp;
	</c:when>
	<c:otherwise>
		<font color="gray">First&nbsp;Prev&nbsp;&nbsp;</font>
	</c:otherwise>
</c:choose>
<!-- 2) -->
<c:forEach  var="i"  begin="${sPage}" end="${ePage}">
	<c:choose>
		<c:when test="${i==currPage}">
			<font size="5" color="Orange">${i}</font>
		</c:when>
		<c:otherwise>
			<a href="mplist?currPage=${i}">${i}</a>
		</c:otherwise>
	</c:choose>
</c:forEach>
<!-- 3) -->
<c:choose>
	<c:when test="${ePage<totalPageNo}">
		<a href="mplist?currPage=${ePage+1}">&nbsp;&nbsp;Next</a>
		<a href="mplist?currPage=${totalPageNo}">&nbsp;Last</a>
	</c:when>
	<c:otherwise>
		<font color="gray">&nbsp;&nbsp;Next&nbsp;Last</font>
	</c:otherwise>
</c:choose>
</div>
<hr>

<a href="home">[Home]</a>
</body>
</html>