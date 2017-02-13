package uo.sdi.business.impl.admin.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.persistence.CategoryDao;
import uo.sdi.persistence.Persistence;
import uo.sdi.persistence.TaskDao;
import uo.sdi.persistence.UserDao;

public class DeepDeleteUserCommand implements Command<Void> {

	private Long userId;

	public DeepDeleteUserCommand(Long id) {
		this.userId = id;
	}

	@Override
	public Void execute() throws BusinessException {
		TaskDao tDao = Persistence.getTaskDao();
		CategoryDao cDao = Persistence.getCategoryDao();
		UserDao uDao = Persistence.getUserDao();

		tDao.deleteAllFromUserId( userId );
		cDao.deleteAllFromUserId( userId );
		uDao.delete( userId );
		
		return null;
	}

}
