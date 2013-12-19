package org.dreamhead.shop.db;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * AppUser: user
 * Date: 5.8.13
 * Time: 13.22
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("baseManager")
public class BaseManager {

    @Autowired
    private SessionFactory sessionFactory;

    private Log logger = LogFactory.getLog(getClass());

    public Object save(Object entity) {
        sessionFactory.getCurrentSession().save(entity);
        return entity;
    }

    public Object saveOrUpdate(Object entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    //package local.
    Query createQuery(String str) {
        logger.warn("SQL REQUEST : " + str);
        return sessionFactory.getCurrentSession().createQuery(str);
    }

    Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    public boolean delete(Object object) {
    	try {
    		sessionFactory.getCurrentSession().delete(object);
    		return true;
    	} catch (Exception ex) {
    		return false;
    	}
        
    }

    @SuppressWarnings("unchecked")
    public <T> T getEntity(Class<T> entityClass, int id) {
        return (T) getCurrentSession().get(entityClass, id);
    }
    
    @SuppressWarnings("unchecked")
    public <T> List<T> getListEntity(Class<T> entityClass) {
        String query = "SELECT entity FROM " + entityClass.getCanonicalName() + " entity";
        return (List<T>) createQuery(query).list();
    }
    
    //Вывод всего, что всязанно с данным пользователем
    @SuppressWarnings("unchecked")
    public <T> List<T> getListEntityUser(Class<T> entityClass, int user) {
        String query = "SELECT entity FROM " + entityClass.getCanonicalName() + " entity WHERE entity.user = " + user;
        return (List<T>) createQuery(query).list();
    }
    
    
    @Deprecated
    @SuppressWarnings("unchecked")
    public <T> List<T> getListEntity(Class<T> entityClass,String param1,Object value1) {
    	return getListEntity(entityClass,
    			new ParamHQL(param1, value1)
    			);
    }
    
    @Deprecated
    @SuppressWarnings("unchecked")
    public <T> List<T> getListEntity(Class<T> entityClass,String param1,Object value1,String param2,Object value2) {
    	return getListEntity(entityClass,
    			new ParamHQL(param1, value1),
    			new ParamHQL(param2, value2)
    			);
    }
    

    @SuppressWarnings("unchecked")
    public <T> List<T> getListEntity(Class<T> entityClass,ParamHQL...params) {
    	StringBuffer query = new StringBuffer("SELECT entity FROM ");
    	query.append(entityClass.getCanonicalName());
    	query.append(" entity ");
    	
    	int l = params.length;
    	for (int i = 0; i<l;i++) {
    		if (i==0) {
    			query.append(" WHERE ");
    		} else {
    			query.append(" AND ");
    		}
    		query.append(params[i].toStringBuffer());
		}
    	String myQuery = query.toString(); 
        return (List<T>) createQuery(myQuery).list();
    }

}


