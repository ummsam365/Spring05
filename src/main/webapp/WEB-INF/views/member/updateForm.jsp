<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Member Update Form **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
</head>
<body>
<h2>** Member Update Form Mybatis **</h2>
<form action="mupdate" method="post" enctype="multipart/form-data"> 
<table>
	<tr height="40"><td bgcolor="aqua">I D</td>
		<td><input type="text" name="id" value="${myInfo.id}" readonly="readonly"></td>
	</tr>
	<tr height="40"><td bgcolor="aqua">Password</td>
		<td><input type="password" name="password" value="${myInfo.password}"></td>
	</tr>
	<tr height="40"><td bgcolor="aqua">Name</td>
		<td><input type="text" name="name" value="${myInfo.name}"></td>
	</tr>
	<tr height="40"><td bgcolor="aqua">Level</td>
		<td><c:choose>
		 	<c:when test="${myInfo.lev=='A'}">
			<select name="lev">
				<option value="A" selected="selected">관리자</option>
				<option value="B">나무</option>
				<option value="C">잎새</option>
				<option value="D">새싹</option>
			</select>
			</c:when>
			<c:when test="${myInfo.lev=='B'}">
			<select name="lev">
				<option value="A">관리자</option>
				<option value="B" selected>나무</option>
				<option value="C">잎새</option>
				<option value="D">새싹</option>
			</select>
			</c:when>
			<c:when test="${myInfo.lev=='C'}">
			<select name="lev">
				<option value="A">관리자</option>
				<option value="B">나무</option>
				<option value="C" selected="selected">잎새</option>
				<option value="D">새싹</option>
			</select>
			</c:when>
			<c:when test="${myInfo.lev=='D'}">
			<select name="lev">
				<option value="A">관리자</option>
				<option value="B">나무</option>
				<option value="C">잎새</option>
				<option value="D" selected="selected">새싹</option>
			</select>
			</c:when>
			<c:otherwise>
			<select name="lev">
				<option value="A">관리자</option>
				<option value="B">나무</option>
				<option value="C">잎새</option>
				<option value="D">새싹</option>
				<option value="E" selected="selected">Error</option>
			</select>
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr height="40"><td bgcolor="aqua">BirthDay</td>
		<td><input type="date" name="birthd" value="${myInfo.birthd}"></td>
	</tr>
	<tr height="40"><td bgcolor="aqua">Point</td>
		<td><input type="text" name="point" value="${myInfo.point}"></td>
	</tr>
	<tr height="40"><td bgcolor="aqua">Weight</td>
		<td><input type="text" name="weight" value="${myInfo.weight}"></td>
	</tr>
	<tr height="40"><td bgcolor="aqua">추천인</td>
		<td><input type="text" name="rid" value="${myInfo.rid}"></td>
	</tr>
	<tr height="40"><td bgcolor="yellow">Image</td>
	<td><img src="${myInfo.uploadfile}" width="70" height="80" class="select_img">
		<input type="text" name="uploadfile" value="${myInfo.uploadfile}" hidden="true"><br>
		<input type="file" name="uploadfilef" id="uploadfilef">
		<script>
 			$("#uploadfilef").change(function(){
   				if(this.files && this.files[0]) {
    				var reader = new FileReader;
   					reader.onload = function(data) {
    					$(".select_img").attr("src", data.target.result)
    						.width(70).height(80); 
    					} // onload_function
   		 			reader.readAsDataURL(this.files[0]);
    			} // if
  			}); // change 
 		</script>	
	</td>
	</tr>
	<tr>
	<td></td><td><input type="submit" value="전송">&nbsp;&nbsp;
	  			 <input type="reset" value="취소"></td>
	</tr>
</table>
</form>
</body>
</html>