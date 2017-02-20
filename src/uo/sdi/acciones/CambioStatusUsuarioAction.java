package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;


public class CambioStatusUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		String id=request.getParameter("id");
		String login=request.getParameter("login");
		String nuevo=request.getParameter("nuevo");
				
		try {
			if ("en".equals(nuevo)){
				UserService userService = Services.getUserService();
				userService.enableUser(new Long(id));
				Log.debug("Usuario [%s] habilitado", login);
			}
			else if ("dis".equals(nuevo)){
				UserService userService = Services.getUserService();
				userService.disableUser(new Long(id));
				Log.debug("Usuario [%s] deshabilitado", login);
			}
			else{
				throw new BusinessException();
			}
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido cambiando el estado de [%s]", login);
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
