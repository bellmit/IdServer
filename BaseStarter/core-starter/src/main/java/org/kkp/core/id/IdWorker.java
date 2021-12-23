package org.kkp.core.id;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kkp.core.exception.SystemRuntimeException;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author Klaus
 * @since 2021/12/23
 **/
@Slf4j
public class IdWorker {

    /**
     * 机器id （0-31）
     */
    private long workerId;

    /**
     * 数据中心ID（0-31）
     */
    private long dataCenterId;

    /**
     * 序列号（0-4095）
     */
    private long sequence = 0L;

    /**
     * 上次的时间戳
     */
    private long lastTimestamp = -1L;

    public IdWorker() {
        this.dataCenterId = getDataCenterId(IdWorkerConstant.MAX_DATACENTER_ID);
        this.workerId = getMaxWorkerId(IdWorkerConstant.MAX_WORKER_ID, this.dataCenterId);
        log.info("id worker info: \n workerId: {} \n dataCenterId: {}", this.workerId, this.dataCenterId);
    }

    /**
     * constructor
     */
    public IdWorker(long workerId, long dataCenterId) {
        if (workerId > IdWorkerConstant.MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker ID can't greater than %d or less than 0", IdWorkerConstant.MAX_WORKER_ID));
        }
        if (dataCenterId > IdWorkerConstant.MAX_DATACENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("data center ID can't greater than %d or less than 0", IdWorkerConstant.MAX_DATACENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }


    /**
     * 下一个id
     *
     * @return ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new SystemRuntimeException(String.format("Clock move backwards. Refusing to generate next id for %d milliseconds", timestamp));
        }
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & IdWorkerConstant.SEQUENCE_MASK;
            if (sequence == 0L) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - IdWorkerConstant.TWEPOCH) << IdWorkerConstant.TIMESTAMP_LEFT_SHIFT) | (dataCenterId << IdWorkerConstant.DATACENTER_ID_SHIFT) | (workerId << IdWorkerConstant.WORKER_ID_SHIFT) | sequence;
    }


    /**
     * 获得下一个毫秒数
     *
     * @param lastTimestamp 指定时间戳
     * @return 下一个毫秒数
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取最大机器ID
     *
     * @return ID
     */
    protected long getMaxWorkerId(long dataCenterId, long maxWorkerId) {
        StringBuilder sb = new StringBuilder();
        sb.append(dataCenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (StringUtils.isNotBlank(name)) {
            sb.append(name.split("@")[0]);
        }
        //        MAC + PID 的hashcode 获取16位
        return (sb.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 获取数据中心id
     *
     * @param maxDataCenterId 最大的ID
     * @return ID
     */
    protected long getDataCenterId(long maxDataCenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (null != mac) {
                    id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                    id = id % (maxDataCenterId + 1);
                }
            }
        } catch (Exception e) {
            throw new SystemRuntimeException(e);
        }
        return id;
    }
}
