package uo.sdi.business.impl.user.command;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.dto.User;
import uo.sdi.persistence.Persistence;

public class ListUsersCommand implements Command<List<User>> {

	@Override
	public List<User> execute() throws BusinessException {
		return Persistence.getUserDao().findAll();
	}

}
