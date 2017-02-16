package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class AdministrarUsuariosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<User> listaUsuarios;
			
		try {
			UserService userService = Services.getUserService();
			listaUsuarios=userService.listUsers();
			
			if (listaUsuarios.size()==0){
				Log.debug("No hay usuarios registrados");
				request.setAttribute("mensajeParaElUsuario", "No hay usuarios registrados");
				return "FRACASO";
			}
			request.setAttribute("listaUsuarios", listaUsuarios);
			Log.debug("Obtenida lista de usuarios para administrar");
		}
		catch (BusinessException b) {
			Log.debug("Se ha producido un error obteniendo la lista de usuarios: %s",
					b.getMessage());
			resultado="FRACASO";
		}
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
