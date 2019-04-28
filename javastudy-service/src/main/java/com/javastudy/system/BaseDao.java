package com.javastudy.system;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.HibernateTransactionManager;

public class BaseDao {
	
	static final Logger objLogger = LoggerFactory.getLogger(BaseDao.class);
	public SessionFactory sessionFactory;
	HibernateTransactionManager transactionManager;

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.transactionManager = new HibernateTransactionManager(
				sessionFactory);
	}

	public HibernateTransactionManager getHibernateTransactionManager()
			throws DaoException {
		return this.transactionManager;
	}

	public void init() {
		objLogger.info("BaseDaoImpl start!");
	}

	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public <T extends BaseEntity> T create(T entity) throws DaoException {
		try {
			getSession().save(entity);
			return entity;
		} catch (DataAccessException e) {
			String message = "创建实体[Class@" + entity.getClass().getName()
					+ "]失败，异常信息：" + e.getMessage();

			objLogger.error(message);
			throw new DaoException(message, e);
		}
	}

	public <T extends BaseEntity> T update(T entity) throws DaoException {
		try {
			getSession().update(entity);
			return entity;
		} catch (DataAccessException e) {
			String message = "更新实体[Class@" + entity.getClass().getName()
					+ "]失败，异常信息：" + e.getMessage();

			objLogger.error(message);
			throw new DaoException(message, e);
		}
	}

	public <T extends BaseEntity> T merge(T entity) throws DaoException {
		try {
			getSession().merge(entity);
			return entity;
		} catch (DataAccessException e) {
			String message = "更新实体[Class@" + entity.getClass().getName()
					+ "]失败，异常信息：" + e.getMessage();

			objLogger.error(message);
			throw new DaoException(message, e);
		}
	}

	public <T extends BaseEntity> T saveOrUpdate(T entity) throws DaoException {
		try {
			getSession().saveOrUpdate(entity);
			return entity;
		} catch (DataAccessException e) {
			String message = "创建/更新实体[Class@" + entity.getClass().getName()
					+ "]失败，异常信息：" + e.getMessage();

			objLogger.error(message);
			throw new DaoException(message, e);
		}
	}

	public <T extends BaseEntity> void delete(T entity) throws DaoException {
		try {
			getSession().delete(entity);
		} catch (DataAccessException e) {
			String message = "删除实体[Class@" + entity.getClass().getName()
					+ "]失败，异常信息：" + e.getMessage();

			objLogger.error(message);
			throw new DaoException(message, e);
		}
	}

	public <T extends BaseEntity> void deleteById(Class<T> clazz,
			String idName, Serializable idValue) throws DaoException {
		String deleteHql = "delete from " + clazz.getName() + " where "
				+ idName + "=?";

		execDeleteHql(deleteHql, new Object[]{idValue});
	}

	public <T extends BaseEntity> void deleteAll(Class<T> clazz)
			throws DaoException {
		Iterator it;
		try {
			Criteria criteria = getSession().createCriteria(clazz);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			List list = criteria.list();
			if ((null != list) && (!(list.isEmpty())))
				for (it = list.iterator(); it.hasNext();)
					getSession().delete(it.next());
		} catch (DataAccessException e) {
			String message = "删除所有实体[Class@" + clazz.getName() + "]失败，异常信息："
					+ e.getMessage();

			objLogger.error(message);
			throw new DaoException(message, e);
		}
	}

	public <T extends BaseEntity> void deleteAll(Collection<T> entities)
			throws DaoException {
		Iterator it;
		try {
			if ((null != entities) && (!(entities.isEmpty())))
				for (it = entities.iterator(); it.hasNext();)
					getSession().delete(it.next());
		} catch (DataAccessException e) {
			String message = "删除实体集合[Class@" + entities.getClass().getName()
					+ ",Size@" + entities.size() + "]失败，异常信息：" + e.getMessage();

			objLogger.error(message);
			throw new DaoException(message, e);
		}
	}

	public <T extends BaseEntity> List<T> loadAll(Class<T> clazz)
			throws DaoException {
		Criteria criteria = getSession().createCriteria(clazz);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List<?> find(String queryString) throws DaoException {
		try {
			Query query = getSession().createQuery(queryString);
			return query.list();
		} catch (DataAccessException e) {
			String message = "执行查询 " + queryString + " 失败";
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public List<?> find(String queryString, boolean cacheable)
			throws DaoException {
		try {
			Query query = getSession().createQuery(queryString);
			query.setCacheable(cacheable);
			return query.list();
		} catch (DataAccessException e) {
			String message = "执行查询 " + queryString + " 失败";
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public Object findbyPage(String queryString, int StartRow, int PageSize,
			Object[] values) throws DaoException {
		try {
			Query query = getSession().createQuery(queryString);
			query.setFirstResult(StartRow);
			query.setMaxResults(PageSize);
			if (values != null) {
				for (int j = 0; j < values.length; ++j) {
					query.setParameter(j, values[j]);
				}
			}
			return query.list();
		} catch (DataAccessException e) {
			String message = "执行hql失败 ,hql=" + queryString;
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public List<?> find(String queryString, Object param, boolean cacheable)
			throws DaoException {
		return find(queryString, new Object[]{param}, cacheable);
	}

	public List<?> find(String queryString, Object param) throws DaoException {
		return find(queryString, new Object[]{param}, false);
	}

	public List<?> find(String queryString, Object[] params)
			throws DaoException {
		return find(queryString, params, false);
	}

	public List<?> find(String queryString, Object[] params, boolean cacheable)
			throws DaoException {
		try {
			Query queryObject = getSession().createQuery(queryString);
			if ((null != params) && (params.length > 0)) {
				int i = 0;
				for (int len = params.length; i < len; ++i)
					queryObject.setParameter(i, params[i]);
			}
			queryObject.setCacheable(cacheable);
			return queryObject.list();
		} catch (DataAccessException e) {
			StringBuffer paramString = new StringBuffer("");
			for (int i = 0; i < params.length; ++i) {
				paramString.append(params[i]);
				paramString.append(" ");
			}
			String message = "执行参数为 " + paramString + "的查询 " + queryString
					+ " 失败";

			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public List<?> findByNamedQuery(String queryName) throws DaoException {
		return findByNamedQuerys(queryName, null);
	}

	public List<?> findByNamedQuery(String queryName, Object param)
			throws DaoException {
		return findByNamedQuerys(queryName, new Object[]{param});
	}

	public List<?> findByNamedQuerys(String queryName, Object[] params)
			throws DaoException {
		try {
			Query queryObject = getSession().getNamedQuery(queryName);
			if ((null != params) && (params.length > 0)) {
				int i = 0;
				for (int len = params.length; i < len; ++i) {
					queryObject.setParameter(i, params[i]);
				}
			}
			return queryObject.list();
		} catch (DataAccessException e) {
			StringBuffer paramString = new StringBuffer("");
			for (int i = 0; i < params.length; ++i) {
				paramString.append(params[i]);
				paramString.append(" ");
			}
			String message = "执行参数为 " + paramString + "命名为 " + queryName
					+ " 的查询失败";

			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public List<?> findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object paramValue) throws DaoException {
		return findByNamedQueryAndNamedParamArr(queryName,
				new String[]{paramName}, new Object[]{paramValue});
	}

	public List<?> findByNamedQueryAndNamedParamArr(String queryName,
			String[] paramNames, Object[] paramValues) throws DaoException {
		try {
			Query query = getSession().getNamedQuery(queryName);
			if ((null != paramNames) && (null != paramValues)
					&& (paramNames.length == paramValues.length)) {
				int i = 0;
				for (int len = paramNames.length; i < len; ++i) {
					query.setParameter(paramNames[i], paramValues[i]);
				}
			}

			return query.list();
		} catch (DataAccessException e) {
			String message = "无法执行findByNamedQueryAndNamedParam操作";
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public Object queryByNamedQuery(String queryName,
			Map<String, Object>[] values) throws DaoException {
		try {
			Query query = getSession().getNamedQuery(queryName);
			if ((values != null) && (values.length > 0)) {
				for (int j = 0; j < values.length; ++j) {
					for (String key : values[j].keySet()) {
						query.setParameter(key, values[j].get(key));
					}
				}
			}
			return query.list();
		} catch (DataAccessException e) {
			String message = "执行NameQuery失败 queryName=" + queryName;
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public Object execSqlQuery(String querySql, Object[] values)
			throws DaoException {
		try {
			Query query = getSession().createSQLQuery(querySql);
			if (values != null) {
				for (int j = 0; j < values.length; ++j) {
					query.setParameter(j, values[j]);
				}
			}
			return query.list();
		} catch (DataAccessException e) {
			String message = "执行查询语句，失败 querySql=" + querySql;
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public <E extends BaseEntity> Object execSqlQuery(String querySql,
			Class<E> clz, Object[] values) throws DaoException {
		try {
			Query query = getSession().createSQLQuery(querySql).addEntity(clz);
			if (values != null) {
				for (int j = 0; j < values.length; ++j) {
					query.setParameter(j, values[j]);
				}
			}
			return query.list();
		} catch (DataAccessException e) {
			String message = "执行查询语句，失败 querySql=" + querySql;
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public Object execUpdateHql(String hql, Object[] values)
			throws DaoException {
		return execHql(hql, values);
	}

	public Object execDeleteHql(String hql, Object[] values)
			throws DaoException {
		return execHql(hql, values);
	}

	public Object execNameQuery(String queryName, Map<String, Object>[] values)
			throws DaoException {
		try {
			Query query = getSession().getNamedQuery(queryName);
			if ((values != null) && (values.length > 0)) {
				for (int j = 0; j < values.length; ++j) {
					for (String key : values[j].keySet()) {
						query.setParameter(key, values[j].get(key));
					}
				}
			}
			return Integer.valueOf(query.executeUpdate());
		} catch (DataAccessException e) {
			String message = "执行NameQuery失败 queryName=" + queryName;
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public Object execNameQueryByPage(String queryName, int StartRow,
			int PageSize, Map<String, Object>[] values) throws DaoException {
		try {
			Query query = getSession().getNamedQuery(queryName);
			if (values != null) {
				for (int j = 0; j < values.length; ++j) {
					query.setParameter(j + 1, values[j]);
				}
			}
			query.setFirstResult(StartRow);
			query.setMaxResults(PageSize);
			return query.list();
		} catch (DataAccessException e) {
			String message = "执行NameQuery失败 queryName=" + queryName;
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public Object execNameQueryAndNamedParam(String queryName,
			String paramName, Object paramValue) throws DaoException {
		try {
			Query query = getSession().getNamedQuery(queryName);
			query.setParameter(paramName, paramValue);
			return Integer.valueOf(query.executeUpdate());
		} catch (DataAccessException e) {
			String message = "执行NameQuery失败 queryName=" + queryName;
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public Object ExecNameQueryAndNamedParamArr(String queryName,
			String[] paramNames, Object[] paramValues) throws DaoException {
		try {
			Query query = getSession().getNamedQuery(queryName);
			for (int i = 0; i < paramNames.length; ++i) {
				query.setParameter(paramNames[i], paramValues[i]);
			}
			return Integer.valueOf(query.executeUpdate());
		} catch (DataAccessException e) {
			String message = "执行NameQuery失败 queryName=" + queryName;
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	private Object execHql(String hql, Object[] values) throws DaoException {
		try {
			Query query = getSession().createQuery(hql);
			if (values != null) {
				for (int j = 0; j < values.length; ++j) {
					query.setParameter(j, values[j]);
				}
			}
			return Integer.valueOf(query.executeUpdate());
		} catch (DataAccessException e) {
			String message = "执行更新语句，失败 hql=" + hql;
			objLogger.error(message, e);
			throw new DaoException(message, e);
		}
	}

	public void flushDatabase() {
		getSession().flush();
	}

}
