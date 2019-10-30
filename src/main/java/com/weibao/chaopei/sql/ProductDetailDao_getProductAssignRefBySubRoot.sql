select 
	sub.id, det.id productPlanId, prod.id productId, prod.comp_name company, 
	det.prod_plan productPlan, sub.assign_status assignStatus 	 
from 
	(select * from wb_depart_product_rel a where a.depart_id = :parentdepartId and a.assign_status='1') pp left join
	(select *from wb_depart_product_rel b where b.depart_id = :subdepartId and b.assign_status='1' ) sub
	on pp.product_detail_id=sub.product_detail_id
	join wb_product_detail det on pp.product_detail_id = det.id
	join wb_insurance_product prod on det.prod_id = prod.id