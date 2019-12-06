package com.weibao.chaopei.service;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.PersonalRewardedDetailEntity;
import com.weibao.chaopei.entity.PersonalUnrewardedDetailEntity;
import com.weibao.chaopei.entity.WithdrawOrderEntity;

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
	/**
	 *  查询取现订单记录
	 * @return
	 */
	public DataGrid getWithdrawOrderList(WithdrawOrderEntity orderEntity, DataGrid dataGrid);
	/**
	 *  查询取现订单的明细
	 * @return
	 */
	public DataGrid getWithdrawDetailList(String orderId, DataGrid dataGrid);
	/**
	 *  个人账户提现
	 * @return
	 */
	public boolean withdrawPerson(String params);
}
