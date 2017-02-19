package uo.sdi.acciones.tasks;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.dto.User;
import alb.util.log.Log;

public class FinalizarTareaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareas;
		List<Category> listaCategorias;
		
		HttpSession session=request.getSession();
		User user=((User)session.getAttribute("user"));
		String id = request.getParameter("id");
		
		try {
			TaskService taskService = Services.getTaskService();
			taskService.markTaskAsFinished(Long.parseLong(id));
			
			listaTareas=taskService.findInboxTasksByUserId(user.getId());
			request.setAttribute("listaTareas", listaTareas);
			
			listaCategorias=taskService.findCategoriesByUserId(user.getId());
			request.setAttribute("listaCategorias", listaCategorias);
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido marcando como terminada la tarea: %s",
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
