package uo.sdi.acciones.tasks;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class ListarTareasHoyAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareas;
		List<Category> listaCategorias;
		
		User user = (User)(request.getSession().getAttribute("user"));
		long userID = user.getId();
		
		try {
			TaskService taskService = Services.getTaskService();
			listaCategorias=taskService.findCategoriesByUserId(userID);
			listaTareas=taskService.findTodayTasksByUserId(userID);
			if (listaTareas.size()==0){
				Log.debug("No hay lista de tareas para hoy del usuario [%s]", 
						request.getSession().getAttribute("user"));
				request.setAttribute("mensajeParaElUsuario", "No tienes tareas para hoy");
				return "FRACASO";
			}
			request.setAttribute("listaTareas", listaTareas);
			request.setAttribute("listaCategorias", listaCategorias);
			Log.debug("Obtenida lista de tareas para hoy del usuario [%s]", 
					request.getSession().getAttribute("user"));
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de tareas: %s",
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
