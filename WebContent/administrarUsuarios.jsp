<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<jsp:useBean id="today" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Administración de usuarios</title>
<style>
    .red    {
        color: red;
	}
</style>
</head>
<body>
	<table border="1" align="center">
			<tr>
				<th>ID</th>
				<th>Email</th>
				<th>Login</th>
				<th>Status</th>
			</tr>
		<c:forEach var="entry" items="${listaUsuarios}" varStatus="i">
			<tr id="item_${i.index}">
				<td>${entry.id}</td>
				<td>${entry.email}</td>
				<td>${entry.login}</td>
				
				<td>
				 <c:choose>
					<c:when test="${entry.status eq 'ENABLED'}">
			
					<a href="cambioStatusUsuario?nuevo=dis&id=${entry.id}&login=${entry.email}">Deshabilitar</a>
			
					</c:when>
					<c:otherwise>
						
					<a href="cambioStatusUsuario?nuevo=en&id=${entry.id}&login=${entry.email}">Habilitar</a>

						
					</c:otherwise>
				</c:choose>
				</td>
				
				<%
					//String checkbox = ${entry.status}.equals("enabled")?
						//	"checked" : "";
					//out.print("<form action=/finAdministrarUsuarios\">");
					//out.print("<input type=\"checkbox\" name=\"\" value="+
						//"\"Bike\"> I have a bike<br>
				//out.print("<td>");
				%>
			</tr>
		</c:forEach>
	</table>
	<a href="principalUsuario">Volver</a>
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>