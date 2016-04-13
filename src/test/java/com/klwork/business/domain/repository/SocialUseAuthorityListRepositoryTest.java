package com.klwork.business.domain.repository;
import java.io.Serializable;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.klwork.test.base.BaseTxWebTests;
import com.klwork.common.dao.QueryParameter;
import com.klwork.common.dto.vo.ViewPage;
import com.klwork.business.domain.model.SocialUseAuthorityList;
import com.klwork.business.domain.repository.SocialUseAuthorityListRepository;

/**
 * 
 * @version 1.0
 * @created ${plugin.now}
 * @author ww
 */
 
public class SocialUseAuthorityListRepositoryTest extends BaseTxWebTests {
	@Autowired
	private SocialUseAuthorityListRepository rep;

	@Test
	public void testCrud() {
		
	}

}
