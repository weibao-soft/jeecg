select prod.prod_plan productPlan, sub.depart_user departid,  
	parent.product_plan_id productPlanId, parent.period, 
	parent.rate parentRate, sub.id, sub.rate 
from (
	select conf1.product_plan_id, conf1.period, conf1.rate 
	from t_s_user_org uo, wb_commission_conf conf1 where uo.org_id=conf1.depart_user and uo.user_id=:userId
)parent left join
(
	select * from wb_commission_conf conf1 where conf1.type=1 and conf1.depart_user=:userId 
)sub
on parent.product_plan_id=sub.product_plan_id
join wb_product_detail prod  on parent.product_plan_id = prod.id