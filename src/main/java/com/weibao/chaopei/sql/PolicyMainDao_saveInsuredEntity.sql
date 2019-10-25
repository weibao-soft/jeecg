	INSERT INTO wb_insured_info(id, org_code, comp_name)
	VALUES
	(:insured.id, :insured.orgCode, :insured.compName
	)
	ON DUPLICATE KEY UPDATE
	comp_name = VALUES(comp_name)