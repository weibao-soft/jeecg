select 
	type.typename company, pd.prod_name prodName, prod.premium, prod.prod_plan productPlan, sub.depart_user departid,    
	parent.product_plan_id productPlanId, parent.period parentPeriod, sub.period, 
	parent.rate parentRate, sub.id, sub.rate 
from 
	(select conf1.id, conf1.product_plan_id, conf1.period, conf1.rate from wb_commission_conf conf1, t_s_depart depart 
		where conf1.depart_user = depart.parentdepartid and depart.ID=:departId
	) parent 
	left join 
	(select * from wb_commission_conf conf1 where conf1.depart_user=:departId)
	sub 
	on parent.product_plan_id = sub.product_plan_id
	join wb_product_detail prod on parent.product_plan_id = prod.id
	join wb_insurance_product pd on pd.id=prod.prod_id
	join t_s_type type on type.typecode=pd.insur_comp_name
	join t_s_typegroup tg on tg.ID=type.typegroupid
	where tg.typegroupcode='ins_comp' 