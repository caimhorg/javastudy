package com.javastudy.system;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.QueryException;
import org.hibernate.QueryParameterException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import com.javastudy.utils.DataPage;


public class BaseDaoImpl<T> extends BaseDao {

    /**
     * 设置sessionfactory
     * @param sessionFactory 具体值
     */
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        super.setSessionFactory(sessionFactory);
    }
    
    /**
     * 创建hsql的query语句
     * @param hsql 需要创建的hsql语句
     * @return query对象
     * @throws BaseException 封装后的异常。
     */
    public Query createQuery(String hsql) throws Exception
    {
        try
        {
            Query query = getSession().createQuery(hsql);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 创建native sql的query语句
     * @param sql 需要创建的sql语句
     * @return sqlquery对象
     * @throws BaseException 封装后的异常。
     */
    public SQLQuery createNativeQuery(String sql) throws Exception
    {
        try
        {
            SQLQuery query = getSession().createSQLQuery(sql);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 获取以名字方式定义的sql
     * @param name sql定义的名字
     * @return query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getNameQuery(String name) throws Exception
    {
        try
        {
            return getSession().getNamedQuery(name);
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 保存对象
     * @param entity 保存的对象
     * @return T 保存的对象
     * @throws BaseException 封装后的异常。
     */
    public T save(final T entity) throws Exception
    {
        try
        {
            getSession().save(entity);
            return entity;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 更新对象
     * @param entity 更新的对象
     * @return T 更新的对象
     * @throws BaseException 封装后的异常。
     */
    public T updateOne(final T entity) throws Exception
    {
        try
        {
            getSession().update(entity);
            return entity;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 删除对象
     * @param entity 删除的对象
     * @throws BaseException 封装后的异常。
     */
    public  void deleteOne(T entity) throws Exception
    {
        try 
        {
            getSession().delete(entity);
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 保存或更新对象
     * @param entity 更新的对象
     * @return T 更新的对象
     * @throws BaseException 封装后的异常。
     */
    public T saveOrUpdateEntity(final T entity) throws Exception
    {
        try
        {
            getSession().saveOrUpdate(entity);
            return entity;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 保存集合中的所有对象
     * @param entities 保存的对象集合
     * @throws BaseException 封装后的异常。
     */
    public void saveAll(final Collection<T> entities) throws Exception
    {
        try
        {
            Session session = getSession();
            for (T entity : entities)
            {
                session.save(entity);
            }
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 更新集合中的所有对象
     * @param entities 保存的对象集合
     * @throws BaseException 封装后的异常。
     */
    public void updateAll(final Collection<T> entities) throws Exception
    {
        try
        {
            Session session = getSession();
            for (T entity : entities)
            {
                session.update(entity);
            }
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 删除entityclass实例，idName为该实例的主键名
     * @param entityClass 实例类
     * @param idName 实例类的主键名，该名为对象中定义的主键名，不是数据库表的主键字段
     * @param ids 具体的主键值
     * @return Object 删除的结果
     * @throws BaseException 封装后的异常。
     */
    public Object deleteEntityByID(Class entityClass, String idName, List<Serializable> ids) throws Exception
    {
        // 安全问题再处理
        String deleteHql = "delete from " + entityClass.getName() + " where "  + idName + " in(:ids)";        
        try
        {
            Query query = getSession().createQuery(deleteHql);
            query.setParameterList("ids", ids);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 删除entityclass实例，idName为该实例的主键名
     * @param entityClass 实例类
     * @param idName 实例类的主键名，该名为对象中定义的主键名，不是数据库表的主键字段
     * @param ids 具体的主键值
     * @return Object 删除结果
     * @throws BaseException 封装后的异常。
     */
    public Object deleteEntityByID(Class entityClass, String idName, Serializable ... ids) throws Exception
    {
        // 安全问题再处理
        String deleteHql = "delete from " + entityClass.getName() + " where "  + idName + " in(:ids)";        
        try
        {
            Query query = getSession().createQuery(deleteHql);
            query.setParameterList("ids", ids);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 执行HQL语句，该语句为更新或删除语句。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql hql语法的语句
     * @param params 该语句中的参数
     * @return Object 语句执行结果
     * @throws BaseException 封装后的异常。
     */
    public Object excute(final String hql, final Object... params) throws Exception
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setParams(query, params);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 执行定义的name语句，该语句为更新或删除语句。
     * 该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name 定义的命名语句
     * @param params 该语句中的参数
     * @return Object 语句执行结果
     * @throws BaseException 封装后的异常。
     */
    public Object excuteNameSql(final String name, final Object... params) throws Exception
    {
        try
        {
            Query query = getExcuteNameSql(name,  params);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 获取定义的name语句，该语句为更新或删除语句；同时进行相关的参数设置，
     * 外界在使用的是可以就直接使用query.executeUpdate()进行语句执行。
     * 该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name 定义的命名语句
     * @param params 该语句中的参数
     * @return Query query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getExcuteNameSql(final String name, final Object... params) throws Exception
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query = setParams(query, params);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
        
    /**
     * 执行HQL语句，该语句为更新或删除语句。该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param hql hql语法的语句，该语句中的参数是以名字命名的。
     * @param params 该语句中的参数，如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return Object 执行结果
     * @throws BaseException 封装后的异常。
     */
    public Object excuteByNameParams(final String hql, final Map<String, ?> params) throws Exception
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setMapParams(query, params);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 执行定义的name语句，该语句为更新或删除语句。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name 定义的命名语句，该语句中的参数是以名字命名的。
     * @param params 该语句中的参数，如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return Object 执行结果
     * @throws BaseException 封装后的异常。
     */
    public Object excuteNameSqlByNameParams(final String name, final Map<String, ?> params) throws BaseException
    {
        try
        {
            Query query = getExcuteNameSqlByNameParams(name, params);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name语句，该语句为更新或删除语句；同时进行相关的参数设置，外界在使用的是可以就直接使用query.executeUpdate()进行语句执行。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name 定义的命名语句，该语句中的参数是以名字命名的。
     * @param params 该语句中的参数，如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return Query query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getExcuteNameSqlByNameParams(final String name, final Map<String, ?> params) throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query = setMapParams(query, params);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 执行HQL语句，该语句为更新或删除语句。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql hql语法的语句，该语句的参数是以位置为基础定义的。
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @return Object 执行结果
     * @throws BaseException 封装后的异常。
     */
    public Object excuteByList(final String hql, final List<Object> params) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setListParams(query, params);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 执行定义的name语句，该语句为更新或删除语句。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name 定义的命名语句，该语句的参数是以位置为基础定义的。
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @return Object 执行结果
     * @throws BaseException 封装后的异常。
     */
    public Object excuteNameSqlByList(final String name, final List<Object> params) throws BaseException
    {
        try
        {
            Query query = getExcuteNameSqlByList(name, params);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name语句，该语句为更新或删除语句；同时进行相关的参数设置，外界在使用的是可以就直接使用query.executeUpdate()进行语句执行。
     * 该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name 定义的命名语句，该语句的参数是以位置为基础定义的。
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getExcuteNameSqlByList(final String name, final List<Object> params) throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query = setListParams(query, params);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 执行HQL语句，该语句为更新或删除语句。
     * @param hql hql语法的语句
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见excuteByNameParams(final String hql, final Map<String, ?> params)方法。
     * @return Object 执行结果
     * @throws BaseException 封装后的异常。
     */
    public Object excuteByNameParams(final String hql, final String paramName, final Object... params) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query.setParameterList(paramName, params);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 执行定义的name语句，该语句为更新或删除语句。
     * @param name 定义的命名语句
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见excuteByNameParams(final String hql, final Map<String, ?> params)方法。
     * @return Object 执行结果
     * @throws BaseException 封装后的异常。
     */
    public Object excuteNameSqlByNameParams(final String name, final String paramName, final Object... params) throws BaseException
    {
        try
        {
            Query query = getExcuteNameSqlByNameParams(name, paramName, params);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name语句，该语句为更新或删除语句；同时进行相关的参数设置，外界在使用的是可以就直接使用query.executeUpdate()进行语句执行。
     * @param name 定义的命名语句
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见excuteByNameParams(final String hql, final Map<String, ?> params)方法。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getExcuteNameSqlByNameParams(final String name, final String paramName, final Object... params) throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query.setParameterList(paramName, params);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 执行HQL语句，该语句为更新或删除语句。
     * @param hql hql语法的语句
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见excuteByNameParams(final String hql, final Map<String, ?> params)方法。
     * @return Object 执行结果
     * @throws BaseException 封装后的异常。
     */
    public Object excuteByNameParams(final String hql, final Collection<?> params, final String paramName) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query.setParameterList(paramName, params);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }  
    
    /**
     * 执行定义的name语句，该语句为更新或删除语句。
     * @param name 定义的命名语句
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见excuteByNameParams(final String hql, final Map<String, ?> params)方法。
     * @return Object 执行结果
     * @throws BaseException 封装后的异常。
     */
    public Object excuteNameSqlByNameParams(final String name, final Collection<?> params, final String paramName) throws BaseException
    {
        try
        {
            Query query = getExcuteNameSqlByNameParams(name, params, paramName);
            return query.executeUpdate();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }   
    
    /**
     * 获取定义的name语句，该语句为更新或删除语句。同时进行相关的参数设置，外界在使用的是可以就直接使用query.executeUpdate()进行语句执行。
     * @param name 定义的命名语句
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见excuteByNameParams(final String hql, final Map<String, ?> params)方法。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getExcuteNameSqlByNameParams(final String name, final Collection<?> params, final String paramName) throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query.setParameterList(paramName, params);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }   
    
    /**
     * 执行HQL语句，该语句查询语句。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql hql语法的语句
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param params 该语句中的参数
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    @Deprecated // 请使用findPage(String hql, int start, int count, Object... params)方法
    public List<T> excuteSelect(final String hql, int start, int count, final Object... params) throws Exception
    {
        return findPage(hql, start, count, params);
    }
        
    /**
     * 执行定义的name语句，该语句查询语句。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name 定义的命名语句
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param params 该语句中的参数
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    @Deprecated    // 请使用findPageByNameSql(String name, int start, int count, Object... params)方法
    public List<T> excuteNameSqlSelect(final String name, int start, int count, final Object... params) throws BaseException
    {
        return findPageByNameSql(name, start, count, params);
    }
    
    /**
     * 获取定义的name语句，该语句查询语句。同时进行相关的参数设置，外界在使用的是可以就直接使用query.list()进行语句执行。
     * 该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name 定义的命名语句
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param params 该语句中的参数
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    @Deprecated  // 请使用getFindPageByNameSql(String name, int start, int count, Object... params)方法
    public Query getExcuteNameSqlSelect(final String name, int start, int count, final Object... params) throws BaseException
    {
        return getFindPageByNameSql(name, start, count, params);
    }
    
    /**
     * 执行HQL语句，该语句查询语句。该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param hql hql语法的语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param params 该语句中的参数，如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    @Deprecated  // 请使用findPageNameParam(String hql, int start, int count, Map<String, ?> params)方法
    public List<T> excuteSelectByNameParams(final String hql, int start, int count, final Map<String, ?> params) throws Exception
    {
        return findPageNameParam(hql, start, count, params);
    }
        
    /**
     * 执行定义的name语句，该语句查询语句。该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name 定义的命名语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param params 该语句中的参数，如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    @Deprecated    // 请使用findPageByNameSqlNameParam(String name, int start, int count, Map<String, ?> params)方法
    public List<T> excuteNameSqlSelectByNameParams(final String name, int start, int count, final Map<String, ?> params) throws BaseException
    {
        return findPageByNameSqlNameParam(name, start, count, params);
    }
    
    /**
     * 获取定义的name语句，该语句查询语句。同时进行相关的参数设置，外界在使用的是可以就直接使用query.list()进行语句执行。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name 定义的命名语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param params 该语句中的参数，如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    @Deprecated // 请使用getFindPageByNameSqlNameParam(String name, int start, int count, Map<String, ?> params)方法替代
    public Query getExcuteNameSqlSelectByNameParams(final String name, int start, int count, final Map<String, ?> params) throws BaseException
    {
        return getFindPageByNameSqlNameParam(name, start, count, params);
    }
    
    /**
     * 执行HQL语句，该语句查询语句。
     * @param hql hql语法的语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * excuteSelectByNameParams(final String hql, int start, int count, final Map<String, ?> params)方法。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    @Deprecated // 请使用findPageNameParam(String hql, int start, int count, final Collection<?> params, final String paramName)方法
    public List<T> excuteSelectByNameParams(final String hql, int start, int count, final Collection<?> params, final String paramName) throws BaseException
    {
        return findPageNameParam(hql, start, count, params, paramName);
    }
            
    /**
     * 执行定义的name语句，该语句查询语句。
     * @param name 定义的命名语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * excuteSelectByNameParams(final String hql, int start, int count, final Map<String, ?> params)方法。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    @Deprecated // 请使用findPageByNameSqlNameParam(String name, int start, int count, final Collection<?> params, final String paramName)方法
    public List<T> excuteNameSqlSelectByNameParams(final String name, int start, int count, final Collection<?> params, final String paramName) throws BaseException
    {
        return findPageByNameSqlNameParam(name, start, count, params, paramName);
    }
    
    /**
     * 获取定义的name语句，该语句查询语句。同时进行相关的参数设置，外界在使用的是可以就直接使用query.list()进行语句执行。
     * @param name 定义的命名语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * excuteSelectByNameParams(final String hql, int start, int count, final Map<String, ?> params)方法。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    @Deprecated  // getFindPageByNameSqlNameParam(String name, int start, int count, final Collection<?> params, final String paramName)请使用方法
    public Query getExcuteNameSqlSelectByNameParams(final String name, int start, int count, final Collection<?> params, final String paramName) throws BaseException
    {
        return getFindPageByNameSqlNameParam(name, start, count, params, paramName);
    }
    
    /**
     * 执行HQL语句，该语句查询语句。
     * @param hql hql语法的语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * excuteSelectByNameParams(final String hql, int start, int count, final Map<String, ?> params)方法。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    @Deprecated // 请使用findPageNameParam(String hql, int start, int count, final String paramName, final Object ... params)方法
    public List<T> excuteSelectByNameParams(final String hql, int start, int count, final String paramName, final Object ... params) throws BaseException
    {
        return findPageNameParam(hql, start, count, paramName, params);
    }
           
    /**
     * 获取定义的name语句，该语句查询语句。同时进行相关的参数设置，外界在使用的是可以就直接使用query.list()进行语句执行。
     * @param name 定义的命名语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * excuteSelectByNameParams(final String hql, int start, int count, final Map<String, ?> params)方法。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    @Deprecated  // 请使用getFindPageByNameSqlNameParam(String name, int start, int count, final String paramName, final Object ... params)方法
    public Query getExcuteNameSqlSelectByNameParams(final String name, int start, int count, final String paramName, final Object ... params) throws BaseException
    {
        return getFindPageByNameSqlNameParam(name, start, count, paramName, params);
    }
    
    /**
     * 执行定义的name语句，该语句查询语句。
     * @param name 定义的命名语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * excuteSelectByNameParams(final String hql, int start, int count, final Map<String, ?> params)方法。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    @Deprecated  // 请使用findPageByNameSqlNameParam(String name, int start, int count, final String paramName, final Object ... params)方法
    public List<T> excuteNameSqlSelectByNameParams(final String name, int start, int count, final String paramName, final Object ... params) throws BaseException
    {
        return findPageByNameSqlNameParam(name, start, count, paramName, params);
    }
    
    /**
     * 执行HQL语句，该语句查询语句。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql hql语法的语句，该语句的参数是以位置为基础定义的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    @Deprecated  // 请使用findPageByList(String hql, int start, int count, List<Object> params)方法
    public List<T> excuteSelectByList(final String hql, int start, int count, final List<Object> params) throws BaseException
    {
        return findPageByList(hql, start, count, params);
    }
    
    /**
     * 执行定义的name语句，该语句查询语句。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name 定义的命名语句，该语句的参数是以位置为基础定义的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    @Deprecated  // 请使用findPageByNameSqlByListParam(String name, int start, int count, List<Object> params)方法
    public List<T> excuteNameSqlSelectByList(final String name, int start, int count, final List<Object> params) throws BaseException
    {
        return findPageByNameSqlByListParam(name, start, count, params);
    }
    
    /**
     * 执行定义的name语句，该语句查询语句。同时进行相关的参数设置，外界在使用的是可以就直接使用query.list()进行语句执行。
     * 该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name 定义的命名语句，该语句的参数是以位置为基础定义的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    @Deprecated    // 请使用getFindPageByNameSqlByListParam(String name, int start, int count, List<Object> params)方法
    public Query getExcuteNameSqlSelectByList(final String name, int start, int count, final List<Object> params) throws BaseException
    {
        return getFindPageByNameSqlByListParam(name, start, count, params);
    }
    
    /**
     * 获得某个实例的记录总数。
     * @param clazz 实例的class对象
     * @return long 对象数量
     * @throws BaseException 封装后的异常。
     */
    public long getCount(final Class clazz) throws BaseException
    {        
        try
        {
            Criteria criteria = getSession().createCriteria(clazz);
            criteria.setProjection(Projections.rowCount());
            Long ret = (Long)criteria.uniqueResult();
            if (ret == null)
            {
                return 0;
            }
            return ret;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过某个变量值查找该类的记录总数
     * @param entityClass 数据库对象类名（不包含包名）
     * @param propertyName 对象的属性定义名
     * @param value 变量值
     * @return long 记录总数
     * @throws BaseException 封装后的异常。
     */
    public long getCountByProperty(String entityClass, String propertyName, Object value) throws BaseException
    {
        try
        {
            String hql = "select count(*) from " + entityClass + " as model where model." + propertyName + " = :proV";
            Query query = getSession().createQuery(hql);
            query.setParameter("proV", value);
            Long ret = (Long)query.uniqueResult();
            if (ret == null)
            {
                return 0;
            }
            return ret;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }   
    
    
    /**
     * 通过hql获取数据表记录数量。该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param hql 语法的语句，该语句中的参数是以名字命名的。
     * @param params 如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return long 记录数量
     * @throws BaseException 封装后的异常。
     */
    public long getCountNameParam(String hql, Map<String, ?> params) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setMapParams(query, params);
            Long ret = (Long)query.uniqueResult();
            if (ret == null)
            {
                return 0;
            }
            return ret;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    /**
     * 通过hql获取数据表记录数量。
     * @param hql 语法的语句，该语句中的参数是以名字命名的。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * getCountNameParam(String hql, Map<String, ?> params)方法。
     * @return long 记录数量
     * @throws BaseException 封装后的异常。
     */
    public long getCountNameParam(String hql, final String paramName, final Object ... params) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query.setParameterList(paramName, params);
            Long ret = (Long)query.uniqueResult();
            if (ret == null)
            {
                return 0;
            }
            return ret;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取数据表记录数量。
     * @param hql 语法的语句，该语句中的参数是以名字命名的。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * getCountNameParam(String hql, Map<String, ?> params)方法。
     * @return long 记录数量
     * @throws BaseException 封装后的异常。
     */
    public long getCountNameParam(String hql, final Collection<?> params, final String paramName) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query.setParameterList(paramName, params);
            Long ret = (Long)query.uniqueResult();
            if (ret == null)
            {
                return 0;
            }
            return ret;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取数据表记录数量。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql hql语句
     * @param params 参数
     * @return long 记录数量
     * @throws BaseException 封装后的异常。
     */
    public long getCount(String hql, Object ... params) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setParams(query, params);
            Long ret = (Long)query.uniqueResult();
            if (ret == null)
            {
                return 0;
            }
            return ret;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取数据表记录数量。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql hql语句
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @return long 记录数量
     * @throws BaseException 封装后的异常。
     */
    public long getCountByList(String hql, List<Object> params) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setListParams(query, params);
            Long ret = (Long)query.uniqueResult();
            if (ret == null)
            {
                return 0;
            }
            return ret;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过主键查找该类的对象
     * @param entityClass 对象class
     * @param value 变量值
     * @return T 查询的对象
     * @throws BaseException 封装后的异常。
     */
    public T findByKey(Class<T> entityClass, Serializable value) throws BaseException
    {
        try
        {
            return (T)getSession().get(entityClass, value);
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
        
    }   
    
    /**
     * 通过主键查找该类的对象
     * @param entityClass 数据库对象类名（包含包名）
     * @param value 变量值
     * @return T 查询的对象
     * @throws BaseException 封装后的异常。
     */
    public T findByKey(String entityClass, Serializable value) throws BaseException
    {
        try
        {
            return (T)getSession().get(entityClass, value);
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
        
    /**
     * 通过某个变量值查找该类的所有对象
     * @param entityClass 对象class
     * @param propertyName 对象的属性定义名
     * @param value 变量值
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findByProperty(Class<T> entityClass, String propertyName, Object value) throws BaseException
    {
        return findByProperty(entityClass.getSimpleName(), propertyName, value);
    }   
    
    /**
     * 通过某个变量值查找该类的所有对象
     * @param entityClass 数据库对象类名（不包含包名）
     * @param propertyName 对象的属性定义名
     * @param value 变量值
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findByProperty(String entityClass, String propertyName, Object value) throws BaseException
    {
        try
        {
            String hql = "from " + entityClass + " as model where model." + propertyName + " = :proV";
            Query query = getSession().createQuery(hql);
            query.setParameter("proV", value);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过某个变量值查找该类的一定数量的对象
     * @param entityClass 对象class
     * @param propertyName 对象的属性定义名
     * @param value 变量值
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageByProperty(Class<T> entityClass, String propertyName, Object value, int start, int count) throws BaseException
    {
        return findPageByProperty(entityClass.getSimpleName(), propertyName, value, start, count);
    }   
    
    /**
     * 通过某个变量值查找该类的所有对象
     * @param entityClass 数据库对象类名（不包含包名）
     * @param propertyName 对象的属性定义名
     * @param value 变量值
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageByProperty(String entityClass, String propertyName, Object value, int start, int count) throws BaseException
    {
        try
        {
            String hql = "from " + entityClass + " as model where model." + propertyName + " = :proV";
            Query query = getSession().createQuery(hql);
            query.setParameter("proV", value);
            query = setPage(query, start, count);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
            
    /**
     * 通过某个变量值查找该类的一定数量的对象
     * @param entityClass 对象class
     * @param propertyName 对象的属性定义名
     * @param value 变量值
     * @param page 分页对象，该对象的值具体参见DataPage定义要求。
     * @return DataPage 分页结果值
     * @throws BaseException 封装后的异常。
     */
    public DataPage findPageByProperty(Class<T> entityClass, String propertyName, Object value, DataPage page) throws BaseException
    {
        return findPageByProperty(entityClass.getSimpleName(), propertyName, value, page);
    }   
    
    /**
     * 通过某个变量值查找该类的所有对象
     * @param entityClass 数据库对象类名（不包含包名）
     * @param propertyName 对象的属性定义名
     * @param value 变量值
     * @param page 分页对象，该对象的值具体参见DataPage定义要求。
     * @return DataPage 分页结果值
     * @throws BaseException 封装后的异常。
     */
    public DataPage findPageByProperty(String entityClass, String propertyName, Object value, DataPage page) throws BaseException
    {
        try
        {
            String hql = "from " + entityClass + " as model where model." + propertyName + " = :proV";
            Query query = getSession().createQuery(hql);
            query.setParameter("proV", value);
            query = setPage(query, page);
            
            page.setDatas(query.list());            
            page.setTotalRecords(getCountByProperty(entityClass, propertyName, value));
            return page;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过某个变量值查找该类的一定数量的对象
     * @param entityClass 对象class
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPage(Class<T> entityClass, int start, int count)  throws BaseException
    {
        return findPageByClass(entityClass.getSimpleName(), start, count);
    }
    
    /**
     * 通过hql获取指定数量的对象
     * @param entityClass 数据库对象类名（不包含包名）
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageByClass(String entityClass, int start, int count)  throws BaseException
    {
        try
        {
            String hql = "from " + entityClass;
            Query query = getSession().createQuery(hql);
            query = setPage(query, start, count);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过某个变量值查找该类的一定数量的对象
     * @param entityClass 对象class
     * @param page 分页对象，该对象的值具体参见DataPage定义要求。
     * @return DataPage 分页结果值
     * @throws BaseException 封装后的异常。
     */
    public DataPage findPage(Class<T> entityClass, DataPage page)  throws BaseException
    {
        return findPageByClass(entityClass.getSimpleName(), page);
    }
    
    /**
     * 通过hql获取指定数量的对象
     * @param entityClass 数据库对象类名（不包含包名）
     * @param page 分页对象，该对象的值具体参见DataPage定义要求。
     * @return DataPage 分页结果值
     * @throws BaseException 封装后的异常。
     */
    public DataPage findPageByClass(String entityClass, DataPage page)  throws BaseException
    {
        try
        {
            String hql = "from " + entityClass;
            Query query = getSession().createQuery(hql);
            query = setPage(query, page);
            
            page.setDatas(query.list());            
            page.setTotalRecords(getCount("select count(*) from " + entityClass));
            return page;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取指定数量的对象。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql 支持语句
     * @param params 参数
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPage(String hql, int start, int count, Object... params)  throws Exception
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setParams(query, params);
            query = setPage(query, start, count);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }    

    /**
     * 执行HQL语句，该语句分页查询语句。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql hql语法的语句
     * @param counthql 分页查询时候，获取对象总数量的查询语句，该语句的参数同具体查询的语句一直。由于hql不支持from中的子查询
     * 目前只能由外界把需要查询数量的语句有外界传入。
     * @param page 分页对象，该对象的值具体参见DataPage定义要求。
     * @param params 该语句中的参数
     * @return DataPage 分页结果值
     * @throws BaseException 封装后的异常。
     */
    public DataPage findPage(final String hql, String counthql, DataPage page, final Object... params) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setParams(query, params);
            query = setPage(query, page);
            page.setDatas(query.list());
            page.setTotalRecords(getCount(counthql, params));
            return page;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取指定数量的对象。该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param hql 语法的语句，该语句中的参数是以名字命名的。
     * @param params 如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageNameParam(String hql, int start, int count, Map<String, ?> params)  throws Exception
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setMapParams(query, params);
            query = setPage(query, start, count);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 执行HQL语句，该语句查询语句。该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param hql hql语法的语句，该语句中的参数是以名字命名的。
     * @param counthql 分页查询时候，获取对象总数量的查询语句，该语句的参数同具体查询的语句一直。由于hql不支持from中的子查询
     * 目前只能由外界把需要查询数量的语句有外界传入。
     * @param page 分页对象，该对象的值具体参见DataPage定义要求。
     * @param params 该语句中的参数，如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return DataPage 分页结果值
     * @throws BaseException 封装后的异常。
     */
    public DataPage findPageNameParam(final String hql, String counthql, DataPage page, final Map<String, ?> params) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setMapParams(query, params);
            query = setPage(query, page);
            page.setDatas(query.list());
            page.setTotalRecords(getCountNameParam(counthql, params));
            return page;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取指定数量的对象。
     * @param hql 语法的语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findPage(String hql, int start, int count, Map<String, ?> params)方法。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageNameParam(String hql, int start, int count, final Collection<?> params, final String paramName)  throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query.setParameterList(paramName, params);
            query = setPage(query, start, count);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 执行HQL语句，该语句查询语句。
     * @param hql hql语法的语句，该语句中的参数是以名字命名的。
     * @param counthql 分页查询时候，获取对象总数量的查询语句，该语句的参数同具体查询的语句一直。由于hql不支持from中的子查询
     * 目前只能由外界把需要查询数量的语句有外界传入。
     * @param page 分页对象，该对象的值具体参见DataPage定义要求。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * excuteSelectByNameParams(final String hql, int start, int count, final Map<String, ?> params)方法。
     * @return DataPage 分页结果值
     * @throws BaseException 封装后的异常。
     */
    public DataPage findPageNameParam(final String hql, String counthql, DataPage page, final Collection<?> params, final String paramName) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query.setParameterList(paramName, params);            
            query = setPage(query, page);
            page.setDatas(query.list());
            page.setTotalRecords(getCountNameParam(counthql, params, paramName));
            return page;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取指定数量的对象。
     * @param hql 语法的语句，该语句中的参数是以名字命名的。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findPage(String hql, int start, int count, Map<String, ?> params)方法。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageNameParam(String hql, int start, int count, final String paramName, final Object ... params)  throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query.setParameterList(paramName, params);
            query = setPage(query, start, count);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 执行HQL语句，该语句查询语句。
     * @param hql hql语法的语句，该语句中的参数是以名字命名的。
     * @param counthql 分页查询时候，获取对象总数量的查询语句，该语句的参数同具体查询的语句一直。由于hql不支持from中的子查询
     * 目前只能由外界把需要查询数量的语句有外界传入。
     * @param page 分页对象，该对象的值具体参见DataPage定义要求。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * excuteSelectByNameParams(final String hql, int start, int count, final Map<String, ?> params)方法。
     * @return DataPage 分页结果值
     * @throws BaseException 封装后的异常。
     */
    public DataPage findPageNameParam(final String hql, String counthql, DataPage page, final String paramName, final Object ... params) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query.setParameterList(paramName, params);
            query = setPage(query, page);
            page.setDatas(query.list());
            page.setTotalRecords(getCountNameParam(counthql, paramName, params));
            return page;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取指定数量的对象。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql 支持语句
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageByList(String hql, int start, int count, List<Object> params)  throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setListParams(query, params);
            query = setPage(query, start, count);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 执行HQL语句，该语句查询语句。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql hql语法的语句，该语句的参数是以位置为基础定义的。
     * @param counthql 分页查询时候，获取对象总数量的查询语句，该语句的参数同具体查询的语句一直。由于hql不支持from中的子查询
     * 目前只能由外界把需要查询数量的语句有外界传入。
     * @param page 分页对象，该对象的值具体参见DataPage定义要求。
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @return DataPage 分页结果值
     * @throws BaseException 封装后的异常。
     */
    public DataPage findPageByList(final String hql, String counthql, DataPage page, final List<Object> params) throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setListParams(query, params);
            query = setPage(query, page);
            page.setDatas(query.list());
            page.setTotalRecords(getCountByList(counthql, params));
            return page;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过定义的sql的name获取指定数量的对象。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name sql定义的名字，sql中定义的参数以位置形式表示
     * @param params 参数
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageByNameSql(String name, int start, int count, Object... params)  throws BaseException
    {
        try
        {
            Query query = getFindPageByNameSql(name, start, count, params);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name sql。同时进行相关的参数设置，外界在使用的是可以就直接使用query.list()进行语句执行。
     * 该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name sql定义的名字，sql中定义的参数以位置形式表示
     * @param params 参数
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getFindPageByNameSql(String name, int start, int count, Object... params)  throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query = setParams(query, params);
            query = setPage(query, start, count);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过定义的sql的name获取指定数量的对象。该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name sql定义的名字，sql中定义的参数以名字形式表示
     * @param params 参数如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageByNameSqlNameParam(String name, int start, int count, Map<String, ?> params)  throws BaseException
    {
        try
        {
            Query query = getFindPageByNameSqlNameParam(name, start, count, params);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name sql。同时进行相关的参数设置，外界在使用的是可以就直接使用query.list()进行语句执行。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name sql定义的名字，sql中定义的参数以名字形式表示
     * @param params 参数如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getFindPageByNameSqlNameParam(String name, int start, int count, Map<String, ?> params)  throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query = setMapParams(query, params);
            query = setPage(query, start, count);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过定义的sql的name获取指定数量的对象。
     * @param name sql定义的名字，sql中定义的参数以名字形式表示
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findPageByName(String name, int start, int count, Map<String, ?> params)方法。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageByNameSqlNameParam(String name, int start, int count, final Collection<?> params, final String paramName)  throws BaseException
    {
        try
        {
            Query query = getFindPageByNameSqlNameParam(name, start, count, params, paramName);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name sql。同时进行相关的参数设置，外界在使用的是可以就直接使用query.list()进行语句执行。
     * @param name sql定义的名字，sql中定义的参数以名字形式表示
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findPageByName(String name, int start, int count, Map<String, ?> params)方法。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getFindPageByNameSqlNameParam(String name, int start, int count, final Collection<?> params, final String paramName)  throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query.setParameterList(paramName, params);
            query = setPage(query, start, count);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过定义的sql的name获取指定数量的对象。
     * @param name sql定义的名字，sql中定义的参数以名字形式表示
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findPageByName(String name, int start, int count, Map<String, ?> params)方法。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageByNameSqlNameParam(String name, int start, int count, final String paramName, final Object ... params)  throws BaseException
    {
        try
        {
            Query query = getFindPageByNameSqlNameParam(name, start, count, paramName, params);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name sql。同时进行相关的参数设置，外界在使用的是可以就直接使用query.list()进行语句执行。
     * @param name sql定义的名字，sql中定义的参数以名字形式表示
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findPageByName(String name, int start, int count, Map<String, ?> params)方法。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getFindPageByNameSqlNameParam(String name, int start, int count, final String paramName, final Object ... params)  throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query.setParameterList(paramName, params);
            query = setPage(query, start, count);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过定义的sql的name获取指定数量的对象。该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name sql定义的名字，sql中定义的参数以位置形式表示
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return List<T> 结果值
     * @throws BaseException 封装后的异常。
     */
    public List<T> findPageByNameSqlByListParam(String name, int start, int count, List<Object> params)  throws BaseException
    {
        try
        {
            Query query = getFindPageByNameSqlByListParam(name, start, count, params);
            return query.list();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name sql。同时进行相关的参数设置，外界在使用的是可以就直接使用query.list()进行语句执行。
     * 该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name sql定义的名字，sql中定义的参数以位置形式表示
     * @param params 该语句中的参数，对于list中的参数，以list中的顺序为参数的顺序。
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getFindPageByNameSqlByListParam(String name, int start, int count, List<Object> params)  throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query = setListParams(query, params);
            query = setPage(query, start, count);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取一个对象实例。如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param hql 支持语句
     * @param params 参数
     * @return T 结果对象
     * @throws BaseException 封装后的异常。
     */
    public T findOneObjectBySql(final String hql, final Object ... params)  throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setParams(query, params);
            query.setMaxResults(1);
            return (T)query.uniqueResult();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过定义的name sql获取一个对象实例。如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name sql定义的名字
     * @param params 参数
     * @return T 结果对象
     * @throws BaseException 封装后的异常。
     */
    public T findOneObjectByNameSql(final String name, final Object ... params)  throws BaseException
    {
        try
        {
            Query query = getFindOneObjectByNameSql(name, params);
            return (T)query.uniqueResult();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name sql。同时进行相关的参数设置，外界在使用的是可以就直接使用query.uniqueResult()进行语句执行。
     * 如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数没有多值的集合参数，即是没有类似in (:value_list)。
     * @param name sql定义的名字
     * @param params 参数
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getFindOneObjectByNameSql(final String name, final Object ... params)  throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query = setParams(query, params);
            query.setMaxResults(1);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取一个对象实例。如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param hql 支持语句
     * @param params 参数如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return T 结果对象
     * @throws BaseException 封装后的异常。
     */
    public T findOneObjectBySqlNameParam(final String hql, final Map<String, ?> params)  throws Exception
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query = setMapParams(query, params);
            query.setMaxResults(1);
            return (T)query.uniqueResult();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw e;
        }
    }
    
    /**
     * 通过定义的name sql获取一个对象实例。如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name sql定义的名字
     * @param params 参数如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return T 结果对象
     * @throws BaseException 封装后的异常。
     */
    public T findOneObjectByNameSqlNameParam(final String name, final Map<String, ?> params)  throws BaseException
    {
        try
        {
            Query query = getFindOneObjectByNameSqlNameParam(name, params);
            return (T)query.uniqueResult();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name sql。同时进行相关的参数设置，外界在使用的是可以就直接使用query.uniqueResult进行语句执行。
     * 如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name sql定义的名字
     * @param params 参数如果map中的值为数组或Collection对象，则该参数会作为一个多值整体参数处理。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getFindOneObjectByNameSqlNameParam(final String name, final Map<String, ?> params)  throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query = setMapParams(query, params);
            query.setMaxResults(1);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取一个对象实例。如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param hql 支持语句
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findOneObjectBySql(final String hql, final Map<String, ?> params)方法。
     * @return T 结果对象
     * @throws BaseException 封装后的异常。
     */
    public T findOneObjectBySqlNameParam(final String hql, final Collection<?> params, final String paramName)  throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query.setParameterList(paramName, params);
            query.setMaxResults(1);
            return (T)query.uniqueResult();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过定义的name sql获取一个对象实例。如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name sql定义的名字
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findOneObjectBySql(final String hql, final Map<String, ?> params)方法。
     * @return T 结果对象
     * @throws BaseException 封装后的异常。
     */
    public T findOneObjectByNameSqlNameParam(final String name, final Collection<?> params, final String paramName)  throws BaseException
    {
        try
        {
            Query query = getFindOneObjectByNameSqlNameParam(name, params, paramName);
            return (T)query.uniqueResult();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name sql。同时进行相关的参数设置，外界在使用的是可以就直接使用query.uniqueResult()进行语句执行。
     * 如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name sql定义的名字
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findOneObjectBySql(final String hql, final Map<String, ?> params)方法。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getFindOneObjectByNameSqlNameParam(final String name, final Collection<?> params, final String paramName)  throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query.setParameterList(paramName, params);
            query.setMaxResults(1);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过hql获取一个对象实例。如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param hql 支持语句
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findOneObjectBySql(final String hql, final Map<String, ?> params)方法。
     * @return T 结果对象
     * @throws BaseException 封装后的异常。
     */
    public T findOneObjectBySqlNameParam(final String hql, final String paramName, final Object ... params)  throws BaseException
    {
        try
        {
            Query query = getSession().createQuery(hql);
            query.setParameterList(paramName, params);
            query.setMaxResults(1);
            return (T)query.uniqueResult();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 通过定义的name sql获取一个对象实例。如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name sql定义的名字
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findOneObjectBySql(final String hql, final Map<String, ?> params)方法。
     * @return T 结果对象
     * @throws BaseException 封装后的异常。
     */
    public T findOneObjectByNameSqlNameParam(final String name, final String paramName, final Object ... params)  throws BaseException
    {
        try
        {
            Query query = getFindOneObjectByNameSqlNameParam(name, paramName, params);
            return (T)query.uniqueResult();
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    /**
     * 获取定义的name sql。同时进行相关的参数设置，外界在使用的是可以就直接使用query.uniqueResult()进行语句执行。
     * 如果结果集中有多个对象，则只返回第一个对象。
     * 该语句中的参数可以有多值的集合参数，即是类似in (:value_list)。
     * @param name sql定义的名字
     * @param paramName 参数名。
     * @param params 该语句中的参数，该参数是作为一个查询参数的值进行处理的，如in (:value_list)，该方法
     * 只支持sql中有一个name参数的情况，如果需要多个参数的参见
     * findOneObjectBySql(final String hql, final Map<String, ?> params)方法。
     * @return Query 需要的query对象
     * @throws BaseException 封装后的异常。
     */
    public Query getFindOneObjectByNameSqlNameParam(final String name, final String paramName, final Object ... params)  throws BaseException
    {
        try
        {
            Query query = getSession().getNamedQuery(name);
            query.setParameterList(paramName, params);
            query.setMaxResults(1);
            return query;
        }
        catch(Exception e)
        {
            BaseException ee = handlException(e);
            throw ee;
        }
    }
    
    
    /**
     * 错误处理
     * @param e 原始异常
     * @param params 异常参数
     * @return BaseException 转换后的异常
     */
    @Deprecated
    protected BaseException handlException(Exception e, Object ... params)
    {
        if (e instanceof QueryParameterException)
        {
            BaseException ee = new  BaseException("E10060002", e.getMessage());
            ee.setStackTrace(e.getStackTrace());
            return ee;
        }
        if (e instanceof QuerySyntaxException)
        {
            BaseException ee = new  BaseException("E10060003", e.getMessage());
            ee.setStackTrace(e.getStackTrace());
            return ee;
        }
        if (e instanceof QueryTimeoutException)
        {
            BaseException ee = new  BaseException("E10060004", e.getMessage());
            ee.setStackTrace(e.getStackTrace());
            return ee;
        }
        if (e instanceof QueryException)
        {
            BaseException ee = new  BaseException("E10060005", e.getMessage());
            ee.setStackTrace(e.getStackTrace());
            return ee;
        }
        if (e instanceof HibernateException)
        {
            BaseException ee = new  BaseException("E10060006");
            ee.setStackTrace(e.getStackTrace());
            return ee;
        }
        BaseException ee = new  BaseException("E10060001", e.getMessage());
        ee.setStackTrace(e.getStackTrace());
        return ee;
    }
    
    /**
     * 设置位置参数
     * @param query query对象
     * @param params 参数
     * @return Query 设置好参数的query对象
     */
    protected Query setParams(Query query, Object ... params)
    {
        if (params != null)
        {
            int size = params.length;
            for (int j = 0; j < size; j++) 
            {
                query.setParameter(j, params[j]);
            }
        }
        return query;
    }
    
    /**
     * 设置位置参数
     * @param query query对象
     * @param params 以list中的顺序为参数的顺序。
     * @return Query 设置好参数的query对象
     */
    protected Query setListParams(Query query, List<Object> params)
    {
        if (params != null && params.size() > 0)
        {
            int size = params.size();
            for (int j = 0; j < size; j++) 
            {
                query.setParameter(j, params.get(j));
            }
        }
        return query;
    }
    
    /**
     * 设置命名参数
     * @param query 原始query对象
     * @param params 名字参数
     * @return 设置好参数的query对象
     */
    protected Query setMapParams(Query query, Map<String, ?> params)
    {
        if (params != null && params.size() > 0)
        {
            Object value;
            for (Map.Entry<String, ?> entry : params.entrySet()) 
            {
                value = entry.getValue();
                if (value instanceof Collection)
                {
                    query.setParameterList(entry.getKey(), (Collection<?>)value);
                }
                else if (value instanceof Object[])
                {
                    query.setParameterList(entry.getKey(), (Object[])value);
                }
                else
                {
                    query.setParameter(entry.getKey(), value);
                }
            }
        }
        return query;
    }
    
    /**
     * 设置分页
     * @param query 原始query
     * @param start 获取对象的开始位置，如果小于等于0，则表示从第一个记录开始获取。
     * @param count 获取对象的数量，如果小于等于0，则表示从start位置开始获取后续所有对象。
     * @return Query 设置好参数的query对象
     */
    protected Query setPage(Query query, int start, int count)
    {
        if (start > 0)
        {
            query.setFirstResult(start);
        }
        if (count > 0)
        {
            query.setMaxResults(count);
        }
        return query;
    }
    
    /**
     * 设置分页
     * @param query 原始query
     * @param page 需要分页的对象。
     * @return Query 设置好参数的query对象
     */
    protected Query setPage(Query query, DataPage page)
    {
        int count = page.getPageSize();
        if (count > 0)
        {
            query.setMaxResults(count);
        }
        else    // 不分页直接返回。
        {
            return query;
        }
        // 因为page.getCurrentPage()从1开始，所有需要减1
        int start = (page.getCurrentPage() - 1) * count;
        if (start > 0)
        {
            query.setFirstResult(start);
        }        
        return query;
    }
    
    /**
     * sql查询，map形式返回
     * @param sql
     * @param start
     * @param count
     * @param params
     * @return
     */
    public List<Map<String, Object>> findPageBySql(String sql, int start, int count, final Map<String, ?> params) {
    	
    	Query query = getSession().createSQLQuery(sql);
		query = setMapParams(query, params);
		query = setPage(query, start, count);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return (List<Map<String, Object>>)query.list();
    }
    
    /**
     * sql查询总条数
     * @param sql
     * @param params
     * @return
     */
    public BigInteger getCountBySql(String sql, final Map<String, ?> params) {
    	
    	Query query = getSession().createSQLQuery(sql);
		query = setMapParams(query, params);
		return query.uniqueResult() == null ? BigInteger.ZERO : (BigInteger)query.uniqueResult();
    }
}
