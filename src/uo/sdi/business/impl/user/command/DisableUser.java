package uo.sdi.business.impl.user.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.persistence.Persistence;

public class DisableUser implements Command<Void> {

	private Long id;
	
	public DisableUser(Long id){
		this.id = id;
	}	
	
	@Override
	public Void execute() throws BusinessException {
		
		Persistence.getUserDao().disableUser(this.id);

		return null;
	}

}
