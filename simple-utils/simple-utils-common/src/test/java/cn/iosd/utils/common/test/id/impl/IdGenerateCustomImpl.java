package cn.iosd.utils.common.test.id.impl;

import cn.iosd.utils.common.id.IdGenerate;

public class IdGenerateCustomImpl implements IdGenerate {
    @Override
    public Long generate() {
        return 1L;
    }

    @Override
    public String generateStr() {
        return "test";
    }
}
