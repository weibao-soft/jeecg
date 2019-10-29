	INSERT INTO wb_policy_holder(id, holder_nature, holder_org_code, holder_comp_name, holder_comp_nature, industry_type, taxpayer_no, 
	receiver_mobile, comp_name, comp_address, comp_phone, deposit_bank, bank_account, last_update_time)
	VALUES
	(:holder.id, :holder.holderNature, :holder.holderOrgCode, :holder.holderCompName, :holder.holderCompNature, :holder.industryType,
	:holder.taxpayerNo, :holder.receiverMobile, :holder.compName, :holder.compAddress, :holder.compPhone,
	:holder.depositBank, :holder.bankAccount, :holder.lastUpdateTime
	)
	ON DUPLICATE KEY UPDATE
	holder_nature = VALUES(holder_nature),
	holder_comp_name = VALUES(holder_comp_name),
	holder_comp_nature = VALUES(holder_comp_nature),
	industry_type = VALUES(industry_type),
	taxpayer_no = VALUES(taxpayer_no),
	receiver_mobile = VALUES(receiver_mobile),
	comp_name = VALUES(comp_name),
	comp_address = VALUES(comp_address),
	comp_phone = VALUES(comp_phone),
	deposit_bank = VALUES(deposit_bank),
	bank_account = VALUES(bank_account),
	last_update_time = VALUES(last_update_time)