package uo.sdi.acciones.tasks;

import java.util.Date;
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

public class CrearTareaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareas;
		List<Category> listaCategorias;
		
		HttpSession session=request.getSession();
		User user=((User)session.getAttribute("user"));
		String listadoActual = ((String)session.getAttribute("listadoActual"));
		
		String categoriaElegida=request.getParameter("categoria");
		String nombreTarea=request.getParameter("taskName");
		
		try {
			TaskService taskService = Services.getTaskService();
			
			Task newTask = new Task();
			newTask.setTitle(nombreTarea);
			newTask.setUserId(user.getId());
			
			if(!categoriaElegida.equals(""))
					newTask.setCategoryId(Long.parseLong(categoriaElegida));
			
			newTask.setCreated(new Date());
			
			if(listadoActual.equals("Hoy"))
				newTask.setPlanned(new Date());
			
			taskService.createTask(newTask);
			
			listaTareas=taskService.findFinishedInboxTasksByUserId(user.getId());
			request.setAttribute("listaTareas", listaTareas);
			
			listaCategorias=taskService.findCategoriesByUserId(user.getId());
			request.setAttribute("listaCategorias", listaCategorias);
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido creando la tarea: %s",
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
