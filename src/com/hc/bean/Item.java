package com.hc.bean;

public class Item {
	private String propertyName;// 实体类成员变量名
	private String javaType;// 实体类成员变量类型

	private String fieldName;// 表字段名
	private String comment;
	private String sqlType;// 表字段类型

	private boolean pk;// 是否是主键

	public Item() {
	}

	public Item(String javaType, String propertyName) {
		this.propertyName = propertyName;
		this.javaType = javaType;
	}

	

	public Item(String javaType, String propertyName, String comment) {
		super();
		this.javaType = javaType;
		this.propertyName = propertyName;
		this.comment = comment;
	}

	public Item(String propertyName, String comment, String javaType, String fieldName, String sqlType) {
		this.propertyName = propertyName;
		this.comment = comment;
		this.javaType = javaType;
		this.fieldName = fieldName;
		this.sqlType = sqlType;
	}

	public Item(String propertyName, String javaType, String fieldName, String sqlType, boolean pk) {
		this.propertyName = propertyName;
		this.javaType = javaType;
		this.fieldName = fieldName;
		this.sqlType = sqlType;
		this.pk = pk;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public String toString() {
		return "Item{" + "propertyName='" + propertyName + '\'' + ", javaType='" + javaType + '\'' + ", fieldName='"
				+ fieldName + '\'' + ", sqlType='" + sqlType + '\'' + ", pk=" + pk + '}';
	}

}
