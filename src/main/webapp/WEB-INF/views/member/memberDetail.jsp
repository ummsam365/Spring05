<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Mybatis My Information **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
</head>
<body>
<h2><pre>
** Member SelectOne Spring MVC2 **

* I     D : ${myInfo.id}
* Password: ${myInfo.password}
* N a m e : ${myInfo.name}
* Level   : ${myInfo.lev}
* BirthDay: ${myInfo.birthd}
* Point   : ${myInfo.point}
* Weight  : ${myInfo.weight}
* 추천인  : ${myInfo.rid}
* Image  : <img src="${myInfo.uploadfile}" width="70" height="80">
</pre>
</h2>
<hr>
<h3>
<c:if test="${message != null}"> ${message}</c:if>
<hr>
<a href="mdetail?code=U">내정보 수정</a>&nbsp;&nbsp;
<a href="logout">[Logout]</a>&nbsp;&nbsp;
<a href="mdelete">[회원탈퇴]</a><br>
<a href="mlist">[MemberList]</a>&nbsp;&nbsp;
<a href="home">[Home]</a>
</h3>
</body>
</html>