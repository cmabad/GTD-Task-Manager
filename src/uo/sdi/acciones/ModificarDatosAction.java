package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import uo.sdi.dto.util.Cloner;


public class ModificarDatosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		String nuevoEmail=request.getParameter("email");
		String oldPassword=request.getParameter("password");
		String newPassword=request.getParameter("newPassword");
		String newPassword2=request.getParameter("newPassword2");
		
		HttpSession session=request.getSession();
		User user=((User)session.getAttribute("user"));
		User userClone=Cloner.clone(user);
		
		if(nuevoEmail!=null)	
			userClone.setEmail(nuevoEmail);
		
		if(oldPassword!=null && newPassword!=null && newPassword2!=null)
		{
			if(oldPassword.trim().equals(userClone.getPassword()) && 
				newPassword.trim().equals(newPassword2.trim()))
					userClone.setPassword(newPassword);
		}
		try {
			UserService userService = Services.getUserService();
			userService.updateUserDetails(userClone);
			Log.debug("Modificado email de [%s] con el valor [%s]", 
					userClone.getLogin(), nuevoEmail);
			session.setAttribute("user",userClone);
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido actualizando el email de [%s] a [%s]: %s", 
					user.getLogin(),nuevoEmail,b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
