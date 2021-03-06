package com.klwork.business.domain.service;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weibo4j.http.AccessToken;
import weibo4j.model.User;

import com.klwork.common.SystemConstants;
import com.klwork.common.dao.QueryParameter;
import com.klwork.common.dto.vo.ViewPage;
import com.klwork.common.utils.ReflectionUtils;
import com.klwork.common.utils.StringDateUtil;
import com.klwork.business.domain.model.DictDef;
import com.klwork.business.domain.model.SocialUserAccount;
import com.klwork.business.domain.model.SocialUserAccountInfo;
import com.klwork.business.domain.model.SocialUserAccountQuery;
import com.klwork.business.domain.repository.SocialUserAccountRepository;

/**
 * 
 * @version 1.0
 * @created ${plugin.now}
 * @author ww
 */

@Service
public class SocialUserAccountService {

	@Autowired
	IdentityService identityService;

	@Autowired
	UserService userService;

	@Autowired
	private SocialUserAccountRepository rep;

	@Autowired
	public SocialUserAccountInfoService socialUserAccountInfoService;

	public void createSocialUserAccount(SocialUserAccount socialUserAccount) {
		SocialUserAccount s = queryExist(socialUserAccount);
		if(s == null){
			socialUserAccount.setId(rep.getNextId());
			Date lastUpdate = StringDateUtil.now();
			socialUserAccount.setLastUpdate(lastUpdate);
			rep.insert(socialUserAccount);
		}else {
			socialUserAccount.setId(s.getId());
			updateSocialUserAccount(socialUserAccount);
		}
	}

	/*public void deleteSocialUserAccount(SocialUserAccount socialUserAccount) {
	}*/

	private SocialUserAccount queryExist(SocialUserAccount socialUserAccount) {
		SocialUserAccountQuery sQuery = new SocialUserAccountQuery();
		sQuery.setOwnUser(socialUserAccount.getOwnUser()).setWeiboUid(socialUserAccount.getWeiboUid()).setType(socialUserAccount.getType());
		List<SocialUserAccount> list = rep.findSocialUserAccountByQueryCriteria(sQuery , null);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public int updateSocialUserAccount(SocialUserAccount socialUserAccount) {
		Date lastUpdate = StringDateUtil.now();
		socialUserAccount.setLastUpdate(lastUpdate);
		return rep.update(socialUserAccount);
	}

	public List<SocialUserAccount> findSocialUserAccountByQueryCriteria(
			SocialUserAccountQuery query, ViewPage<SocialUserAccount> page) {
		return rep.findSocialUserAccountByQueryCriteria(query, page);
	}

	public SocialUserAccount findSocialUserAccountById(String id) {
		return rep.find(id);
	}

	public int count(SocialUserAccountQuery query) {
		return rep.findSocialUserAccountCountByQueryCriteria(query);
	}
	
	

	public SocialUserAccount findSocialUserByType(String userId, int type) {
		List<SocialUserAccount> list = findSocialUserListByType(userId, type);
		return getOnlyOne(list);
	}
	
	/**
	 * 查询用户某个类型的第三方帐号
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<SocialUserAccount> findSocialUserListByType(String userId,
			int type) {
		SocialUserAccountQuery query = new SocialUserAccountQuery();
		query.setOwnUser(userId).setType(type);
		List<SocialUserAccount> list = findSocialUserAccountByQueryCriteria(query, null);
		return list;
	}

	public <T> T getOnlyOne(List<T> list) {
		if (list !=null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 查询用户下所有帐号
	 * @param userId
	 * @return
	 */
	public List<SocialUserAccount> queryUserAccountByUserId(String userId) {
		SocialUserAccountQuery sQuery = new SocialUserAccountQuery();
		sQuery.setOwnUser(userId);
		List<SocialUserAccount> sList = findSocialUserAccountByQueryCriteria(sQuery, null);
		return sList;
	}
}