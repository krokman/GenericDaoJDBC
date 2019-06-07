package dao.impl;

import dao.AbstractJdbcTemplate;
import dao.interfaces.CategoryDao;
import model.Category;

public class CategoryDaoImpl extends AbstractJdbcTemplate<Category> implements CategoryDao {
	public CategoryDaoImpl() {
		super(Category.class);
	}
}
