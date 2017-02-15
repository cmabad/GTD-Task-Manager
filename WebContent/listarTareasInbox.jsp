<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<jsp:useBean id="today" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Listado de Tareas del Inbox del Usuario</title>
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
				<th>Titulo</th>
				<th>Planeado</th>
			</tr>
		<c:forEach var="entry" items="${listaTareasInbox}" varStatus="i">
			<tr id="item_${i.index}">
				<td><a href="mostrarTareas?id=${entry.id}">${entry.id}</a></td>
				<td>${entry.title}</td>
				 <c:choose>
		            <c:when test="${entry.planned le today}">
		                <td class="red"> ${entry.planned} </td> 
		            </c:when>
		            <c:otherwise>
		                <td> ${entry.planned} </td> 
		            </c:otherwise>
		        </c:choose>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>