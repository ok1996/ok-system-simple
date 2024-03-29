package cn.iosd.utils.common.id.impl;

import cn.iosd.utils.common.id.IdGenerate;
import cn.iosd.utils.common.id.utils.ObjectId;
import cn.iosd.utils.common.id.utils.SnowflakeId;

/**
 * @author ok1996
 */
public class SimpleIdGenerate implements IdGenerate {

    public SimpleIdGenerate(){
        SnowflakeId.setDatacenterId(1L);
        SnowflakeId.setMachineId(1L);
    }

    @Override
    public Long generate() {
        return SnowflakeId.generate();
    }

    @Override
    public String generateStr() {
        return ObjectId.generate();
    }
}