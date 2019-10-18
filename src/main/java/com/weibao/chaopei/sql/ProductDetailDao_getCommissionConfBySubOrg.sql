select 
	prod.product_plan productPlan, sub.depart_user departid,  
	parent.product_plan_id productPlanId, parent.period, 
	parent.rate parentRate, sub.id, sub.rate 
from 
	(select conf1.id, conf1.product_plan_id, conf1.period, conf1.rate from wb_commission_conf conf1, t_s_depart depart 
		where conf1.depart_user = depart.parentdepartid and depart.ID=:departId
	) parent 
	left join 
	(select * from wb_commission_conf conf1 where conf1.depart_user=:departId)
	sub 
	on parent.product_plan_id = sub.product_plan_id
	join product_detail prod 
	on parent.product_plan_id = prod.id