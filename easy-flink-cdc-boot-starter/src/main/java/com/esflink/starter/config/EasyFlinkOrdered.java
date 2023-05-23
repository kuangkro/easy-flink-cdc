package com.esflink.starter.config;

/**
 * 定义 easy-flink 各模块加载顺序
 *
 * @author zhouhongyin
 * @since 2023/5/23 11:24
 */
public interface EasyFlinkOrdered {
    int ORDER_SINK = 10;
    int ORDER_CONF = 20;
    int ORDER_LISTENER = 30;
    int ORDER_REDIS = 40;
    int ORDER_ROCKETMQ = 40;
}