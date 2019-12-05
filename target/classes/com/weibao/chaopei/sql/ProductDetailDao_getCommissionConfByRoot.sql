select pd.prod_plan productPlan, rel.depart_id departid, rel.product_detail_id productPlanId, conf.id, conf.period, conf.rate 
from  wb_depart_product_rel rel left join 
		(select *from wb_commission_conf conf1 where conf1.depart_user = :departId) conf
on rel.product_detail_id=conf.product_plan_id
join wb_product_detail pd on rel.product_detail_id=pd.id
where rel.depart_id = :departId 
and rel.assign_status=1;