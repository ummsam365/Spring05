<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** BoardList Page Criteria **</title>
<!-- 컨트롤러의 test 1) 에서 setViewName 을 사용하지 않으면 자원화일 절대경로 사용해야함. 
	 url 아랫쪽 HOME 과 새글쓰기의 요청명도 절대 경로 사용해야함. 	--> 
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
<script>
$(function(){
	$('#searchBtn').on("click",function(){
				self.location="listcri"
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
<h2>** BoardList Page Criteria **</h2>
<img  src="resources/image/bar.gif"><br>
<div id="searchBar">
	<select name="searchType" id="searchType">
		<option value="n" <c:out value="${pageMaker.cri.searchType==null ? 'selected':'' }"/>>
		---</option>
		<option value="t" <c:out value="${pageMaker.cri.searchType eq 't' ? 'selected':'' }"/>>
		Title</option>
		<option value="c" <c:out value="${pageMaker.cri.searchType eq 'c' ? 'selected':'' }"/>>
		Content</option>
		<option value="w" <c:out value="${pageMaker.cri.searchType eq 'w' ? 'selected':'' }"/>>
		Writer</option>
		<option value="tc" <c:out value="${pageMaker.cri.searchType eq 'tc' ? 'selected':'' }"/>>
		Title or Content</option>
		<option value="cw" <c:out value="${pageMaker.cri.searchType eq 'cw' ? 'selected':'' }"/>>
		Content or Writer</option>
		<option value="tcw" <c:out value="${pageMaker.cri.searchType eq 'tcw' ? 'selected':'' }"/>>
		Title or Content or Writer</option>
	</select>
	<input type="text" name="keyword" id="keyword" value="${pageMaker.cri.keyword}">
	<button id="searchBtn">Search</button>
</div>
<table width=800 border="0">
<tr align="center" height=30 bgcolor="PowderBlue">
	<td>Seq</td><td>Title</td><td>ID</td>
	<td>RegDate</td><td>Count</td>
</tr>
<c:forEach var="mm" items="${Banana}">
	<tr align="center" height=30>
	<td>${mm.seq}</td>
	<td  align="left">
		<!-- 댓글의 들여쓰기 -->
		<c:if test="${mm.indent>0}">
			<c:forEach begin="1" end="${mm.indent}">
				<span>&nbsp;&nbsp;</span>
			</c:forEach>
			<span style="color:orange">re..</span>
		</c:if>
		
		<!--  조회수 증가   -->
		<c:if test="${logID!=null}">
		    <!-- 조회수 증가를 위해 글쓴이의 id 도 포함   -->
			<a href="bdetail?seq=${mm.seq}&id=${mm.id}">${mm.title}</a>
		</c:if>
		<c:if test="${logID==null}">
			${mm.title}
		</c:if>
	</td>
	<td>${mm.id}</td>
	<td>${mm.regdate}</td><td>${mm.cnt}</td>
	</tr>
</c:forEach>
</table>

<c:if test="${message != null}">
 => ${message}<br>
</c:if>
<!--	     	
  ** Page Criteria 추가   
    1) First << ,  Prev < : enabled 여부
    2) sPage~ePage 까지 perPageNo 값 만큼 출력, 
    3) Next >  ,   Last >> : enabled 여부	
-->
<hr>
<div align="center">
<!-- 1) -->
<c:if test="${pageMaker.prev}">
	<a href="listcri${pageMaker.makeSearch(1)}">First&nbsp;</a>
	<a href="listcri${pageMaker.makeSearch(pageMaker.sPageNo-1)}">&laquo;&nbsp;</a>
					<!-- listcri?currPage=8&PerPageRow=10 -->
</c:if>

<!--  2)  -->
<c:forEach begin="${pageMaker.sPageNo}"
		   end="${pageMaker.ePageNo}" var="i">
	<c:choose>
 		<c:when test="${pageMaker.cri.currPage==i}">
 			<font size="5" color="Orange">${i}</font>&nbsp;
 		</c:when>
 		<c:otherwise>
 			<a href="listcri${pageMaker.makeSearch(i)}">${i}</a>&nbsp;
 		</c:otherwise>
 	</c:choose>	   
	<%-- <c:out value="${pageMaker.cri.currPage == i ? 'class=active':''}"/> --%>
  </c:forEach>
<!--  3) --> 
  <c:if test="${pageMaker.next && pageMaker.ePageNo > 0}">
	<a href="listcri${pageMaker.makeSearch(pageMaker.ePageNo+1)}">&nbsp;&raquo;</a>
	<a href="listcri${pageMaker.makeSearch(pageMaker.lastPageNo)}">&nbsp;Last</a>
  </c:if>

</div>
<hr>
<c:if test="${logID!=null}">
	<a href="/green/binsertf">[새글등록]</a>&nbsp;
</c:if>
<a href="/green/home">Home</a><br>

</body>
</html>