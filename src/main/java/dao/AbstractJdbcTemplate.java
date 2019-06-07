package dao;

import dao.interfaces.GenericDao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.List;


public class AbstractJdbcTemplate<T> implements GenericDao<T> {

	private JdbcTemplate jdbcTemplate = JdbcConnector.jdbcTemplate(JdbcConnector.dataSource());
	private Class<T> clazz;
	private BeanWrapperRowMapper<T> beanWrapperRowMapper;
	private String tableName;

	public AbstractJdbcTemplate(Class<T> clazz) {
		this.clazz = clazz;
		beanWrapperRowMapper = new BeanWrapperRowMapper<>(clazz);
		this.tableName = clazz.getAnnotation(Table.class).name();
	}

	public List<T> getAll() {
		String query = "SELECT * FROM " + tableName;
		return jdbcTemplate.query(query, beanWrapperRowMapper);
	}

	public T save(T t) {
		String query = ("INSERT into " + tableName + " (" + getColumnsOfTObject() + ") values (" + getValuesOfTObject(t) + ")");
		jdbcTemplate.update(query);
		return t;
	}

	public T get(Long id) {
		return jdbcTemplate.queryForObject(
				"SELECT * FROM " + tableName + " WHERE ID=" + id, beanWrapperRowMapper);

	}

	public T update(T t) {
		String[] values = getValuesOfTObject(t).split(",");
		String[] columns = getColumnsOfTObject().split(",");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < values.length; i++) {
			sb.append(columns[i]).append("=").append(values[i]).append(", ");
		}
		sb.deleteCharAt(sb.length() - 2);
		String query = ("UPDATE " + tableName + " SET " + sb + "WHERE ID=" + getValuesOfTObject(t).substring(1, 2));
		jdbcTemplate.update(query);
		return t;
	}

	public void delete(Long id) {
		String query = "DELETE FROM " + tableName + " WHERE ID=" + id;
		jdbcTemplate.update(query);
	}

	private String getColumnsOfTObject() {
		StringBuilder columns = new StringBuilder();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			columns.append(f.getName()).append(",");
		}
		columns.deleteCharAt(columns.length() - 1);
		return columns.toString().toUpperCase();
	}

	private String getValuesOfTObject(T object) {
		StringBuilder values = new StringBuilder();
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			try {
				values.append("'").append(f.get(object)).append("',");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		values.deleteCharAt(values.length() - 1);
		return values.toString();
	}
}
