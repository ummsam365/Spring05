<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Ajax Test Main Form **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
<script src="resources/jqLib/memberCheck.js"></script>
<script src="resources/jqLib/axTest01.js"></script>
<script src="resources/jqLib/axTest02.js"></script>
<script src="resources/jqLib/axTest03.js"></script>
</head>
<body>
<h2>** Ajax Test Main Form **</h2>
<br>
<span id="jsloginf" class="textLink">[JsLogin]</span>&nbsp;
<span id="ajoinf" class="textLink">[AxJoin]</span>&nbsp;
<span id="amlist" class="textLink">[AxMList]</span>&nbsp;
<span id="ablist" class="textLink">[AxBList]</span><br>
<hr>
<c:if test="${logID != null}">
	* ${logID}, ${logName} 님 안녕 하세요 ~~ &nbsp;&nbsp;&nbsp;
</c:if>
<a href="home">[Home]</a>
<hr>
<div id="resultArea1"></div>
<div id="resultArea2"></div>
</body>
</html>