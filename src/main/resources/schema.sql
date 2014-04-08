
drop database horizon;
create database if not exists horizon;
use horizon;

create table if not exists horizon_user(
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 user_name varchar(200) comment '用户名',
 user_password varchar(100) comment '密码',
 user_email varchar(100) comment '邮箱',
 registered_time datetime comment '注册时间',
 updated_time datetime comment '更新时间',
 user_status TINYINT comment '用户状态 0：正常状态 1：限制状态' NOT NULL default '0',
 user_type TINYINT comment '用户类型 0:vender 1:business 2:superuser',
 country varchar(150) comment '注册用户国家',
 province varchar(150) comment '注册用户省份',
 city varchar(150) comment '注册用户城市',
 street_name varchar(150) comment '注册用户街道地址',
 company_name varchar(200) comment '商家名',
 description varchar(500) comment '商家描述',
 last_login datetime comment '上次登录时间',
 vip_level TINYINT comment 'vip等级',
 cell_phone_num varchar(20) comment '手机号码',
 qq_num varchar(20) comment 'QQ号码',
 acount DOUBLE comment '资金账户'
);

create table if not exists horizon_sms_content (
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 user_id BIGINT comment '用户id',
 sms_content VARCHAR(200) comment '短信验证码模板',
 status TINYINT comment '模板状态 1:审核 2：未审核',
 create_time datetime comment '创建时间',
 update_time datetime comment '更新时间'
);
alter table horizon_sms_content add constraint tb_horizon_user_fk Foreign Key(user_id) References horizon_user(id);


create table if not exists horizon_node(
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 node_name varchar(200) comment '节点名',
 alias_name varchar(200) comment '热点别名',
 mcode varchar(200) comment '路由mcode',
 industry TINYINT comment '所属行业',
 turn_off_time bigint comment '强制断开时间(分)',
 trun_off_free_time bigint comment '空闲断开时间((分))',
 multi_terminal_login TINYINT comment '是否允许多终端登录 0:no 1:yes',
 limit_speed TINYINT comment '是否限速 0:no 1:yes',
 total_limit_incoming BIGINT comment '总下载速度',
 total_limit_outgoing BIGINT comment '总上传速度',
 each_limit_incoming BIGINT comment '每用户下载速度',
 each_limit_outgoing BIGINT comment '每用户上传速断',
 start_time TIME comment '系统开放开始时间',
 end_time TIME comment '系统开放截止时间',
 portal_url varchar(200) comment '中转url',
 update_time DATETIME comment '配置更新时间',
 create_time DATETIME comment '创建时间',
 login_type TINYINT comment '登录类型 0：直接访问 1：用户名密码访问 2：手机验证',
 node_status TINYINT comment '节点状态 0：正常 1：限制',
 vender_id BIGINT comment '路由厂商',
 business_id BIGINT comment '普通商家',
 limit_online_user_num int comment '限制在线用户数',
 running TINYINT comment '节点是否正在运行',
 remain_sms BIGINT comment '剩余短信条数',
 sms_code_valid_time BIGINT comment '短信验证码有效期',
 sms_code_valid_time_type TINYINT comment '短信验证码有效期类型 1:分钟 2:小时 3:天',
 sms_type TINYINT comment '短信验证码类型 1:一次性 2:可多次登录',
 sms_code_length int comment '验证码长度',
 sms_code_day_num int comment '验证码一天获取次数',
 sms_code_obtain_interval BIGINT comment '短信验证码获取间隔',
 sms_content_id BIGINT comment '短信验证内容模板'
);
alter table horizon_node add constraint tb_horizon_user_vender_fk Foreign Key(vender_id) References horizon_ser(id);

alter table horizon_node add constraint tb_horizon_user_business_fk Foreign Key(business_id) References horizon_user(id);
alter table horizon_node add constraint tb_horizon_sms_content_fk Foreign Key(sms_content_id) References horizon_sms_content(id);


