package com.klwork.business.domain.repository;
import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.klwork.common.dao.QueryParameter;
import com.klwork.common.domain.repository.MbDomainRepositoryImp;
import com.klwork.common.dto.vo.ViewPage;
import com.klwork.business.domain.model.SocialUserAccount;

/**
 * 
 * @version 1.0
 * @created ${plugin.now}
 * @author ww
 */
 
@Repository(value = "socialUserAccountRepository")
public class SocialUserAccountRepository extends
		MbDomainRepositoryImp<SocialUserAccount, Serializable> {

	@SuppressWarnings("unchecked")
	public <T extends QueryParameter> List<SocialUserAccount> findSocialUserAccountByQueryCriteria(T query,
			ViewPage page) {
		List<SocialUserAccount> list = getDao().selectList(
				"selectSocialUserAccountByQueryCriteria", query, page);
		if (page != null) {
			int count = findSocialUserAccountCountByQueryCriteria(query);
			page.setTotalSize(count);
			// 总数
			page.setPageDataList(list);
		}
		return list;
	}

	/**
	 * 查询总数
	 * 
	 * @param query
	 * @return
	 */
	public Integer findSocialUserAccountCountByQueryCriteria(Object query) {
		return (Integer) getDao().selectOne(
				"selectSocialUserAccountCountByQueryCriteria", query);
	}
}
