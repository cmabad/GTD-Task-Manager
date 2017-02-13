package uo.sdi.business.impl.command;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.persistence.PersistenceException;
import uo.sdi.persistence.Persistence;
import uo.sdi.persistence.Transaction;

public class CommandExecutor<T> {
	
	public T execute(Command<T> cmd) throws BusinessException {
		Transaction trx = Persistence.newTransaction();
		trx.begin();
		try {

			T res = cmd.execute();
			trx.commit();
			
			return res;
		}
		catch(PersistenceException | BusinessException ex) {
			trx.rollback();
			throw ex;
		}
	}

}
