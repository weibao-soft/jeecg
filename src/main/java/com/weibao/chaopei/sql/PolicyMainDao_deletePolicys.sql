delete wb_insurance_policy
from wb_insurance_policy p,wb_draft_relation r,wb_insurance_draft d
where p.id=r.policy_id and d.id=r.draft_id 
and d.id=:draftId 
and p.id not in ( ${DaoFormat.getInStrs(ids)} );