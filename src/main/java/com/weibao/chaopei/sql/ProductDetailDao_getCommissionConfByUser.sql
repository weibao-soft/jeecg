select type.typename company, pd.prod_name prodName, prod.premium, prod.prod_plan productPlan, sub.depart_user departid,    
	parent.product_plan_id productPlanId, sub.period, 
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
join wb_insurance_product pd on pd.id=prod.prod_id
join t_s_type type on type.typecode=pd.insur_comp_name
join t_s_typegroup tg on tg.ID=type.typegroupid
where tg.typegroupcode='ins_comp' 