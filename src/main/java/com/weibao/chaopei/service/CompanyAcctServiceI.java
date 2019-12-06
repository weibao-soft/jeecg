package com.weibao.chaopei.service;

import com.weibao.chaopei.entity.WithdrawOrderEntity;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.CommonService;

import com.weibao.chaopei.entity.CompanyRewardedDetailEntity;
import com.weibao.chaopei.entity.CompanyUnrewardedDetailEntity;

public interface CompanyAcctServiceI extends CommonService {
	/**
	 *  查询分润明细
	 * @return
	 */
	public DataGrid getReceiveDetailList(CompanyRewardedDetailEntity rewardDetail, DataGrid dataGrid);
	/**
	 *  查询未分润明细
	 * @return
	 */
	public DataGrid getUnreceiveDetailList(CompanyUnrewardedDetailEntity unrewardDetail, DataGrid dataGrid);

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
	 *  公司账户提现
	 * @return
	 */
	public boolean withdrawCompany(String params);

}
