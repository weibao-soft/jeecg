delete r 
from wb_draft_relation as r,wb_insurance_draft as d 
where d.id=r.draft_id 
and d.id=:draftId 
and r.policy_id not in ( ${DaoFormat.getInStrs(ids)} )