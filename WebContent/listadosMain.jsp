<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>
<jsp:useBean id="today" class="java.util.Date" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Menu Principal de Listados</title>
<style>
    .red    {
        color: red;
	}
</style>
</head>
<body>
	<br/>
	<br/>
	<form action="listarTareasInbox" method="post">
		<input type="radio" name="command" value="0"/>Mostrar Terminadas
		<input type="radio" name="command" value="1" checked/>Mostrar Sin Terminar
		<input type="submit" value="Mostrar Tareas de Inbox Filtradas Tareas">
	</form>
	<br/>
	<a id="listarTareas" href="listarTareas">Mostrar Tareas Hoy</a>
	<br/>
	<a id="listarTareasSemana" href="listarTareasSemana">Mostrar Tareas de esta Semana</a>
	<br/>
	<form action="listarTareasPorCategoria" method="post">
		<br>
		<table align="left">
			<tr><td align="left">Escoja la categoria a buscar:</td></tr>
			<tr>
			    <td align="left"><select name="categoria" size="1">
			    <c:forEach var="entry" items="${listaCategorias}" varStatus="i">
					<option value="${entry.id}">${entry.name}</option>
				</c:forEach>
			    </select></td>
			</tr>
			<tr>
			    <td>
					<input type="radio" name="command" value="0"/>Mostrar Terminadas
					<input type="radio" name="command" value="1" checked/>Mostrar Sin Terminar
				</td>
			</tr>
			<tr>
			    <td align="left"><input type="submit"
					value="Buscar Tareas Por Categoria"></td>
			</tr>
		</table>
	</form>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<h3 align="left"> Añadir una Tarea Nueva </h3>
	<form action="newTarea" method="post">
		<br>
		<table align="left">
			<tr><td align="left">Nombre de la Tarea:</td></tr>
			<tr>
			    <td>
					<input type="text" id="taskName" name="taskName" />
				</td>
			</tr>
			<tr><td align="left">Categoria de la Tarea:</td></tr>
			<tr>
			    <td align="left"><select name="categoria" size="1">
			    <c:forEach var="entry" items="${listaCategorias}" varStatus="i">
					<option value="${entry.id}">${entry.name}</option>
				</c:forEach>
					<option value="">None</option>
			    </select></td>
			</tr>
			<tr>
			    <td align="left"><input type="submit"
					value="Añadir Tarea"></td>
			</tr>
		</table>
	</form>
	<br/>
	<table border="1" align="center">
			<tr>
				<th>Categoria</th>
				<th>ID</th>
				<th>Titulo</th>
				<th>Planeado</th>
			</tr>
		<c:forEach var="entry" items="${listaTareas}" varStatus="i">
			<tr id="item_${i.index}">
				<td>${entry.categoryId}</td>
				<td>${entry.id}</td>
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