
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>TABLA DE MULTIPLICAR</h1>

	<h2>DATOS</h2>

	<form method="post" action="TablaMultiplicar">
		<label>Número: </label> <input type="text" name="num"> <input
			type="submit" />
	</form>

	<c:if test="${lista != null }">

		<h2>RESULTADO</h2>

		<table>
			<c:forEach items="${lista }" var="rec">
				<tr>
					<td>${rec.num1}</td>
					<td>${rec.operador}</td>
					<td>${rec.num2}</td>
					<td>=</td>
					<td>${rec.rpta}</td>
				</tr>
			</c:forEach>
		</table>

	</c:if>


	
</body>
</html>