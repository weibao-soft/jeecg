	INSERT INTO wb_policy_holder(id, holder_nature, org_code, comp_name, comp_nature, industry_type, taxpayer_no, 
	receiver_mobile, comp_name2, comp_address, comp_phone, deposit_bank, bank_account, update_time)
	VALUES
	(:holder.id, :holder.holderNature, :holder.orgCode, :holder.compName, :holder.compNature, :holder.industryType,
	:holder.taxpayerNo, :holder.receiverMobile, :holder.compName2, :holder.compAddress, :holder.compPhone,
	:holder.depositBank, :holder.bankAccount, :holder.updateTime
	)
	ON DUPLICATE KEY UPDATE
	holder_nature = VALUES(holder_nature),
	comp_name = VALUES(comp_name),
	comp_nature = VALUES(comp_nature),
	industry_type = VALUES(industry_type),
	taxpayer_no = VALUES(taxpayer_no),
	receiver_mobile = VALUES(receiver_mobile),
	comp_name2 = VALUES(comp_name2),
	comp_address = VALUES(comp_address),
	comp_phone = VALUES(comp_phone),
	deposit_bank = VALUES(deposit_bank),
	bank_account = VALUES(bank_account),
	update_time = VALUES(update_time)