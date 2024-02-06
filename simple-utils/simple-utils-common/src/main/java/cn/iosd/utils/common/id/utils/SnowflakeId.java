package cn.iosd.utils.common.id.utils;

/**
 * 雪花id - 由时间戳、数据中心 ID、机器 ID 和序列号组成的 64 位整数。
 * <pre>
 *      时间戳占比：42位 (支持139年的时间范围)
 *      数据中心 ID 占比：5位 (使用进程ID作为数据中心ID)
 *      机器 ID 占比：5位  (根据本机硬件地址生成机器ID)
 *      序列号占比：12位
 * </pre>
 *
 * @author ok1996
 */
public class SnowflakeId {
    /**
     * 起始点 2023-12-07 02:53:32
     */
    private static final long EPOCH = 1701888812345L;

    private static final int BITS_MACHINE_ID = 5;
    private static final int BITS_DATACENTER_ID = 5;
    private static final int BITS_TIMESTAMP = 42;
    private static final int BITS_SEQUENCE = 12;
    private static final long MAX_SEQUENCE = (1L << BITS_SEQUENCE) - 1;

    private static final long MACHINE_ID;
    private static final long DATACENTER_ID;
    private static long lastTimestamp = -1L;
    private static long sequence = 0L;

    static {
        MACHINE_ID = ObjectId.generateMachineId();
        DATACENTER_ID = ObjectId.generateProcessId();
    }

    public static synchronized long generate() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID for " + (lastTimestamp - timestamp) + " milliseconds.");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                timestamp = waitNextMillis(timestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << (BITS_MACHINE_ID + BITS_DATACENTER_ID + BITS_SEQUENCE))
                | (DATACENTER_ID << (BITS_MACHINE_ID + BITS_SEQUENCE))
                | (MACHINE_ID << (BITS_SEQUENCE + BITS_TIMESTAMP))
                | sequence;
    }

    private static long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
