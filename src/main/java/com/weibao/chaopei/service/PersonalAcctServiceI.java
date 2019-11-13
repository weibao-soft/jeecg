package com.weibao.chaopei.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.PersonalRewardedDetailEntity;
import com.weibao.chaopei.entity.PersonalUnrewardedDetailEntity;

public interface PersonalAcctServiceI extends CommonService {
	/**
	 *  查询分润明细
	 * @return
	 */
	public DataGrid getReceiveDetailList(PersonalRewardedDetailEntity rewardDetail, DataGrid dataGrid);
	/**
	 *  查询未分润明细
	 * @return
	 */
	public DataGrid getUnreceiveDetailList(PersonalUnrewardedDetailEntity unrewardDetail, DataGrid dataGrid);
}
