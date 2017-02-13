package uo.sdi.business;

import java.util.List;

import uo.sdi.business.exception.BusinessException;
import uo.sdi.dto.User;

public interface AdminService {

	public void deepDeleteUser(Long id) throws BusinessException;
	public void disableUser(Long id) throws BusinessException;
	public void enableUser(Long id) throws BusinessException;

	public List<User> findAllUsers() throws BusinessException;
	public User findUserById(Long id) throws BusinessException;

}
