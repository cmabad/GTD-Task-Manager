package uo.sdi.persistence;

import java.util.List;

import uo.sdi.dto.Category;
import uo.sdi.persistence.util.GenericDao;

public interface CategoryDao extends GenericDao<Category, Long> {

	List<Category> findByUserId(Long userId);
	int deleteAllFromUserId(Long userId);
	Category findByUserIdAndName(Long userId, String name);

}
