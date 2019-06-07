import dao.impl.CategoryDaoImpl;
import dao.impl.UserDaoImpl;
import dao.interfaces.CategoryDao;
import dao.interfaces.UserDao;
import model.Category;
import model.User;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		CategoryDao categoryDao = new CategoryDaoImpl();
		Category category = categoryDao.get(new Long(3));
		System.out.println(category.getId() + " " + category.getDescription());

		ArrayList<Category> al = new ArrayList<>(categoryDao.getAll());
		for (Category element : al) {
			System.out.println(element.getId());
		}
		category.setDescription("ASDASDSAD");
		categoryDao.update(category);

		UserDao userDaoImpl = new UserDaoImpl();
		ArrayList<User> ul = new ArrayList<>(userDaoImpl.getAll());

		for (User user : ul) {
			System.out.println(user.getId() + user.getEmail());
		}
	}
}
