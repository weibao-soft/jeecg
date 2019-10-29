select p.id,p.plan_id,p.plate_no,p.frame_no,p.engine_no,p.start_date,p.end_date,p.`status`,p.contact_name,p.policy_mobile,
p.invoice_type,p.create_time,p.user_id,h.holder_nature,h.holder_org_code,h.holder_comp_name,h.holder_comp_nature,
h.industry_type,h.taxpayer_no,h.receiver_mobile,h.comp_name,h.comp_address,h.comp_phone,h.deposit_bank,h.bank_account,
i.insured_comp_name,i.insured_org_code,r.draft_id,d.truck_nums,d.`status`,d.save_time 
from wb_insurance_policy p,wb_draft_relation r,wb_insurance_draft d,wb_policy_holder h,wb_insured_info i
where p.id=r.policy_id and d.id=r.draft_id and p.holder_id=h.id and p.insured_id=i.id 
and d.id=:draftId;
