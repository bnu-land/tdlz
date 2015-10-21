<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String msg=request.getParameter("login");msg=msg==null?"":msg; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="j_spring_security_check" method="POST">
<table>
	<tr>
		<td>Login Message:</td>
		<td><%=msg%>!</td>
	</tr>
	<tr>
		<td>用户:</td>
		<td><input type='text' name='j_username' value="admin"></td>
	</tr>
	<tr>
		<td>密码:</td>
		<td><input type='password' name='j_password' value="admin"></td>
	</tr>
	<tr>
		<td><input name="reset" type="reset"></td>
		<td><input name="submit" type="submit" value="登录"></td>
	</tr>
</table>
</form>

</body>
</html>