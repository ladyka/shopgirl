package org.dreamhead.shop.db;

public class ParamHQL {
	
	private String param;
	private Object value;
	
	public ParamHQL(String param,Object value) {
		this.param = param;
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	
	public StringBuffer toStringBuffer() {
		StringBuffer query = new StringBuffer(" entity.");
    	query.append(param);
    	query.append(" = '");
    	query.append(String.valueOf(value));
    	query.append("' ");
    	return query;
	}

}
