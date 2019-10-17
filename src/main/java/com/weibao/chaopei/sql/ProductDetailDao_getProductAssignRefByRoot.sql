select t.id productPlanId, t.product_id productId, prod.company, t.product_plan productPlan, x.assign_status  
from product_detail t left join 
(select * from wb_depart_product_rel r where r.depart_id = :departId and r.assign_status='1' ) x  
on t.id=x.product_detail_id
join product prod on t.product_id=prod.id