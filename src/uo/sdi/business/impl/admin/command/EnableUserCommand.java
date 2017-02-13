package uo.sdi.business.impl.admin.command;

import uo.sdi.business.exception.BusinessCheck;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.persistence.Persistence;
import uo.sdi.persistence.UserDao;

public class EnableUserCommand implements Command<Void> {

	private Long id;

	public EnableUserCommand(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		UserDao uDao = Persistence.getUserDao();
		
		User user = uDao.findById(id);
		BusinessCheck.isNotNull( user, "User does not exist" );
		
		user.setStatus( UserStatus.ENABLED );
		uDao.update( user );

		return null;
	}

}
