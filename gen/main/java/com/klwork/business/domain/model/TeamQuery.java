package com.klwork.business.domain.model;

import com.klwork.common.dao.QueryParameter;

/**
 * 
 * @version 1.0
 * @created ${plugin.now}
 * @author ww
 * 
 */
public class TeamQuery extends QueryParameter{
 	private static final long serialVersionUID = 1L;
 	
 	public TeamQuery() {
 	
 	}
 	
 	/**
	 *  
	 */
	private String ownUser;
	
	/**
	 *  
	 */
	private String type;
	

	/**
	 * 
	 *
	 * @param ownUser
	 */
	public TeamQuery setOwnUser(String ownUser){
		this.ownUser = ownUser;
		return this;
	}
	
	/**
	 * 
	 *
	 * @return
	 */	
	public String getOwnUser(){
		return ownUser;
	}
	
	/**
	 * 
	 *
	 * @param type
	 */
	public TeamQuery setType(String type){
		this.type = type;
		return this;
	}
	
	/**
	 * 
	 *
	 * @return
	 */	
	public String getType(){
		return type;
	}
	

}