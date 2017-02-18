package uo.sdi.acciones.navegacion;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.Accion;
import uo.sdi.business.Services;
import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.Category;
import uo.sdi.dto.User;

public class IrEditarTareaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		String resultado="EXITO";
		String id = request.getParameter("id");
		
		List<Category> listaCategorias;
		
		HttpSession session=request.getSession();
		User user=((User)session.getAttribute("user"));
		
		try {
			TaskService taskService = Services.getTaskService();
			listaCategorias=taskService.findCategoriesByUserId(user.getId());
			request.setAttribute("listaCategorias", listaCategorias);
		}
		catch (BusinessException b) {
			Log.debug("Algo ha ocurrido escogiendo la tarea: %s",
					b.getMessage());
			resultado="FRACASO";
		}
		
		session.setAttribute("idEscogido", id);
		return resultado;
	}
	
	@Override
	public String toString() {
		return getClass().getName();
	}
	
}
