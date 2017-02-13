package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ValidarseAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		String nombreUsuario=request.getParameter("nombreUsuario");
		HttpSession session=request.getSession();
		if (session.getAttribute("user")==null) {
			UserService userService = Services.getUserService();
			User userByLogin=null;
			try {
				userByLogin = userService.findLoggableUser(nombreUsuario, 
						nombreUsuario+"123");
			} catch (BusinessException b) {
				session.invalidate();
				Log.debug("Algo ha ocurrido intentando iniciar sesión [%s]: %s", 
						nombreUsuario,b.getMessage());
				request.setAttribute("mensajeParaElUsuario", b.getMessage());
				resultado="FRACASO";
			}
			if (userByLogin!=null) {
				session.setAttribute("user", userByLogin);
				int contador=Integer.parseInt((String)request.getServletContext()
						.getAttribute("contador"));
				request.getServletContext().setAttribute("contador", 
						String.valueOf(contador+1));
				session.setAttribute("fechaInicioSesion", new java.util.Date());
				Log.info("El usuario [%s] ha iniciado sesión",nombreUsuario);
			}
			else {
				session.invalidate();
				Log.info("El usuario [%s] no está registrado",nombreUsuario);
				request.setAttribute("mensajeParaElUsuario", "El usuario ["+
						nombreUsuario+"] no está registrado");
				resultado="FRACASO";
			}
		}
		else
			if (!nombreUsuario.equals(session.getAttribute("user"))) {
				Log.info("Se ha intentado iniciar sesión como [%s] teniendo la sesión iniciada como [%s]",
						nombreUsuario,((User)session.getAttribute("user")).getLogin());
				request.setAttribute("mensajeParaElUsuario", 
						"Se ha intentado iniciar sesión como ["+nombreUsuario+
						"] teniendo la sesión iniciada como ["+
						((User)session.getAttribute("user")).getLogin()+"]");
				session.invalidate();
				resultado="FRACASO";
			}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