create table if not exists horizon_common_user(
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 user_name varchar(200) comment '用户名',
 user_password varchar(100) comment '密码',
 node_id BIGINT comment '节点id -1:还没授予节点 -2:支持所有节点 other id:节点ID',
 user_id BIGINT comment '商户ID',
 user_type TINYINT comment '用户类型  1:一次性用户 2:普通用户',
 multi_terminal_login TINYINT comment '是否允许多终端登录 -1:未设置 1:no 2:yes',
 valid_time datetime comment '用户有效时间',
 usable_time BIGINT comment '用户可用时间',
 user_status TINYINT comment '用户状态',
 description varchar(500) comment '用户描述',
 is_logined TINYINT comment '是否登录过系统，为一次性用户做准备 0:未登录过 1:已登录过',
 create_time datetime comment '创建时间',
 update_time datetime comment '更新时间'
);
alter table horizon_common_user add constraint tb_horizon_user_fk Foreign Key(user_id) References horizon_user(id);
alter table horizon_common_user add constraint tb_horizon_node_fk Foreign Key(node_id) References horizon_node(id);

create table horizon_tokens(
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 token varchar(255) comment '用户验证token',
 node_id BIGINT comment 'ID',
 user_id BIGINT comment 'ID',
 status TINYINT comment 'token状态'
);

alter table horizon_tokens add constraint tb_horizon_node_fk Foreign Key(node_id) References horizon_node(id);
alter table horizon_tokens add constraint tb_horizon_common_user_fk Foreign Key(user_id) References horizon_common_user(id);

create table if not exists horizon_node_connection(
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 node_id BIGINT comment '节点id',
 tokens_id BIGINT(255) comment '连接标记token',
 status TINYINT comment '连接状态',
 mac varchar(255) comment '连接mac地址',
 ip varchar(255) comment '连接ip地址',
 identity varchar(255) comment '验证标记',
 incoming BIGINT comment '实时下载流量',
 outgoing BIGINT comment '实时上传流量',
 total_incoming BIGINT comment '总下载流量',
 total_outgoing BIGINT comment '总上传流量',
 connect_start datetime comment '连接开始时间',
 connect_end datetime comment '连接截至时间',
 user_id BIGINT comment '用户ID',
 cell_phone_num varchar(20) comment '手机号码',
 device TINYINT comment '设备类型',
 web_ip varchar(255) comment '互联网IP',
 connect_type TINYINT comment '连接类型',
 interrupt_reason TINYINT comment '中断原因',
 business_id BIGINT comment '商户ID',
 free_time BIGINT comment '空闲时间'
);

alter table horizon_node_connection add constraint tb_horizon_node_fk Foreign Key(node_id) References horizon_node(id);
alter table horizon_node_connection add constraint tb_horizon_common_user_fk Foreign Key(user_id) References horizon_common_user(id);
alter table horizon_node_connection add constraint tb_horizon_tokens_fk Foreign Key(tokens_id) References horizon_tokens(id);
alter table horizon_node_connection add constraint tb_horizon_business_user_fk Foreign Key(business_id) References horizon_user(id);

create table if not exists horizon_route_record(
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 node_id BIGINT comment '节点id',
 sys_uptime BIGINT comment '系统运行时间',
 sys_mem_free BIGINT comment '系统可用内存',
 sys_load float comment '系统负载',
 wifidog_uptime BIGINT comment 'wifidog运行时间',
 create_time datetime comment '连接截至时间'
);
alter table horizon_route_record add constraint tb_horizon_node_fk Foreign Key(node_id) References horizon_node(id);

create table if not exists horizon_route_status(
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 node_id BIGINT comment '节点id',
 web_ip varchar(255) comment '互联网IP',
 create_time datetime comment '创建时间'
);

alter table horizon_route_status add constraint tb_horizon_node_fk Foreign Key(node_id) References horizon_node(id);


create table if not exists horizon_user_trade (
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 user_id BIGINT comment '用户id',
 total_price DOUBLE comment '总价',
 status TINYINT comment '状态',
 trade_type TINYINT comment '交易类型 1:充值 2:取现',
 create_time datetime comment '创建时间',
 update_time datetime comment '更新时间',
 description varchar(200) comment '描述',
 trade_way TINYINT comment '交易方式 1：银行卡转账 2:支付宝转账'
);
alter table horizon_user_trade add constraint tb_horizon_business_user_fk Foreign Key(user_id) References horizon_user(id);

create table if not exists horizon_sms_trade (
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 user_id BIGINT comment '用户id',
 node_id BIGINT comment '节点id',
 apply_sms BIGINT comment '申请的短信条数',
 total_price DOUBLE comment '总价',
 status TINYINT comment '状态',
 create_time datetime comment '创建时间',
 update_time datetime comment '更新时间'
);

