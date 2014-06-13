package org.dreamhead.shop.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.dreamhead.shop.entity.AppUser;
import org.dreamhead.shop.entity.Category;
import org.dreamhead.shop.entity.Shipment;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BaseRequest {

	private SimpleDateFormat dateFormaterDATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	BaseManager baseKernel;
	
	
	public List<Category> getCategories() {
		return baseKernel.getListEntity(Category.class);
	}

	public List<Shipment> getShipments(int CategoryId) {
		Category category = baseKernel.getEntity(Category.class, CategoryId);
		return category.getShipments();
	}

	public Category getCategory(int CategoryId) {
		Category category = baseKernel.getEntity(Category.class, CategoryId);
		return category;
	}

	@SuppressWarnings("unchecked")
	public List<Shipment> searchShipments(String searchquery) {
		Criteria criteria = baseKernel.getCurrentSession().createCriteria(Shipment.class);
		criteria.add(Restrictions.like("name", "%" + searchquery + "%"));
		List<Shipment> dictionaries = null;
		try {
			dictionaries = (List<Shipment>) criteria.list(); 
		} catch (Exception ex) {
			dictionaries = new ArrayList<Shipment>();
		}
		
		
		Criteria criteria1 = baseKernel.getCurrentSession().createCriteria(Shipment.class);
		criteria1.add(Restrictions.like("description", "%" + searchquery + "%"));
		try {
			dictionaries.addAll((List<Shipment>) criteria.list());
		} catch (Exception ex) {
			
		}
		
		return dictionaries;
	}
	
	public AppUser getAppUserFromEmail(String email) {
		try {
			Criteria criteria = baseKernel.getCriteria(AppUser.class);
			criteria.add(Restrictions.like("email",email));
			return (AppUser) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return baseKernel.getEntity(AppUser.class, 3);
		}
	}

    public <T> T getEntity(Class<T> entityClass, int id) {
        return baseKernel.getEntity(entityClass, id);
    }
    
    
	public Object saveOrUpdate(Object object) {
		return baseKernel.saveOrUpdate(object);
	}
	
	public Object save(Object object) {
		return baseKernel.save(object);
	}

}
