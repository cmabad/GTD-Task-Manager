package uo.sdi.business.impl.task.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.util.TaskCheck;
import uo.sdi.dto.Task;
import uo.sdi.persistence.Persistence;
import uo.sdi.persistence.TaskDao;

public class UpdateTaskCommand implements Command<Void> {

	private Task task;

	public UpdateTaskCommand(Task task) {
		this.task = task;
	}

	@Override
	public Void execute() throws BusinessException {
		TaskCheck.titleIsNotNull(task);
		TaskCheck.titleIsNotEmpty(task);
		if ( task.getCategoryId() != null) {
			TaskCheck.categoryExists( task );
		}
		
		TaskDao tDao = Persistence.getTaskDao();
		
		Task previous = tDao.findById( task.getId() );
		checktaskAlreadyExist(previous);
		checkUserNotChanged(previous);
		
		task.setCreated( previous.getCreated() ); // change ignored
		tDao.update( task );
		return null;
	}

	private void checktaskAlreadyExist(Task previous) throws BusinessException {
		BusinessCheck.isNotNull( previous, "The task does not exist");
	}

	private void checkUserNotChanged(Task previous) throws BusinessException {
		BusinessCheck.isTrue( task.getUserId().equals( previous.getUserId()),
			"A task cannot be changed to other user"
		);
	}

}
