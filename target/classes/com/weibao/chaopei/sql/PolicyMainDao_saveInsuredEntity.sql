	INSERT INTO wb_insured_info(id, insured_org_code, insured_comp_name)
	VALUES
	(:insured.id, :insured.insuredOrgCode, :insured.insuredCompName
	)
	ON DUPLICATE KEY UPDATE
	insured_comp_name = VALUES(insured_comp_name)