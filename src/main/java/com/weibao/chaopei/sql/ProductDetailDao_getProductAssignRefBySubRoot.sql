select 
	sub.id, det.id productPlanId, prod.id productId, type.typename company, prod.prod_name productName , det.premium, 
	det.prod_plan productPlan, sub.assign_status assignStatus 	 
from 
	(select * from wb_depart_product_rel a where a.depart_id = :parentdepartId and a.assign_status='1') pp left join
	(select *from wb_depart_product_rel b where b.depart_id = :subdepartId and b.assign_status='1' ) sub
	on pp.product_detail_id=sub.product_detail_id
	join wb_product_detail det on pp.product_detail_id = det.id
	join wb_insurance_product prod on det.prod_id = prod.id	
  	join t_s_type type on type.typecode=prod.insur_comp_name
  	join t_s_typegroup tg on tg.ID=type.typegroupid
	where tg.typegroupcode='ins_comp'