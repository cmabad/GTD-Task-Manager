package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import alb.util.log.Log;

public class ListarCategoriasAction implements Accion {

	private static final long EXAMPLE_USER_ID=1;
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		
		List<Category> listaCategorias;
		
		try {
			TaskService taskService = Services.getTaskService();
			listaCategorias=taskService.findCategoriesByUserId(EXAMPLE_USER_ID);
			request.setAttribute("listaCategorias", listaCategorias);
			Log.debug("Obtenida lista de categorías conteniendo [%d] categorías", 
					listaCategorias.size());
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido obteniendo lista de categorías: %s",
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
