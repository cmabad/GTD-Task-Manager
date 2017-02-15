package uo.sdi.acciones.tasks;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Task;
import alb.util.log.Log;

public class ListarTareasPorCategoriaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareas;
		//HttpSession session=request.getSession();
		//User user=((User)session.getAttribute("user"));
		long category = (Long) request.getAttribute("category_ID");
		
		try {
			TaskService taskService = Services.getTaskService();
			listaTareas=taskService.findTasksByCategoryId(category);
			request.setAttribute("listaTareasPorCategoria", listaTareas);
			Log.debug("Obtenida lista de tareas de Categoria conteniendo [%d] tareas", 
					listaTareas.size());
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de tareas de Categoria: %s",
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
