<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Login Success **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
</head>

<body>
<h3><pre>
** Login Success **

${logID}, ${logName} 님 안녕하세요 ~~

* Login Info *
* Session  ID   : ${pageContext.session.id}
* Creation Time : ${pageContext.session.creationTime}
* LastAccessTime: ${pageContext.session.lastAccessedTime}
</pre>
<hr>
<a href="mdetail">내정보</a>&nbsp; 
<a href="logout">Logout</a>&nbsp;
<a href="mdelete">회원탈퇴</a><br>
<a href="mlist">MemberList</a>&nbsp;
<a href="blist">boardList</a>&nbsp;
<a href="home">Home</a><br>
</h3>
</body>
</html>