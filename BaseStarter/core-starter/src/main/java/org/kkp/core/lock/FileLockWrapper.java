package org.kkp.core.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kkp.core.CoreConstant;
import org.kkp.core.CoreProperties;
import org.kkp.core.exception.SystemRuntimeException;
import org.kkp.core.util.CoreUtil;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Klaus
 * @since 2021/12/21
 **/
@Slf4j
public class FileLockWrapper implements Closeable {

    /**
     * 锁名字
     */
    private String name;

    /**
     * 锁
     */
    private FileLock lock;

    /**
     * 文件通道
     */
    private FileChannel fileChannel;

    public FileLockWrapper(String name) {
        this.initFileLock(name, false);
    }

    /**
     * 构造函数
     *
     * @param name 锁文件名称
     * @param wait 是否等待
     */
    public FileLockWrapper(String name, boolean wait) {
        this.initFileLock(name, wait);
    }


    /**
     * 初始化
     *
     * @param name 锁名字
     * @param wait
     */
    private void initFileLock(String name, boolean wait) {
        // 获取配置
        CoreProperties coreProperties = CoreConstant.applicationContext.getBean(CoreProperties.class);
        if (StringUtils.isBlank(coreProperties.getFileLockRootDir())) {
            throw new SystemRuntimeException("未配置文件锁根目录");
        }
        String path = coreProperties.getFileLockRootDir();
        // 设置名字
        this.name = path + name;
        this.initLock();
        if (wait) {
            // 获得文件通道
            do {
                this.initFileChannel();
            } while (this.fileChannel == null);
            // 获得锁
            do {
                this.initTryLock();
            } while (this.lock == null);
        } else {
            //获得文件通道
            this.initFileChannel();
            //获得锁
            this.initTryLock();
        }
    }

    /**
     * 初始化文件锁
     */
    private void initTryLock() {
        // 判断通道空
        if (this.fileChannel != null) {
            // 尝试获取锁
            try {
                this.lock = this.fileChannel.lock();
                log.debug("{} ----> {}, 获得文件锁成功", CoreUtil.getTraceId(), this.name);
            } catch (Exception ex) {
                log.debug("{} ----> {}, 获得文件锁失败,失败原因:{}", CoreUtil.getTraceId(), this.name, ex.getMessage());
            }
        }

    }

    private void initFileChannel() {
        // 创建文件通道， 读写都创建， 关闭后删除
        try {
            this.fileChannel = FileChannel.open(Paths.get(this.name), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW, StandardOpenOption.DELETE_ON_CLOSE);
            log.debug("{} ----> {}, 获得文件通道成功", CoreUtil.getTraceId(), this.name);
        } catch (Exception ex) {
            log.debug("{} ----> {}, 获得文件通道失败,失败原因:{}", CoreUtil.getTraceId(), this.name, ex.getMessage());
        }

    }

    private void initLock() {
        try {
            Files.createDirectories(Paths.get(this.name).getParent());
        } catch (Exception ex) {
            log.debug("{} ----> {} 创建文件根目录失败，原因是 {}", CoreUtil.getTraceId(), Paths.get(this.name).getParent(), ex.getMessage());
        }
    }


    @Override
    public void close() throws IOException {
        this.unlock();
    }

    private void unlock() {
        //释放锁
        if (this.isValid()) {
            try {
                this.lock.release();
                log.debug("{} --> {} 释放文件锁成功", CoreUtil.getTraceId(), this.name);
            } catch (Exception e) {
                log.debug("{} --> {} 释放文件锁失败,错误类型为 : {}", CoreUtil.getTraceId(), this.name, e.getClass().getName());
            } finally {
                this.lock = null;
            }
        }
        //释放文件通道
        if (this.fileChannel != null) {
            try {
                this.fileChannel.close();
                log.debug("{} --> {} 释放文件通道锁成功", CoreUtil.getTraceId(), this.name);
            } catch (Exception e) {
                log.debug("{} --> {} 释放文件通道锁失败,错误类型为 : {}", CoreUtil.getTraceId(), this.name, e.getClass().getName());
            } finally {
                this.fileChannel = null;
            }
        }
    }

    /**
     * 锁状态
     *
     * @return boolean
     */
    public boolean isValid() {
        return this.lock != null && this.lock.isValid();
    }

    /**
     * 锁文件列表
     *
     * @return List<String>
     * @throws IOException
     */
    public static List<Path> listAllLockFileName() throws IOException {
        //获得配置
        CoreProperties coreProperties = CoreConstant.applicationContext.getBean(CoreProperties.class);
        //判空
        if (StringUtils.isNotBlank(coreProperties.getFileLockRootDir())) {
            return Files.list(Paths.get(coreProperties.getFileLockRootDir())).collect(Collectors.toList());
        }
        //默认返回
        return new ArrayList<>();
    }
}
