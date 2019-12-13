create table wb_policy_change (
	id varchar(32) NOT NULL COMMENT '变更id',
	batch_num varchar(32) NOT NULL COMMENT '变更批次',
	insurance_policy_id varchar(32) NOT NULL COMMENT '保单id',
	status smallint(2) NOT NULL COMMENT '变更状态：1-代理提交；2-保险公司审批',
	description varchar(500) COMMENT '变更说明',
	createdate datetime NOT NULL COMMENT '创建时间',
    modifydate datetime NOT NULL COMMENT '修改时间'
);

create table wb_policy_change_content (
    policy_change_id varchar(32) NOT NULL COMMENT '保单id',
    id varchar(32) NOT NULL COMMENT '变更内容附件id',
    type varchar(255) COMMENT '类型：车牌、发动机号、机架号',
    status smallint(1) NOT NULL COMMENT '状态：0-无效；1-有效；',
    createdate datetime NOT NULL COMMENT '创建时间',
    modifydate datetime NOT NULL COMMENT '修改时间'
);

create table wb_policy_change_confirm (
    policy_change_id varchar(32) NOT NULL COMMENT '保单id',
    id varchar(32) NOT NULL COMMENT '确认内容附件id',
    type varchar(255) COMMENT '类型',
    status smallint(1) NOT NULL COMMENT '状态：0-无效；1-有效；',
    createdate datetime NOT NULL COMMENT '创建时间',
    modifydate datetime NOT NULL COMMENT '修改时间'
);