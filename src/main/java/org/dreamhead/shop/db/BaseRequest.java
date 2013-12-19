package org.dreamhead.shop.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.dreamhead.shop.entity.Category;
import org.dreamhead.shop.entity.Shipment;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class BaseRequest {

	private SimpleDateFormat dateFormaterDATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private BaseManager bm;
	
	public BaseRequest() {
		 bm = BQD.M;
	}
	
	public List<Category> getCategories() {
		return bm.getListEntity(Category.class);
	}

	public List<Shipment> getShipments(int CategoryId) {
		Category category = bm.getEntity(Category.class, CategoryId);
		return category.getShipments();
	}

	public Category getCategory(int CategoryId) {
		Category category = bm.getEntity(Category.class, CategoryId);
		return category;
	}
	
//	public static List<QFBaLocation> getBALocations(int qfformTemplate, int locationVariant) {
//		try {
//			return BQD.M.getListEntity(QFBaLocation.class,
//					new ParamHQL("qfformTemplate", qfformTemplate),
//					new ParamHQL("locationVariant", locationVariant)
//			);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ArrayList<QFBaLocation>();
//		}
//	}
//
//	public List<QFDictionary> findQFDictionary(String name) {
//		Criteria criteria = BQD.M.getCurrentSession().createCriteria(QFDictionary.class);
//		criteria.add(Restrictions.like("nameDictionary", "%" + name + "%"));
//		List<QFDictionary> dictionaries = (List<QFDictionary>) criteria.list();
//		return dictionaries;
//	}
//
//	public List<QFAncillaryData> findQFAncillaryData(String dataValue,
//			int dictionaryId) {
//		Criteria criteria = BQD.M.getCurrentSession().createCriteria(QFDictionary.class);
//		criteria.add(Restrictions.like("dataValue", "%" + dataValue + "%"));
//		criteria.add(Restrictions.like("qfdictionary", new QFDictionary(dictionaryId)));
//		List<QFAncillaryData> dictionaries = (List<QFAncillaryData>) criteria.list();
//		return dictionaries;
//	}


}
