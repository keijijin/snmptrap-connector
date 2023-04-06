package com.example.kafka;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class SnmptrapSourceConnectorConfig extends AbstractConfig {

    public static final String BOOTSTRAP_SERVERS_CONFIG = "bootstrap.servers";
    public static final String BOOTSTRAP_SERVERS_DOC = "Target bootstrap servers";

    public static final String TOPIC_CONFIG = "topic";
    public static final String TOPIC_DOC = "Topic to write to";
    
    public static final String SNMP_HOST_CONFIG = "snmp.host";
    public static final String SNMP_HOST_DOC = "Snmp host which receive snmptrap";
    
    public static final String SNMP_PORT_CONFIG = "snmp.port";
    public static final String SNMP_PORT_DOC = "Snmp port which receive snmptrap";

    public static final String SNMP_COMMUNITY_CONFIG = "snmp.community";
    public static final String SNMP_COMMUNITY_DOC = "Snmp community string (e.g. public)";

    public static final String KEY_SERIALIZER_CLASS_CONFIG = "key.serializer.class";
    public static final String KEY_SERIALIZER_CLASS_DOC = "key serializer class";

    public static final String VALUE_SERIALIZER_CLASS_CONFIG = "value.serializer.class";
    public static final String VALUE_SERIALIZER_CLASS_DOC = "value serializer class";


    public SnmptrapSourceConnectorConfig(ConfigDef config, Map<String, String> parsedConfig) {
        super(config, parsedConfig);
    }

    public SnmptrapSourceConnectorConfig(Map<String, String> parsedConfig) {
        super(conf(), parsedConfig);
    }

    public static ConfigDef conf() {
        return new ConfigDef()
                .define(BOOTSTRAP_SERVERS_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, BOOTSTRAP_SERVERS_DOC)
                .define(TOPIC_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, TOPIC_DOC)
                .define(SNMP_HOST_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, SNMP_HOST_DOC)
                .define(SNMP_PORT_CONFIG, ConfigDef.Type.INT, 162, ConfigDef.Importance.HIGH, SNMP_PORT_DOC)
                .define(SNMP_COMMUNITY_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, SNMP_COMMUNITY_DOC)
                .define(KEY_SERIALIZER_CLASS_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, KEY_SERIALIZER_CLASS_DOC)
                .define(VALUE_SERIALIZER_CLASS_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, VALUE_SERIALIZER_CLASS_DOC)
                ;
    }

    public String getBootstrapServersConfig() {
        return this.getString(BOOTSTRAP_SERVERS_CONFIG);
    }

    public String getTopicConfig() {
        return this.getString(TOPIC_CONFIG);
    }

    public String getSnmpHostConfig() {
        return this.getString(SNMP_HOST_CONFIG);
    }

    public Integer getSnmpPortConfig() {
        return this.getInt(SNMP_PORT_CONFIG);
    }

    public String getSnmpCommunityConfig() {
        return this.getString(SNMP_COMMUNITY_CONFIG);
    }

    public String getKeySerializerClassConfig() { return this.getString(KEY_SERIALIZER_CLASS_CONFIG); }

    public String getValueSerializerClassConfig() { return this.getString(VALUE_SERIALIZER_CLASS_CONFIG); }
}
