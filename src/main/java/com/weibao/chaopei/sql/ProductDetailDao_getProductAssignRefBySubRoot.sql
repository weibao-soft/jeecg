select 
	sub.id, det.id productPlanId, prod.id productId, prod.company, 
	det.product_plan productPlan, sub.assign_status assignStatus 	 
from 
	(select * from wb_depart_product_rel a where a.depart_id = :parentdepartId and a.assign_status='1') pp left join
	(select *from wb_depart_product_rel b where b.depart_id = :subdepartId and b.assign_status='1' ) sub
	on pp.product_detail_id=sub.product_detail_id
	join product_detail det on pp.product_detail_id = det.id
	join product prod on det.product_id = prod.id