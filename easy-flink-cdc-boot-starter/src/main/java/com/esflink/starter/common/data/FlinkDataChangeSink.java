package com.esflink.starter.common.data;


import org.apache.flink.streaming.api.functions.sink.SinkFunction;

/**
 * 消息接收的处理类
 *
 * @author zhouhongyin
 * @since 2023/3/5 23:04
 */
public interface FlinkDataChangeSink extends SinkFunction<DataChangeInfo> {
    @Override
    void invoke(DataChangeInfo value, Context context) throws Exception;
}