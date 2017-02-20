package uo.sdi.acciones.category;

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

public class EditarCategoriaAction implements Accion {
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Task> listaTareas;
		List<Category> listaCategorias;
		
		String categoriaElegida=request.getParameter("categoria");
		String eliminar = request.getParameter("command");
		String nombreCategoria = request.getParameter("categoryName");
		
		HttpSession session=request.getSession();
		User user=((User)session.getAttribute("user"));
		
		try {
			TaskService taskService = Services.getTaskService();
			listaCategorias=taskService.findCategoriesByUserId(user.getId());
			
			if("0".equals(eliminar)) {
				
				if(categoriaElegida.equals("new")) {
					if(!nombreCategoria.trim().equals("")) {
						
						for(Category cat : listaCategorias) {
							if(cat.getName().equals(nombreCategoria)) {
								resultado = "FRACASO";
								break;
							}
						}
						
						if(resultado.equals("EXITO")) {
							Category cat = new Category();
							
							cat.setUserId(user.getId());
							cat.setName(nombreCategoria);
							
							taskService.createCategory(cat);
						}
						
					} else {
						//Name not valid
						resultado = "FRACASO";
					}
				} else {
					
					if(!nombreCategoria.trim().equals("")) {
					
						for(Category cat : listaCategorias) {
							if(cat.getName().equals(nombreCategoria)) {
								resultado = "FRACASO";
								break;
							}
						}
					
						if(resultado.equals("EXITO")) {
							Category cat = taskService.findCategoryById(Long.parseLong(categoriaElegida));
							
							cat.setName(nombreCategoria);
							
							taskService.updateCategory(cat);
						}
					
					} else {
						resultado = "FRACASO";
					}
				}
				
			} else {
				
				if(categoriaElegida.equals("new")) {
					//do Nothing
				} else {
					//Warn about deletion
					listaTareas=taskService.findTasksByCategoryId(Long.parseLong(categoriaElegida));
					
					taskService.deleteCategory(Long.parseLong(categoriaElegida));
				}
			}		
		
			
			listaTareas=taskService.findInboxTasksByUserId(user.getId());
			request.setAttribute("listaTareas", listaTareas);
			
			listaCategorias=taskService.findCategoriesByUserId(user.getId());
			request.setAttribute("listaCategorias", listaCategorias);
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
