package com.example.kafka;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.*;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SnmptrapSourceTask extends SourceTask {

    private Logger log = LoggerFactory.getLogger(SnmptrapSourceTask.class);

    private String topic;
    private List<SourceRecord> records = new ArrayList<SourceRecord>();
    private Snmp snmp;

    public String version() {
        return "1.0.0";
    }

    public void start(Map<String, String> props) {
        log.info("Configs: {}", props.toString());

        topic = props.get("topic");

        String snmpHost = props.get("snmp.host");
        String snmpPort = props.get("snmp.port");
        String snmpCommunity = props.get("snmp.community");

        try {
            UdpAddress address = new UdpAddress(snmpHost + "/" + snmpPort);
            TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping(address);
            snmp = new Snmp(transport);

            USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3.createLocalEngineID()), 0);
            SecurityModels.getInstance().addSecurityModel(usm);

            snmp.listen();

            snmp.addCommandResponder(new CommandResponder() {
                public void processPdu(CommandResponderEvent event) {
                    PDU pdu = event.getPDU();
                    if (pdu != null) {
                        String fromAddress = event.getPeerAddress().toString();
                        String[] parts = fromAddress.split("/");
                        String fromHost = parts[0];
                        String messageString = "fromHost[" + fromHost + "], " + pdu.toString();
                        log.debug("Received PDU: {}", messageString);
                        synchronized (records) {
                            SourceRecord record = new SourceRecord(null, null, topic, null, null, null, messageString);
                            records.add(record);
                            records.notifyAll();
                        }
                    }
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SourceRecord> poll() throws InterruptedException {
        synchronized (records) {
            while (records.isEmpty()) {
                records.wait(1000);
            }
            List<SourceRecord> polledRecords = new ArrayList<>(records);
            records.clear();
            return polledRecords;
        }
    }

    public void stop() {
        try {
            snmp.close();
            synchronized (records) {
                records.notifyAll();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
