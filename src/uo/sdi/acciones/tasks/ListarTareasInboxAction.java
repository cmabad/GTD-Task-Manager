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

public class ListarTareasInboxAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareas;
		List<Category> listaCategorias;
		
		HttpSession session=request.getSession();
		User user=((User)session.getAttribute("user"));
		
		String filtro = request.getParameter("command");
		
		try {
			TaskService taskService = Services.getTaskService();
			
			if(filtro!= null && "0".equals(filtro)) 
				listaTareas=taskService.findFinishedInboxTasksByUserId(user.getId());
			else 
				listaTareas=taskService.findInboxTasksByUserId(user.getId());
				
			listaCategorias=taskService.findCategoriesByUserId(user.getId());
			request.setAttribute("listaCategorias", listaCategorias);
			request.setAttribute("listaTareas", listaTareas);
			Log.debug("Obtenida lista de tareas de Inbox conteniendo [%d] tareas", 
					listaTareas.size());
						
			session.setAttribute("listadoActual", "Inbox");
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de tareas de Inbox: %s",
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
