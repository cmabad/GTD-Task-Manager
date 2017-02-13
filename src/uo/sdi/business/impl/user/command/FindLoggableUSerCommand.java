package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.dto.User;
import uo.sdi.dto.types.UserStatus;
import uo.sdi.persistence.Persistence;

public class FindLoggableUSerCommand<T> implements Command<User> {

	private String login;
	private String password;

	public FindLoggableUSerCommand(String login, String password) {
		this.login = login;
		this.password = password;
	}

	@Override
	public User execute() throws BusinessException {
		User user = Persistence.getUserDao()
						.findByLoginAndPassword(login, password);
		
		return (user != null && user.getStatus().equals( UserStatus.ENABLED ))
				? user
				: null;
	}

}
