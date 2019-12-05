delete p
from wb_insurance_policy as p,wb_draft_relation as r,wb_insurance_draft as d
where p.id=r.policy_id and d.id=r.draft_id 
and d.id=:draftId 
and p.id not in ( ${DaoFormat.getInStrs(ids)} )