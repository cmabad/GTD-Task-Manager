<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head> <title>TaskManager - Inicie sesión</title>
<body>
  <form action="registrarse" method="post" name="registrarse_form_name">

 	<center><h1>Registro de usuario</h1></center>
 	<hr><br>
 	<table align="center">
    	<tr> 
    		<td align="right">Nombre de usuario: </td>
	    	<td><input type="text" name="nombreUsuario" align="left" size="15"></td>
      	</tr>
      	<tr> 
    		<td align="right">Email: </td>
	    	<td><input type="text" name="email" align="left" size="40"></td>
      	</tr>
      	<tr> 
    		<td align="right">Contraseña (mínimo 8 caracteres alfanuméricos): </td>
	    	<td><input type="password" name="password" align="left" size="15"></td>
      	</tr>
      	<tr>
      		<td align="right">Repita constraseña: </td>
	    	<td><input type="password" name="password2" align="left" size="15"></td>
      	</tr>
      	<tr>
    	    <td><input type="submit" value="Enviar"/></td>
      	</tr>
      </table>
   </form>
   <a id="volver_link_id" href="login.jsp">Volver</a>
   
   <%@ include file="pieDePagina.jsp" %>
</body>
</html>