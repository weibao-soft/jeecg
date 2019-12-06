select x.id, t.id productPlanId, t.prod_id productId, type.typename company, prod.prod_name productName, 
	t.prod_plan productPlan, t.premium, x.assign_status assignStatus
from wb_product_detail t left join 
(select * from wb_depart_product_rel r where r.depart_id = :departId and r.assign_status='1' ) x  
on t.id=x.product_detail_id
join wb_insurance_product prod on t.prod_id=prod.id
join t_s_type type on type.typecode=prod.insur_comp_name
join t_s_typegroup tg on tg.ID=type.typegroupid
where tg.typegroupcode='ins_comp'