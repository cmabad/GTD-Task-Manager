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

public class ListarTareasPorCategoriaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareas;
		List<Category> listaCategorias;
		
		String categoriaElegida=request.getParameter("categoria");
		String filtro = request.getParameter("command");
		
		HttpSession session=request.getSession();
		User user=((User)session.getAttribute("user"));
		
		try {
			TaskService taskService = Services.getTaskService();
			listaCategorias=taskService.findCategoriesByUserId(user.getId());
			
			if(categoriaElegida!=null ) {
				
				if(filtro!=null) {
					if("0".equals(filtro)) {
						listaTareas=taskService.findFinishedTasksByCategoryId(Long.parseLong(categoriaElegida));
						request.setAttribute("listaTareas", listaTareas);
						Log.debug("Obtenida lista de tareas de Categoria conteniendo [%d] tareas", 
								listaTareas.size());
					} else {
						listaTareas=taskService.findTasksByCategoryId(Long.parseLong(categoriaElegida));
						request.setAttribute("listaTareas", listaTareas);
						Log.debug("Obtenida lista de tareas de Categoria conteniendo [%d] tareas", 
								listaTareas.size());
					}		
				} else {

					listaTareas=taskService.findTasksByCategoryId(Long.parseLong(categoriaElegida));
					request.setAttribute("listaTareas", listaTareas);
					
					Log.debug("Obtenida lista de tareas de Categoria conteniendo [%d] tareas", 
							listaTareas.size());
				}
				
			} else {
				
				listaTareas=taskService.findTasksByCategoryId((long) -1);
				request.setAttribute("listaTareas", listaTareas);
				Log.debug("Obtenida lista de tareas de Categoria conteniendo [%d] tareas", 
						listaTareas.size());
				
			}
			request.setAttribute("listaCategorias", listaCategorias);
			session.setAttribute("listadoActual", "PorCategoria");
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
