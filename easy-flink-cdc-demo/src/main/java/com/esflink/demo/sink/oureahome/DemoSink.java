package com.esflink.demo.sink.oureahome;

import com.alibaba.fastjson.JSONObject;
import com.esflink.demo.pojo.OdsRegion;
import com.esflink.starter.annotation.FlinkSink;
import com.esflink.starter.common.data.DataChangeInfo;
import com.esflink.starter.common.data.FlinkJobSink;
import org.apache.doris.flink.cfg.DorisExecutionOptions;
import org.apache.doris.flink.cfg.DorisOptions;
import org.apache.doris.flink.cfg.DorisReadOptions;
import org.apache.doris.flink.sink.DorisSink;
import org.apache.doris.flink.sink.writer.serializer.SimpleStringSerializer;
import org.apache.doris.shaded.com.google.gson.JsonObject;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * <p> 同步信息到 es </p>
 *
 * @author zhouhongyin
 * @since 2023/6/9 17:11
 */

@FlinkSink(value = "mssql", database = "CreditAPI", table = "dbo.Region")
public class DemoSink implements FlinkJobSink {

    private DorisSink.Builder<String> buildOptions(){
        DorisSink.Builder<String> builder = DorisSink.builder();

        DorisOptions dorisOptions = DorisOptions.builder()
                .setFenodes("192.168.6.51:8030")
                .setTableIdentifier("testdb.ods_region_2")
                .setUsername("admin")
                .setPassword("85442791")
                .build();

        Properties properties = new Properties();
// 上游是json数据的时候，需要开启以下配置
        properties.setProperty("read_json_by_line", "true");
        properties.setProperty("format", "json");

// 上游是 csv 写入时，需要开启配置
//properties.setProperty("format", "csv");
//properties.setProperty("column_separator", ",");

        DorisExecutionOptions executionOptions = DorisExecutionOptions.builder()
                .setLabelPrefix("label-doris")
                .setDeletable(false)
                //.setBatchMode(true)  开启攒批写入
                .setStreamLoadProp(properties)
                .build();

        builder.setDorisReadOptions(DorisReadOptions.builder().build())
                .setDorisExecutionOptions(executionOptions)
                .setSerializer(new SimpleStringSerializer())
                .setDorisOptions(dorisOptions);
        return builder;
    }


    @Override
    public void invoke(DataChangeInfo value, Context context) throws Exception {
        System.out.println("invoke:" + value);
    }

    @Override
    public void insert(DataChangeInfo value, Context context) throws Exception {
        System.out.println("insert:" +value);
    }

    @Override
    public void update(DataChangeInfo value, Context context) throws Exception {
        System.out.println("update:" +value);
        String afterData = value.getAfterData();
        List<String> data = new ArrayList<>();
        data.add(afterData);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(30000);
        DorisSink.Builder<String> builder = buildOptions();
        env.fromElements(afterData).sinkTo(builder.build());
    }

    @Override
    public void delete(DataChangeInfo value, Context context) throws Exception {
        System.out.println("delete:" +value);
    }

    @Override
    public void handleError(DataChangeInfo value, Context context, Throwable throwable) {

    }
}
