-- ----------------------------
-- Table structure for `wb_insurance_product`
-- ----------------------------
drop table if exists wb_insurance_product;
create table wb_insurance_product
(
   id                   varchar(32) NOT NULL COMMENT '产品id',
   prod_code            varchar(32) DEFAULT NULL COMMENT '产品代码',
  `prod_name`           varchar(100) DEFAULT NULL COMMENT '产品名称',
   prod_type            varchar(30) DEFAULT NULL COMMENT '产品类型',
   insur_comp_name      varchar(100) DEFAULT NULL COMMENT '保险公司名称',
   period          	    varchar(10) DEFAULT NULL COMMENT '期限',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='保险产品表';


-- ----------------------------
-- Table structure for `wb_product_detail`
-- ----------------------------
drop table if exists wb_product_detail;
create table wb_product_detail
(
   id                   varchar(32) NOT NULL COMMENT '方案id',
  `prod_id`             varchar(32) DEFAULT NULL COMMENT '产品id',
   plan_code            varchar(32) DEFAULT NULL COMMENT '方案代码',
   prod_plan            varchar(100) DEFAULT NULL COMMENT '产品方案',
   plan_type            varchar(30) DEFAULT NULL COMMENT '营运性质',
   premium          	float(10,2) DEFAULT 0 COMMENT '保费',
   sort_no              int DEFAULT 0 COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='产品方案明细表';


-- ----------------------------
-- Table structure for `wb_insurance_policy`
-- ----------------------------
drop table if exists wb_insurance_policy;
create table wb_insurance_policy
(
   id                   varchar(32) NOT NULL COMMENT '保单id',
  `prod_id`             varchar(32) NOT NULL COMMENT '产品id',
   plan_id              varchar(32) NOT NULL COMMENT '保障方案id',
   policy_no            varchar(30) DEFAULT NULL COMMENT '保险单号',
   batch_no             varchar(30) DEFAULT NULL COMMENT '批单号',
   plate_no             varchar(20) DEFAULT NULL COMMENT '车牌号',
   frame_no             varchar(30) NOT NULL COMMENT '车架号',
   engine_no            varchar(30) DEFAULT NULL COMMENT '发动机号',
   start_date           datetime DEFAULT NULL COMMENT '保险开始日期',
   end_date             datetime DEFAULT NULL COMMENT '保险结束日期',
   contact_name         varchar(30) DEFAULT NULL COMMENT '投保联系人',
   policy_mobile        varchar(20) DEFAULT NULL COMMENT '保单接收手机',
  `holder_nature`       varchar(10) DEFAULT NULL COMMENT '投保人性质',
   holder_org_code      varchar(24) NOT NULL COMMENT '投保组织机构代码',
   holder_comp_name     varchar(100) DEFAULT NULL COMMENT '投保单位名称',
   holder_comp_nature   varchar(10) DEFAULT NULL COMMENT '投保单位性质',
   industry_type        varchar(10) DEFAULT NULL COMMENT '行业类别',
   insured_org_code     varchar(24) NOT NULL COMMENT '被保组织机构代码',
  `insured_comp_name`   varchar(100) DEFAULT NULL COMMENT '被保单位名称',
   invoice_type         char(1) DEFAULT '1' COMMENT '发票类型',
   taxpayer_no          varchar(30) DEFAULT NULL COMMENT '纳税人识别号',
   receiver_mobile      varchar(20) DEFAULT NULL COMMENT '普票接收手机',
   comp_name            varchar(100) DEFAULT NULL COMMENT '专票公司名称',
   comp_address         varchar(200) DEFAULT NULL COMMENT '专票公司地址',
   comp_phone           varchar(20) DEFAULT NULL COMMENT '专票公司电话',
   deposit_bank         varchar(30) DEFAULT NULL COMMENT '开户行',
   bank_account         varchar(30) DEFAULT NULL COMMENT '银行账号',
  `recipients`          varchar(64) DEFAULT NULL COMMENT '专票收件人',
   recipients_tel       varchar(20) DEFAULT NULL COMMENT '专票收件人电话',
  `reci_address`        varchar(255) DEFAULT NULL COMMENT '专票收件地址',
   premium          	float(10,2) DEFAULT 0 COMMENT '保费',
   proposal_no          varchar(32) NOT NULL COMMENT '投保单号',
   order_no             varchar(32) NOT NULL COMMENT '订单编号',
   policy_url           varchar(255) NOT NULL COMMENT '电子保单Url',
   invoice_numb         varchar(10) NOT NULL COMMENT '发票号码',
   ton_count            int DEFAULT 0 COMMENT '核定载重质量',
   seat_num             int DEFAULT 0 COMMENT '座位数',
   car_type_code        char(1) DEFAULT '0' COMMENT '车辆使用性质',
   is_paper_policy      char(1) DEFAULT '0' COMMENT '是否纸质保单',
   is_paper_invoice     char(1) DEFAULT '0' COMMENT '是否纸质发票',
   user_id              varchar(32) NOT NULL COMMENT '用户id',
   status               char(1) NOT NULL DEFAULT '1' COMMENT '保单状态',
   pay_status           char(1) DEFAULT '0' COMMENT '支付状态',
   pay_time             datetime DEFAULT NULL COMMENT '支付时间',
   reward_status        char(1) DEFAULT '0' COMMENT '分润状态',
   reward_time          datetime DEFAULT NULL COMMENT '分润时间',
   create_time          datetime DEFAULT NULL COMMENT '创建时间',
   last_update_time     datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='保单信息表';


-- ----------------------------
-- Table structure for `wb_policy_holder`
-- ----------------------------
drop table if exists wb_policy_holder;
create table wb_policy_holder
(
   id                   varchar(32) NOT NULL COMMENT '投保人id',
  `holder_nature`       varchar(10) DEFAULT NULL COMMENT '投保人性质',
   holder_org_code      varchar(24) NOT NULL COMMENT '投保组织机构代码',
   holder_comp_name     varchar(100) DEFAULT NULL COMMENT '投保单位名称',
   holder_comp_nature   varchar(10) DEFAULT NULL COMMENT '投保单位性质',
   industry_type        varchar(10) DEFAULT NULL COMMENT '行业类别',
   taxpayer_no          varchar(30) DEFAULT NULL COMMENT '纳税人识别号',
   receiver_mobile      varchar(20) DEFAULT NULL COMMENT '普票接收手机',
   comp_name            varchar(100) DEFAULT NULL COMMENT '专票公司名称',
   comp_address         varchar(200) DEFAULT NULL COMMENT '专票公司地址',
   comp_phone           varchar(20) DEFAULT NULL COMMENT '专票公司电话',
   deposit_bank         varchar(30) DEFAULT NULL COMMENT '开户行',
   bank_account         varchar(30) DEFAULT NULL COMMENT '银行账号',
   last_update_time     datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='投保人信息表';


-- ----------------------------
-- Table structure for `wb_invoice_receiver`
-- ----------------------------
drop table if exists wb_invoice_receiver;
create table wb_invoice_receiver
(
   id                   varchar(32) NOT NULL COMMENT '收件人id',
  `recipients`          varchar(64) DEFAULT NULL COMMENT '专票收件人',
   recipients_tel       varchar(20) NOT NULL COMMENT '专票收件人电话',
  `reci_address`        varchar(255) DEFAULT NULL COMMENT '专票收件地址',
   user_id              varchar(32) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='发票收件人信息表';


-- ----------------------------
-- Table structure for `wb_insurance_draft`
-- ----------------------------
drop table if exists wb_insurance_draft;
create table wb_insurance_draft
(
   id                   varchar(32) NOT NULL COMMENT '草稿id',
   plan_id              varchar(32) NOT NULL COMMENT '保障方案id',
   holder_comp_name     varchar(100) DEFAULT NULL COMMENT '投保单位名称',
  `recipients`          varchar(64) DEFAULT NULL COMMENT '专票收件人',
   truck_nums           int DEFAULT NULL COMMENT '投保车辆（台）',
   status               char(1) DEFAULT '1' COMMENT '状态',
   user_id              varchar(32) DEFAULT NULL COMMENT '用户id',
  `save_time`           datetime DEFAULT NULL COMMENT '暂存时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='保单暂存表';


-- ----------------------------
-- Table structure for `wb_draft_relation`
-- ----------------------------
drop table if exists wb_draft_relation;
create table wb_draft_relation
(
   id                   varchar(32) NOT NULL COMMENT '关系表id',
   draft_id             varchar(32) DEFAULT NULL COMMENT '草稿id',
  `policy_id`           varchar(32) DEFAULT NULL COMMENT '保单id',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='草稿关联保单关系表';


-- ----------------------------
-- Table structure for `wb_company_account`
-- ----------------------------
drop table if exists wb_company_account;
create table wb_company_account
(
   id                   varchar(32) NOT NULL COMMENT '投保人id',
  `depart_id`           varchar(32) DEFAULT NULL COMMENT '部门id',
   bank_acct_name       varchar(100) NOT NULL COMMENT '银行户名',
   bank_no              varchar(32) DEFAULT NULL COMMENT '银行账号',
   bank_info            varchar(100) DEFAULT NULL COMMENT '银行开户行信息',
   real_name            varchar(100) DEFAULT NULL COMMENT '真实姓名',
   certi_no             varchar(32) DEFAULT NULL COMMENT '身份证号码',
   withdraw_passwd      varchar(100) DEFAULT NULL COMMENT '提现密码',
   received_balance     float(10,2) DEFAULT 0 COMMENT '已分润的可提现余额',
   unreceived_balance   float(10,2) DEFAULT 0 COMMENT '未到分账期的待分润余额',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='公司账户表';


-- ----------------------------
-- Table structure for `wb_personal_account`
-- ----------------------------
drop table if exists wb_personal_account;
create table wb_personal_account
(
   id                   varchar(32) NOT NULL COMMENT '投保人id',
  `user_id`             varchar(32) DEFAULT NULL COMMENT '用户id',
   bank_acct_name       varchar(100) NOT NULL COMMENT '银行户名',
   bank_no              varchar(32) DEFAULT NULL COMMENT '银行账号',
   bank_info            varchar(100) DEFAULT NULL COMMENT '银行开户行信息',
   real_name            varchar(100) DEFAULT NULL COMMENT '真实姓名',
   certi_no             varchar(32) DEFAULT NULL COMMENT '身份证号码',
   withdraw_passwd      varchar(100) DEFAULT NULL COMMENT '提现密码',
   received_balance     float(10,2) DEFAULT 0 COMMENT '已分润的可提现余额',
   unreceived_balance   float(10,2) DEFAULT 0 COMMENT '未到分账期的待分润余额',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='个人账户表';


-- ----------------------------
-- Table structure for `wb_company_rewarded_detail`
-- ----------------------------
drop table if exists wb_company_rewarded_detail;
create table wb_company_rewarded_detail
(
   id                   varchar(32) NOT NULL COMMENT '投保人id',
  `company_account_id`  varchar(32) DEFAULT NULL COMMENT '公司账户ID',
  `depart_id`           varchar(32) DEFAULT NULL COMMENT '部门id',
  `policy_id`           varchar(32) DEFAULT NULL COMMENT '保单id',
   amount          	    float(10,2) DEFAULT 0 COMMENT '金额',
   status               char(1) NOT NULL DEFAULT '0' COMMENT '分润状态：0-已分润，可提现；1-已提现，待到账；2-已到账',
   divide_time          datetime DEFAULT NULL COMMENT '分润时间',
   withdraw_time        datetime DEFAULT NULL COMMENT '申请提现时间',
   receive_time         datetime DEFAULT NULL COMMENT '收到账时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='公司已分润佣金明细表';


-- ----------------------------
-- Table structure for `wb_company_unreward_detail`
-- ----------------------------
drop table if exists wb_company_unreward_detail;
create table wb_company_unreward_detail
(
   id                   varchar(32) NOT NULL COMMENT '投保人id',
  `company_account_id`  varchar(32) DEFAULT NULL COMMENT '公司账户ID',
  `depart_id`           varchar(32) DEFAULT NULL COMMENT '部门id',
  `policy_id`           varchar(32) DEFAULT NULL COMMENT '保单id',
   amount          	    float(10,2) DEFAULT 0 COMMENT '金额',
   pay_time             datetime DEFAULT NULL COMMENT '支付成功时间',
   reward_time          datetime DEFAULT NULL COMMENT '分润到账时间',
   generate_time        datetime DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='公司账户待分润佣金明细';


-- ----------------------------
-- Table structure for `wb_personal_rewarded_detail`
-- ----------------------------
drop table if exists wb_personal_rewarded_detail;
create table wb_personal_rewarded_detail
(
   id                   varchar(32) NOT NULL COMMENT '投保人id',
  `personal_account_id` varchar(32) DEFAULT NULL COMMENT '个人账户ID',
  `user_id`             varchar(32) DEFAULT NULL COMMENT '用户id',
  `policy_id`           varchar(32) DEFAULT NULL COMMENT '保单id',
   amount          	    float(10,2) DEFAULT 0 COMMENT '金额',
   status               char(1) NOT NULL DEFAULT '0' COMMENT '分润状态：0-已分润，可提现；1-已提现，待到账；2-已到账',
   divide_time          datetime DEFAULT NULL COMMENT '分润时间',
   withdraw_time        datetime DEFAULT NULL COMMENT '申请提现时间',
   receive_time         datetime DEFAULT NULL COMMENT '收到账时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='个人已分润佣金明细表';


-- ----------------------------
-- Table structure for `wb_personal_unreward_detail`
-- ----------------------------
drop table if exists wb_personal_unreward_detail;
create table wb_personal_unreward_detail
(
   id                   varchar(32) NOT NULL COMMENT '投保人id',
  `personal_account_id` varchar(32) DEFAULT NULL COMMENT '个人账户ID',
  `user_id`             varchar(32) DEFAULT NULL COMMENT '用户id',
  `policy_id`           varchar(32) DEFAULT NULL COMMENT '保单id',
   amount          	    float(10,2) DEFAULT 0 COMMENT '金额',
   pay_time             datetime DEFAULT NULL COMMENT '支付成功时间',
   reward_time          datetime DEFAULT NULL COMMENT '分润到账时间',
   generate_time        datetime DEFAULT NULL COMMENT '生成时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='个人账户待分润佣金明细';


-- ----------------------------
-- Table structure for `wb_withdraw_order`
-- ----------------------------
drop table if exists wb_withdraw_order;
create table wb_withdraw_order
(
   id                   varchar(32) NOT NULL COMMENT '投保人id',
  `account_id`          varchar(32) DEFAULT NULL COMMENT '公司/个人账户ID',
  `alipay_acct`         varchar(32) DEFAULT NULL COMMENT '支付宝账号',
  `alipay_name`         varchar(255) DEFAULT NULL COMMENT '支付宝实名',
  `bank_info`           varchar(255) DEFAULT NULL COMMENT '提现的银行信息',
   amount          	    float(10,2) DEFAULT 0 COMMENT '本次提现金额',
   org_type             char(1) NOT NULL DEFAULT '0' COMMENT '提现机构类型：0-机构；1-个人',
   status               char(1) NOT NULL DEFAULT '0' COMMENT '提现状态：0-未到账；1-已到账；',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='公司及个人账户提现记录';


-- ----------------------------
-- Table structure for `wb_withdraw_order_detail`
-- ----------------------------
drop table if exists wb_withdraw_order_detail;
create table wb_withdraw_order_detail
(
   id                   varchar(32) NOT NULL COMMENT '投保人id',
  `order_id`            varchar(32) DEFAULT NULL COMMENT '提现记录',
  `reward_detail_id`    varchar(32) DEFAULT NULL COMMENT '佣金分润明细id',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='公司及个人账户提现记录关联明细的关系';



CREATE UNIQUE INDEX policy_holder_unindex ON wb_policy_holder(holder_org_code);
CREATE UNIQUE INDEX invoice_receiver_unindex ON wb_invoice_receiver(recipients_tel);


-- ----------------------------
-- Table structure for `wb_insured_info`
-- ----------------------------
/*drop table if exists wb_insured_info;
create table wb_insured_info
(
   id                   varchar(32) NOT NULL COMMENT '被保人id',
   insured_org_code     varchar(24) NOT NULL COMMENT '被保组织机构代码',
  `insured_comp_name`   varchar(100) DEFAULT NULL COMMENT '被保单位名称',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='被投保人信息表';
CREATE UNIQUE INDEX insured_info_unindex ON wb_insured_info(insured_org_code);
*/
