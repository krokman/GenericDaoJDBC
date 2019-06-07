package dao.interfaces;

import java.util.List;

public interface GenericDao<T> {
	T save(T t);

	T get(Long id);

	T update(T t);

	void delete(Long id);

	List<T> getAll();
}
