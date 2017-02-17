package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.UserService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class RegistrarseAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		String nombreUsuario=request.getParameter("nombreUsuario");
		String email = request.getParameter("email");
		String password=request.getParameter("password");
		String password2=request.getParameter("password2");
		
		/* Campos vacíos o contraseñas no coinciden */
		if (campoVacio(request,nombreUsuario,password,password2) 
				|| contraseñasDiferentes(request,password,password2)){
			Log.debug("Se ha producido un error en el registro. Campos vacíos "+
				"o contraseñas diferentes");
			return "FRACASO";
		}
		
		User user = new User(nombreUsuario,email,password);
		UserService userService = Services.getUserService();
		try {
			/* el usuario ya existe, contraseña es segura, email con estructura */
			userService.registerUser(user);
		} catch (BusinessException e) {
			request.setAttribute("mensajeParaElUsuario", "El usuario ya existe, "+
					"el email es incorrecto o la contraseña no es segura");
			Log.debug("Se ha producido un error en el registro.El usuario ya "
					+ "existe, el email es incorrecto o la contraseña no es segura");
			return "FRACASO";
		}
		
		request.getSession().setAttribute("user",user);
		// en caso de que el registro sea erróneo, no vamos a hacer que ponga
		// todo otra vez
		request.setAttribute("registroNombreUsuario", nombreUsuario);
		request.setAttribute("registroEmail",email);
		return resultado;
	}

	/**
	 * Comprueba si las contraseñas no son iguales
	 * @return
	 */
	private boolean contraseñasDiferentes(HttpServletRequest request, String pass, String pass2) {
		if (!pass.equals(pass2)){
			request.setAttribute("mensajeParaElUsuario", "Las contraseñas no coinciden");
			return true;
		}
		return false;
	}

	/**
	 * Comprueba que todos los campos del registro hayan sido rellenados.
	 * @param nombre
	 * @param pass
	 * @param pass2
	 * @return
	 */
	private boolean campoVacio(HttpServletRequest request, String nombre, String pass, String pass2) {
		if(pass==null || pass2==null || nombre == null){
			request.setAttribute("mensajeParaElUsuario", "Debe rellenar todos los campos");
			return true;
		}
			return false;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
