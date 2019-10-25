	INSERT INTO wb_policy_holder(id, recipients, recipients_tel, reci_address, user_id)
	VALUES
	(:receiver.id, :receiver.recipients, :receiver.recipientsTel, :receiver.reciAddress, :receiver.userId
	)
	ON DUPLICATE KEY UPDATE
	recipients = VALUES(recipients),
	reci_address = VALUES(reci_address),
	user_id = VALUES(user_id)