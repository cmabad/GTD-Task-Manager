package uo.sdi.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.*;
import uo.sdi.acciones.tasks.ListarTareasHoyAction;
import uo.sdi.acciones.tasks.ListarTareasInboxAction;
import uo.sdi.acciones.tasks.ListarTareasPorCategoriaAction;
import uo.sdi.acciones.tasks.ListarTareasSemanaAction;
import uo.sdi.dto.User;
import uo.sdi.persistence.PersistenceException;

public class Controlador extends javax.servlet.http.HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, Accion>> mapaDeAcciones; // <rol, <opcion, objeto Accion>>
	private Map<String, Map<String, Map<String, String>>> mapaDeNavegacion; // <rol, <opcion, <resultado, JSP>>>

	public void init() throws ServletException {  
		crearMapaAcciones();
		crearMapaDeNavegacion();
    }
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws IOException, ServletException {
		
		String accionNavegadorUsuario, resultado, jspSiguiente;
		Accion objetoAccion;
		String rolAntes, rolDespues;
		
		try {
			accionNavegadorUsuario=request.getServletPath().replace("/","");  // Obtener el string que hay a la derecha de la última /
				
			rolAntes=obtenerRolDeSesion(request);
			
			objetoAccion=buscarObjetoAccionParaAccionNavegador(rolAntes, 
					accionNavegadorUsuario);
			
			request.removeAttribute("mensajeParaElUsuario");
				
			resultado=objetoAccion.execute(request,response);
				
			rolDespues=obtenerRolDeSesion(request);
			
			jspSiguiente=buscarJSPEnMapaNavegacionSegun(rolDespues, 
					accionNavegadorUsuario, resultado);

			request.setAttribute("jspSiguiente", jspSiguiente);

		} catch(PersistenceException e) {
			
			request.getSession().invalidate();
			
			Log.error("Se ha producido alguna excepción relacionada con la persistencia [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario", 
					"Error irrecuperable: contacte con el responsable de la aplicación");
			jspSiguiente="/login.jsp";
			
		} catch(Exception e) {
			
			request.getSession().invalidate();
			
			Log.error("Se ha producido alguna excepción no manejada [%s]",
					e.getMessage());
			request.setAttribute("mensajeParaElUsuario", 
					"Error irrecuperable: contacte con el responsable de la aplicación");
			jspSiguiente="/login.jsp";
		}
			
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jspSiguiente); 
			
		dispatcher.forward(request, response);			
	}			
	
	
	private String obtenerRolDeSesion(HttpServletRequest req) {
		HttpSession sesion=req.getSession();
		if (sesion.getAttribute("user")==null)
			return "ANONIMO";
		else
			if (((User)sesion.getAttribute("user")).getIsAdmin())
				return "ADMIN";
			else
				return "USUARIO";
	}

	// Obtiene un objeto accion en funci�n de la opci�n
	// enviada desde el navegador
	private Accion buscarObjetoAccionParaAccionNavegador(String rol, String opcion) {
		
		Accion accion=mapaDeAcciones.get(rol).get(opcion);
		Log.debug("Elegida acción [%s] para opción [%s] y rol [%s]",accion,opcion,rol);
		return accion;
	}
	
	
	// Obtiene la p�gina JSP a la que habr� que entregar el
	// control el funci�n de la opci�n enviada desde el navegador
	// y el resultado de la ejecuci�n de la acci�n asociada
	private String buscarJSPEnMapaNavegacionSegun(String rol, String opcion, String resultado) {
		
		String jspSiguiente=mapaDeNavegacion.get(rol).get(opcion).get(resultado);
		Log.debug("Elegida página siguiente [%s] para el resultado [%s] tras realizar [%s] con rol [%s]",
				jspSiguiente,resultado,opcion,rol);
		return jspSiguiente;		
	}
		
		
	private void crearMapaAcciones() {
		
		mapaDeAcciones=new HashMap<String,Map<String,Accion>>();
		
		Map<String,Accion> mapaPublico=new HashMap<String,Accion>();
		mapaPublico.put("validarse", new ValidarseAction());
		mapaPublico.put("listarCategorias", new ListarCategoriasAction());
		mapaDeAcciones.put("ANONIMO", mapaPublico);
		
		Map<String,Accion> mapaRegistrado=new HashMap<String,Accion>();
		mapaRegistrado.put("modificarDatos", new ModificarDatosAction());
		mapaRegistrado.put("cerrarSesion", new CerrarSesionAction());
		mapaRegistrado.put("listarTareas", new ListarTareasHoyAction());
		mapaRegistrado.put("listarTareasSemana", new ListarTareasSemanaAction());
		mapaRegistrado.put("listarTareasInbox", new ListarTareasInboxAction());
		mapaRegistrado.put("listarTareasPorCategoria", new ListarTareasPorCategoriaAction());
		mapaDeAcciones.put("USUARIO", mapaRegistrado);
		
		Map<String,Accion> mapaAdmin=new HashMap<String,Accion>();
		mapaAdmin.put("modificarDatos", new ModificarDatosAction());
		mapaAdmin.put("cerrarSesion", new CerrarSesionAction());
		mapaAdmin.put("listarTareas", new ListarTareasHoyAction());
		mapaAdmin.put("listarTareasSemana", new ListarTareasSemanaAction());
		mapaAdmin.put("listarTareasInbox", new ListarTareasInboxAction());
		mapaAdmin.put("listarTareasPorCategoria", new ListarTareasPorCategoriaAction());
		mapaAdmin.put("administrarUsuarios", new AdministrarUsuariosAction());
		mapaDeAcciones.put("ADMIN", mapaAdmin);
	}
	
	
	private void crearMapaDeNavegacion() {
				
		mapaDeNavegacion=new HashMap<String,Map<String, Map<String, String>>>();

		// Crear mapas auxiliares vacíos
		Map<String, Map<String, String>> opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		Map<String, String> resultadoYJSP=new HashMap<String, String>();

		// Mapa de navegación de anónimo
		resultadoYJSP.put("FRACASO","/login.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarCategorias.jsp");
		resultadoYJSP.put("FRACASO","/login.jsp");
		opcionResultadoYJSP.put("listarCategorias", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/login.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);
		
		mapaDeNavegacion.put("ANONIMO",opcionResultadoYJSP);
		
		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		resultadoYJSP=new HashMap<String, String>();
		
		// Mapa de navegación de usuarios normales
		resultadoYJSP.put("EXITO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarTareas", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareasSemana.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarTareasSemana", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareasInbox.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarTareasInbox", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareasPorCategoria.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarTareasPorCategoria", resultadoYJSP);
		
		mapaDeNavegacion.put("USUARIO",opcionResultadoYJSP);
		
		// Crear mapas auxiliares vacíos
		opcionResultadoYJSP=new HashMap<String, Map<String, String>>();
		resultadoYJSP=new HashMap<String, String>();
		
		// Mapa de navegación del administrador
		resultadoYJSP.put("EXITO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("validarse", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/principalUsuario.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("modificarDatos", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("cerrarSesion", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareas.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarTareas", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareasSemana.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarTareasSemana", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareasInbox.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarTareasInbox", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String, String>();
		resultadoYJSP.put("EXITO","/listarTareasPorCategoria.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		opcionResultadoYJSP.put("listarTareasPorCategoria", resultadoYJSP);
		
		resultadoYJSP=new HashMap<String,String>();
		resultadoYJSP.put("EXITO","/userManagement.jsp");
		resultadoYJSP.put("FRACASO","/principalUsuario.jsp");
		
		mapaDeNavegacion.put("ADMIN",opcionResultadoYJSP);
		
	}
			
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}