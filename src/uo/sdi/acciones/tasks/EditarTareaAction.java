package uo.sdi.acciones.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class EditarTareaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareas;
		List<Category> listaCategorias;

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		HttpSession session=request.getSession();
		User user=((User)session.getAttribute("user"));
		String id = ((String)session.getAttribute("idEscogido"));
		
		String nombreTarea=request.getParameter("newTaskName");
		String commentarioTarea=request.getParameter("newTaskComment");
		
		Date fechaPlaneadaTarea = null;
		try {
			if(!request.getParameter("newFechaPlaneada").equals(""))
				fechaPlaneadaTarea=formatter.parse(request.getParameter("newFechaPlaneada"));
		} catch (ParseException p) {
			Log.debug("La fecha introducida es incorrecta: %s", p.getMessage());
			resultado="FRACASO";
		}
		
		String categoriaElegida=request.getParameter("categoria");
		
		try {
			TaskService taskService = Services.getTaskService();
			Task task = ((Task)taskService.findTaskById(Long.parseLong(id)));
			
			if(!"".equals(nombreTarea))
				task.setTitle(nombreTarea);
			
			if(!"".equals(categoriaElegida))
				task.setCategoryId(Long.parseLong(categoriaElegida));
			else
				task.setCategoryId(null);
			
			if(fechaPlaneadaTarea!=null)
				task.setPlanned(fechaPlaneadaTarea);
			
			task.setComments(commentarioTarea);

			taskService.updateTask(task);
			
			listaTareas=taskService.findInboxTasksByUserId(user.getId());
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
