package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.persistence.Persistence;


public class EliminarUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		String id=request.getParameter("id");
		String login=request.getParameter("login");
		
		Persistence.getUserDao().delete(new Long(id));
		
		Log.debug("Usuario [%s] eliminado", login);
		
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
