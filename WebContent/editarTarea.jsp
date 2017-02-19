<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="comprobarNavegacion.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head> <title>TaskManager - Editar Tarea</title>
<body>
  <form action="editarTarea" method="post">

 	<center><h2>Editor de Tareas</h2></center>
 	<hr><br>
 	<table align="center">
    	<tr> 
    		<td align="right">Nombre de la Tarea: </td>
	    	<td><input type="text" name="newTaskName" align="left" size="50" value="${task.title}"> </td>
      	</tr>
      	<tr> 
    		<td align="right">Comentario de la Tarea: </td>
	    	<td><input type="text" name="newTaskComment" align="left" size="50" value="${task.comments}"></td>
      	</tr>
      	<tr> 
    		<td align="right">Fecha Planeada de la Tarea (formato dd-MMM-yyyy): </td>
	    	<td><input type="text" name="newFechaPlaneada" align="left" size="50" value="${task.planned}"></td>
      	</tr>
      	<tr><td align="right">Nueva Categoria de la Tarea:</td></tr>
			<tr>
			    <td align="right"><select name="categoria" size="1">
			    <c:forEach var="entry" items="${listaCategorias}" varStatus="i">
					<option value="${entry.id}">${entry.name}</option>
				</c:forEach>
					<option value="">None</option>
			    </select></td>
			</tr>
      	<tr>
    	    <td><input type="submit" value="Terminar"/></td>
      	</tr>
      </table>
   </form>
   
   <%@ include file="pieDePagina.jsp" %>
</body>
</html>