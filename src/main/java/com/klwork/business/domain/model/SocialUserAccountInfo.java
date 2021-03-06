package com.klwork.business.domain.model;


import java.io.Serializable;

/**
 * 
 * @version 1.0
 * @created ${plugin.now}
 * @author ww
 * 
 */
public class SocialUserAccountInfo implements Serializable 
 {
 	private static final long serialVersionUID = 1L;
 	public SocialUserAccountInfo() {
 	}
 

	/**
	 *  
	 */
	private String id;
	/**
	 *  
	 */
	private String userId;
	/**
	 *  
	 */
	private String accountId;
	
	/**
	 *  
	 */
	private String entityId;
	
	/**
	 *  
	 */
	private String type;
	/**
	 *  
	 */
	private String key;
	/**
	 *  
	 */
	private Object valueType;
	/**
	 *  
	 */
	private String value;
	/**
	 *  
	 */
	private Integer valueInt;
	/**
	 *  
	 */
	private String valueString;
	/**
	 *  
	 */
	private java.util.Date valueDate;
	/**
	 *  
	 */
	private Double valueDouble;
	/**
	 *  
	 */
	private java.util.Date lastUpdate;

	/**
	 * 
	 *
	 * @param id
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public String getId(){
		return id;
	}
	/**
	 * 
	 *
	 * @param userId
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public String getUserId(){
		return userId;
	}
	/**
	 * 
	 *
	 * @param accountId
	 */
	public void setAccountId(String accountId){
		this.accountId = accountId;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public String getAccountId(){
		return accountId;
	}
	/**
	 * 
	 *
	 * @param type
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public String getType(){
		return type;
	}
	/**
	 * 
	 *
	 * @param key
	 */
	public void setKey(String key){
		this.key = key;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public String getKey(){
		return key;
	}
	/**
	 * 
	 *
	 * @param valueType
	 */
	public void setValueType(Object valueType){
		this.valueType = valueType;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public Object getValueType(){
		return valueType;
	}
	/**
	 * 
	 *
	 * @param value
	 */
	public void setValue(String value){
		this.value = value;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public String getValue(){
		return value;
	}
	/**
	 * 
	 *
	 * @param valueInt
	 */
	public void setValueInt(Integer valueInt){
		this.valueInt = valueInt;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public Integer getValueInt(){
		return valueInt;
	}
	/**
	 * 
	 *
	 * @param valueString
	 */
	public void setValueString(String valueString){
		this.valueString = valueString;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public String getValueString(){
		return valueString;
	}
	/**
	 * 
	 *
	 * @param valueDate
	 */
	public void setValueDate(java.util.Date valueDate){
		this.valueDate = valueDate;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public java.util.Date getValueDate(){
		return valueDate;
	}
	/**
	 * 
	 *
	 * @param valueDouble
	 */
	public void setValueDouble(Double valueDouble){
		this.valueDouble = valueDouble;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public Double getValueDouble(){
		return valueDouble;
	}
	/**
	 * 
	 *
	 * @param lastUpdate
	 */
	public void setLastUpdate(java.util.Date lastUpdate){
		this.lastUpdate = lastUpdate;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public java.util.Date getLastUpdate(){
		return lastUpdate;
	}
	
	/**
	 * 
	 *
	 * @param entityId
	 */
	public void setEntityId(String entityId){
		this.entityId = entityId;
	}
	/**
	 * 
	 *
	 * @return
	 */	
	public String getEntityId(){
		return entityId;
	}
}