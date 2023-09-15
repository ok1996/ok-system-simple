CREATE TABLE base_dict_info
(
    id               bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
    dict_group_param varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典分组参数',
    dict_group_desc  varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典分组说明',
    dict_value       varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典编码',
    dict_desc        varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典描述',
    dict_remark      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典备注',
    dict_sort        int                                     DEFAULT '0' COMMENT '字典排序',
    dict_status      int                                     DEFAULT '0' COMMENT '字典状态（0正常 1停用）',
    PRIMARY KEY (id),
    INDEX            dictGroupParamDesc(dict_group_param,dict_group_desc),
    INDEX            dictStatus(dict_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典数据';

INSERT INTO base_dict_info (id, dict_group_param, dict_group_desc, dict_value, dict_desc, dict_remark, dict_sort, dict_status) VALUES (1, 'leader', '测试数据-职位', '1', '领导', '测试数据-备注', 0, 0);
INSERT INTO base_dict_info (id, dict_group_param, dict_group_desc, dict_value, dict_desc, dict_remark, dict_sort, dict_status) VALUES (2, 'leader', '测试数据-职位', '2', '苦工', '测试数据-备注', 0, 0);
INSERT INTO base_dict_info (id, dict_group_param, dict_group_desc, dict_value, dict_desc, dict_remark, dict_sort, dict_status) VALUES (3, 'sex', '测试数据-性别', '1', '女', '测试数据-备注', 0, 0);
INSERT INTO base_dict_info (id, dict_group_param, dict_group_desc, dict_value, dict_desc, dict_remark, dict_sort, dict_status) VALUES (4, 'sex', '测试数据-性别', '2', '男', '测试数据-备注', 0, 0);