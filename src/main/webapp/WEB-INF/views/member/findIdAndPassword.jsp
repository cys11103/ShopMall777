<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<link href="css/shopping.css" rel="stylesheet">  
	<script type="text/javascript" src="member/findIdAndPassword.js"></script>
	</head>
	<body>
	<div id="wrap">
	<h1>아이디, 비밀번호찾기</h1>
	<br>
	<form name="findId" action="find_id" method="get">
	<table>
	<tr>
		<td align="right"><label> 이름</label></td>
		<td><input type="text" name="name" value=""></td>
	</tr>	
	<tr>
		<td align="right"><label> 이메일</label></td>
		<td><input type="text" name="email" value=""></td>
	</tr>
	<tr>
		<td align="center" colspan="2"><input type="button" value="아이디 찾기" onclick="findMemberId()"></td>
	</tr>
	</table>
	</form>
	<p><p><p><p><p><p><p><p><p><p><p><p>
	
	
	<form name="findPW">
	<table>
	<tr>
		<td align="right"><label> 아이디</label></td>
		<td><input type="text" name="memberId" value=""></td>
	</tr>	
	<tr>
		<td align="right"><label> 이름</label></td>
		<td><input type="text" name="name" value=""></td>
	</tr>	
	<tr>
		<td align="right"><label> 이메일</label></td>
		<td><input type="text" name="email" value=""></td>
	</tr>
	<tr>
		<td align="center" colspan="2"><input type="button" value="비밀번호 찾기" onclick="findPassword()"></td>
	</tr>
	</table>
	</form>
	</div>
</body>
</html>