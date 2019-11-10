select p.id,p.plan_id,p.plate_no,p.frame_no,p.engine_no,p.start_date,p.end_date,p.`status`,p.contact_name,p.policy_mobile,
p.invoice_type,p.create_time,p.user_id,p.holder_nature,p.holder_org_code,p.holder_comp_name,p.holder_comp_nature,
p.industry_type,p.taxpayer_no,p.receiver_mobile,p.comp_name,p.comp_address,p.comp_phone,p.deposit_bank,p.bank_account,
p.insured_comp_name,p.insured_org_code,p.recipients,p.recipients_tel,p.reci_address,p.permium,p.pay_status,p.reward_status,
r.draft_id,d.truck_nums,d.save_time 
from wb_insurance_policy p,wb_draft_relation r,wb_insurance_draft d
where p.id=r.policy_id and d.id=r.draft_id 
and d.id=:draftId
