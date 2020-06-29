<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** MemberList Page Criteria **</title>
<!-- 컨트롤러의 test 1) 에서 setViewName 을 사용하지 않으면 자원화일 절대경로 사용해야함. 
	 url 아랫쪽 HOME 과 새글쓰기의 요청명도 절대 경로 사용해야함. 	--> 
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
<script>
$(function(){
	$('#searchBtn').on("click",function(){
				self.location="mlistcri"
					+"${pageMaker.makeQuery(1)}"
					+"&searchType="
					+$("#searchType").val()
					/* + $("select option:selected").val() */
					+"&keyword="
					+$("#keyword").val();
	});
}); // ready 


</script>
</head>
<body>
<h2>** MemberList Page Criteria **</h2>
<img  src="resources/image/bar.gif"><br>
<div id="searchBar">
	<select name="searchType" id="searchType">
		<option value="l" <c:out value="${pageMaker.cri.searchType==null ? 'selected':'' }"/>>
		---</option>
		<!-- <option value="l" 'selected'>---</option>  --> 
		<option value="i" <c:out value="${pageMaker.cri.searchType eq 'i' ? 'selected':'' }"/>>
		ID</option>
		<option value="n" <c:out value="${pageMaker.cri.searchType eq 'n' ? 'selected':'' }"/>>
		Name</option>
		<option value="r" <c:out value="${pageMaker.cri.searchType eq 'r' ? 'selected':'' }"/>>
		추천인</option>
		<option value="in" <c:out value="${pageMaker.cri.searchType eq 'in' ? 'selected':'' }"/>>
		ID or Name</option>
		<option value="ir" <c:out value="${pageMaker.cri.searchType eq 'ir' ? 'selected':'' }"/>>
		ID or 추천인</option>
		<option value="inr" <c:out value="${pageMaker.cri.searchType eq 'inr' ? 'selected':'' }"/>>
		ID or Name or 추천인</option>
	</select>
	<input type="text" name="keyword" id="keyword" value="${pageMaker.cri.keyword}">
	<button id="searchBtn">Search</button>
</div>
<table width=800 border="0">
<tr align="center" height=30 bgcolor="Violet">
	<td>I D</td><td>Password</td><td>Name</td>
	<td>Lev</td><td>BirthDay</td><td>Point</td>
	<td>Weight</td><td>추천인</td>
</tr>
<c:forEach var="list" items="${Banana}">
<tr align="center" height=30>
	<td>${list.id}</td>
	<td>${list.password}</td>
	<td>${list.name}</td>
	<!-- => lev 등급표시하기   A:관리자   B:나무   C:잎새    D:새싹  => sql 문으로처리-->
	<td>${list.lev}</td>
	<td>${list.birthd}</td>
	<td>${list.point}</td>
	<td>${list.weight}</td>
	<td>${list.rid}</td>
</tr>
</c:forEach>
</table>

<c:if test="${message != null}">
 => ${message}<br>
</c:if>
<!--  ** Page Criteria 추가  -->
<hr>
<div align="center">
<!-- 1) -->
<c:if test="${pageMaker.prev}">
	<a href="mlistcri${pageMaker.makeSearch(1)}">First&nbsp;</a>
	<a href="mlistcri${pageMaker.makeSearch(pageMaker.sPageNo-1)}">&laquo;&nbsp;</a>
</c:if>
<!--  2)  -->
<c:forEach begin="${pageMaker.sPageNo}"
		   end="${pageMaker.ePageNo}" var="i">
	<c:choose>
 		<c:when test="${pageMaker.cri.currPage==i}">
 			<font size="5" color="Orange">${i}</font>&nbsp;
 		</c:when>
 		<c:otherwise>
 			<a href="mlistcri${pageMaker.makeSearch(i)}">${i}</a>&nbsp;
 		</c:otherwise>
 	</c:choose>	   
  </c:forEach>
<!--  3) --> 
  <c:if test="${pageMaker.next && pageMaker.ePageNo > 0}">
	<a href="mlistcri${pageMaker.makeSearch(pageMaker.ePageNo+1)}">&nbsp;&raquo;</a>
	<a href="mlistcri${pageMaker.makeSearch(pageMaker.lastPageNo)}">&nbsp;Last</a>
  </c:if>

</div>
<hr>
<a href="listcri">[BoardCri]</a>&nbsp;
<a href="home">Home</a><br>

</body>
</html>