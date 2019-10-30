select x.id, t.id productPlanId, t.prod_id productId, prod.comp_name company, t.prod_plan productPlan, x.assign_status assignStatus
from wb_product_detail t left join 
(select * from wb_depart_product_rel r where r.depart_id = :departId and r.assign_status='1' ) x  
on t.id=x.product_detail_id
join wb_insurance_product prod on t.prod_id=prod.id