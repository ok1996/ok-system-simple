package cn.iosd.utils.common.id.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.time.Instant;

/**
 * 生成24位长度的唯一标识符，其中包含时间戳、机器ID、进程ID和计数器
 *
 * <pre>
 *      时间戳占比：16位 (支持136年的时间范围)
 *      进程 ID 占比：4位 (使用运行时MXBean获取进程名称的哈希码)
 *      机器 ID 占比：6位  (根据本机硬件地址生成机器ID)
 *      序列号占比：6位
 * </pre>
 *
 * @author ok1996
 */
public class ObjectId {
    private static final String HEX_CHARS = "0123456789abcdef";
    private static final int MACHINE_ID;
    private static final int PROCESS_ID;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static int counter = 0;

    static {
        MACHINE_ID = generateMachineId();
        PROCESS_ID = generateProcessId();
    }

    public static String generate() {
        StringBuilder objectId = new StringBuilder(24);
        appendTimestamp(objectId);
        appendIntToHexString(objectId, MACHINE_ID, 6);
        appendIntToHexString(objectId, PROCESS_ID, 4);
        appendIntToHexString(objectId, getNextCounter(), 6);
        return objectId.toString();
    }

    private static void appendTimestamp(StringBuilder objectId) {
        long timestamp = Instant.now().getEpochSecond();
        appendIntToHexString(objectId, (int) (timestamp >> 32), 8);
        appendIntToHexString(objectId, (int) timestamp, 8);
    }

    private static void appendIntToHexString(StringBuilder objectId, int value, int length) {
        for (int i = length - 1; i >= 0; i--) {
            int index = (value >>> (i * 4)) & 0xF;
            objectId.append(HEX_CHARS.charAt(index));
        }
    }

    /**
     * 根据本机硬件地址生成机器ID，如果无法获取本机信息，则随机生成一个
     *
     * @return 机器ID
     */
    public static int generateMachineId() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localhost);
            byte[] mac = networkInterface.getHardwareAddress();
            return ((mac[mac.length - 2] & 0xFF) << 8) | (mac[mac.length - 1] & 0xFF);
        } catch (Exception e) {
            return RANDOM.nextInt(0x10000);
        }
    }

    /**
     * 生成进程ID，使用运行时MXBean获取进程名称的哈希码
     *
     * @return 进程ID
     */
    public static int generateProcessId() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode() & 0xFFFF;
    }

    private static synchronized int getNextCounter() {
        counter = (counter + 1) & 0xFFFFFF;
        return counter;
    }
}