alter table horizon_sms_trade add constraint tb_horizon_business_user_fk Foreign Key(user_id) References horizon_user(id);
alter table horizon_sms_trade add constraint tb_horizon_node_fk Foreign Key(node_id) References horizon_node(id);

create table if not exists horizon_sms_security_code (
 id BIGINT comment 'ID' primary key AUTO_INCREMENT,
 user_id BIGINT comment '用户id',
 node_id BIGINT comment '节点id',
 cell_phone_num varchar(20) comment '手机号码',
 security_code varchar(10) comment '手机验证码',
 create_time datetime comment '创建时间',
 status TINYINT comment '使用情况 1：使用 2：未使用',
 mobile_type TINYINT comment '运营商类型 1：移动 2：联通 3：电信'
);
alter table horizon_sms_security_code add constraint tb_horizon_business_user_fk Foreign Key(user_id) References horizon_user(id);
alter table horizon_sms_security_code add constraint tb_horizon_node_fk Foreign Key(node_id) References horizon_node(id);

insert into horizon_user values(null,'admin', 'admin', 'ganjunzx@gmail.com',now(), now(), 0, 0, '中国', '广东', '深圳', '白石洲乔城豪苑', '科创', '科创', now(), 0, '18676719508', '305534274', 0);

--create table if not exists horizon_sms_use_record (
-- id BIGINT comment 'ID' primary key AUTO_INCREMENT,
-- user_id BIGINT comment '用户id',
-- node_id BIGINT comment '节点id',
-- cell_phone_num varchar(20) comment '手机号码',
-- use_type TINYINT comment '消费类型 1.',
-- use_content varchar(200) comment '短信内容',
-- create_time datetime comment '创建时间'
--);
--alter table horizon_sms_use_record add constraint tb_horizon_node_fk Foreign Key(node_id) References horizon_node(id);
--alter table horizon_sms_use_record add constraint tb_horizon_business_user_fk Foreign Key(user_id) References horizon_user(id);

--insert into horizon_node_connection values(null, 2, -1, 1,null, null, null, -1, -1, -1, -1, now(), now(), 1, null, 0, null, 0, 0, 3);

--create table if not exists horizon_node_product(
-- id BIGINT comment 'ID' primary key AUTO_INCREMENT,
-- node_id BIGINT comment '节点id',
-- vender_id BIGINT comment '路由厂商',
-- business_id BIGINT comment '普通商家'
--);
--
--alter table horizon_node_product add constraint tb_horizon_user_vender_fk Foreign Key(vender_id) References horizon_ser(id);
--
--alter table horizon_node_product add constraint tb_horizon_user_business_fk Foreign Key(business_id) References horizon_user(id);
--
--alter table horizon_node_product add constraint tb_horizon_node_fk Foreign Key(node_id) References horizon_node(id);



--create table horizon_area(
-- id INT comment 'ID' primary key AUTO_INCREMENT,
-- country varchar(10) comment '国家',
-- province varchar(20) comment '省份',
-- city varchar(20) comment '城市'
--)
--
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '西城');
--insert into horizon_area values(null, '中国', '北京', '朝阳');
--insert into horizon_area values(null, '中国', '北京', '崇文');
--insert into horizon_area values(null, '中国', '北京', '房山');
--insert into horizon_area values(null, '中国', '北京', '丰台');
--insert into horizon_area values(null, '中国', '北京', '海淀');
--insert into horizon_area values(null, '中国', '北京', '门头沟');
--insert into horizon_area values(null, '中国', '北京', '石景山');
--insert into horizon_area values(null, '中国', '北京', '宣武');
--insert into horizon_area values(null, '中国', '北京', '昌平');
--insert into horizon_area values(null, '中国', '北京', '大兴');
--insert into horizon_area values(null, '中国', '北京', '怀柔');
--insert into horizon_area values(null, '中国', '北京', '平谷');
--insert into horizon_area values(null, '中国', '北京', '顺义');
--insert into horizon_area values(null, '中国', '北京', '通州');
--insert into horizon_area values(null, '中国', '北京', '密云');
--insert into horizon_area values(null, '中国', '北京', '延庆');
--insert into horizon_area values(null, '中国', '北京', '延庆');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');
--insert into horizon_area values(null, '中国', '北京', '东城');