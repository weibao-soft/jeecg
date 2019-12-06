select type.typename company, prod.prod_name prodName, pd.prod_plan productPlan, pd.premium, rel.depart_id departid, 
	rel.product_detail_id productPlanId, conf.id, conf.period, conf.rate  
from  wb_depart_product_rel rel left join 
		(select *from wb_commission_conf conf1 where conf1.depart_user = :departId) conf
on rel.product_detail_id=conf.product_plan_id
join wb_product_detail pd on rel.product_detail_id=pd.id
join wb_insurance_product prod on prod.id=pd.prod_id
join t_s_type type on type.typecode=prod.insur_comp_name
join t_s_typegroup tg on tg.ID=type.typegroupid
where tg.typegroupcode='ins_comp' and rel.depart_id = :departId 
and rel.assign_status=1;