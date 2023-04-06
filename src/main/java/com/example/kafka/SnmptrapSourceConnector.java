package com.example.kafka;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SnmptrapSourceConnector extends SourceConnector {
    private static Logger log = LoggerFactory.getLogger(SnmptrapSourceConnector.class);
    private SnmptrapSourceConnectorConfig config;
    
    @Override
    public void start(Map<String, String> map) {
        config = new SnmptrapSourceConnectorConfig(map);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return SnmptrapSourceTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int i) {
        ArrayList<Map<String, String>> configs = new ArrayList<>(1);
        configs.add(config.originalsStrings());
        return configs;
    }

    @Override
    public void stop() {

    }

    @Override
    public ConfigDef config() {
        return SnmptrapSourceConnectorConfig.conf();
    }

    @Override
    public String version() {
        return "1.0.0";
    }
}
