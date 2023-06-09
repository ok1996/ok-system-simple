CREATE TABLE demo_datasource(
                               id bigint NOT NULL COMMENT '主键id',
                               content varchar(255) DEFAULT NULL COMMENT '内容',
                               PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='datasource示例模块数据库表';