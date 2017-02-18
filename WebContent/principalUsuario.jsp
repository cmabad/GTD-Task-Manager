<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="comprobarNavegacion.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="uo.sdi.dto.User" import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>TaskManager - Página principal del usuario</title>
</head>
<body>
<i>Iniciaste sesión el <fmt:formatDate pattern="dd-MM-yyyy' a las 'HH:mm" 
								value="${sessionScope.fechaInicioSesion}"/>
								(usuario número ${contador})</i>
	<br/><br/>
	<jsp:useBean id="user" class="uo.sdi.dto.User" scope="session" />
	<table>
		<tr>
			<td>Id:</td><td id="id"><jsp:getProperty property="id" name="user" /></td>
		</tr>
		<tr>
			<td>Email:</td>
			<td id="email"><form action="modificarDatos" method="POST">
					<input type="text" name="email" size="15"
						value="<jsp:getProperty property="email" name="user"/>"> 
					<input type="submit" value="Modificar">
				</form>
			</td>
		</tr>
		<tr>
			<td>Contraseña:</td>
			<td id="password"><form action="modificarDatos" method="POST">
					<input type="text" name="password" size="15"
						value="Old Password"> 
					<input type="text" name="newPassword" size="15"
						value="New Password"> 
					<input type="text" name="newPassword2" size="15"
						value="New Password Again">
					<input type="submit" value="Modificar">
				</form>
			</td>
		</tr>
		<tr>
			<td>Es administrador:</td><td id="isAdmin"><jsp:getProperty property="isAdmin" name="user" /></td>
		</tr>
		<tr>
			<td>Login:</td><td id="login"><jsp:getProperty property="login" name="user" /></td>
		</tr>
		<tr>
			<td>Estado:</td><td id="status"><jsp:getProperty property="status" name="user" /></td>
		</tr>
	</table>
	<br/>
	<a id="listarTareasInbox" href="listarTareasInbox">Mostrar Inbox de Tareas</a>
	<br/>
	<%  User userAdmin = (User) request.getSession().getAttribute("user");
		if (userAdmin.getIsAdmin()){
			out.print("<a id=\"administrarUsuarios_link_id\" href=\"administrarUsuarios\">Administrar usuarios</a>");
			out.print("<br/>");
		}
	%>
	<a id="cerrarSesion_link_id" href="cerrarSesion">Cerrar sesión</a>
	
	<%@ include file="pieDePagina.jsp" %>
</body>
</html>
