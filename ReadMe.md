## 必要なもの
- JDK 11
- Maven 3.8.6
- Kafka 2.0

## snmptrap connectorのビルド
1. Snmptrap Connectorのソースコードをクローンします。

    ```
    $ git clone https://github.com/keijijin/snmptrap-connector.git
    ```

2. クローンしたディレクトリに移動し、`mvn`コマンドを使用してプロジェクトをビルドします。

    ```
    $ cd snmptrap-connector
    $ mvn clean package
    ```
   
3. 成功すれば、`target`ディレクトリに`snmptrap-connector-1.0.0-SNAPSHOT-package`ディレクトリが生成されます。

## snmptrap connectorのデプロイ
1. デプロイ先のディレクトリを作成します。
    ```
   $ mkdir /path/to/kafka/connectors
   ```
2. `target`ディレクトリから`snmptrap-connector-1.0.0-SNAPSHOT-package`をコピーして、デプロイ先のディレクトリに配置します。
    ```
   $ cp -r target/snmptrap-connector-1.0.0-SNAPSHOT-package /path/to/kafka/connectors/.
   ```
3. `config`ディレクトリの`SnmptrapSourceConnector.properties`を編集し、kafkaの`config`ディレクトリにコピーします。
   
   - 編集(必要にお応じて修正して下さい)：
    ```
   bootstrap.servers=localhost:9092
   topic=demo-1
   ```
    - コピー：
    ```
   $ cp config/SnmptrapSourceConnector.properties /path/to/kafka/config/.
   ```
4. `config`ディレクトリの`connect-standalone.properties`の`plugin.path=`を編集します
    ```
   plugin.path=/path/to/connectors
   ```

## Kafka Connectの実行
1. 以下のコマンドを実行して、Kafka Connectを起動します。
    ```
   $ cd /path/to/kafka
   $ ./bin/connect-standalone.sh config/connect-standalone.properties config/SnmptrapSourceConnector.properties
   ```
2. 成功すれば、Kafka Connectが起動し、指定されたプロパティに従ってコネクタが実行されます。
    ```
    [2023-04-06 13:02:26,917] INFO Created connector SnmptrapSourceConnector (org.apache.kafka.connect.cli.ConnectStandalone:109)
    [2023-04-06 13:02:26,961] INFO [SnmptrapSourceConnector|task-0] WorkerSourceTask{id=SnmptrapSourceConnector-0} Source task finished initialization and start (org.apache.kafka.connect.runtime.AbstractWorkerSourceTask:271)
   ```

## snmptrap connectorのテスト
- 以下のスクリプトを実行し、snmptrapをsnmp.host:snmp.port宛に送信しましょう。
   ```
  $ sudo ./scripts/send-snmp.sh 127.0.0.1 333 999 "Hello World"
  ```
- bootstrap.serverのtopicにメッセージが送られていれば成功です。
   ```
  Received message from Kafka: {"schema":null,"payload":"fromHost[127.0.0.1], V1TRAP[reqestID=0,timestamp=1:54:38.52,enterprise=1.3.6.1.4.1.8072.9999.0.2.35,genericTrap=0,specificTrap=333, VBS[1.3.6.1.4.1.8072.9999.0.2.35.0.333.1 = 999; 1.3.6.1.4.1.8072.9999.0.2.35.0.333.2 = Hello World]]"}
  ```