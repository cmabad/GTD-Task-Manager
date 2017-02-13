package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;

public class CerrarSesionAction implements Accion {
 
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		Log.debug("Se ha cerrado la sesion");
		request.getSession().invalidate();
		
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
