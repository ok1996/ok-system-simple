CREATE TABLE IF NOT EXISTS base_param
(
    id                  bigint       NOT NULL AUTO_INCREMENT COMMENT '主键',
    module_names        json                  DEFAULT NULL COMMENT '模块名列表',
    param_key           varchar(64)  NOT NULL DEFAULT '' COMMENT 'key',
    code_values         json                  DEFAULT NULL COMMENT '参数值',
    remark              varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
    history_code_values json                  DEFAULT NULL COMMENT '历史参数值',
    create_time         timestamp NULL DEFAULT NULL COMMENT '创建时间',
    modify_time         timestamp NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id),
    UNIQUE KEY uq_param_paramkey (param_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='参数配置';