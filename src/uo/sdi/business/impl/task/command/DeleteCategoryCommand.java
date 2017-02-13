package uo.sdi.business.impl.task.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.persistence.Persistence;

public class DeleteCategoryCommand implements Command<Void> {

	private Long catId;

	public DeleteCategoryCommand(Long catId) {
		this.catId = catId;
	}

	@Override
	public Void execute() throws BusinessException {
		Persistence.getTaskDao().deleteAllFromCategory( catId );
		Persistence.getCategoryDao().delete( catId );
		return null;
	}

}
