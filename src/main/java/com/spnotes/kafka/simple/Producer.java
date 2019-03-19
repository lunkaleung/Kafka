package com.spnotes.kafka.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Scanner;
import java.util.Properties;

import com.spnotes.kafka.commons.*;

public class Producer {
    private static Scanner in;
    private static String bootstrap_servers = ConfProp.getInstance().getProperty("BOOTSTRAP_SERVERS");
    private static String key_serializer_class = ConfProp.getInstance().getProperty("KEY_SERIALIZER_CLASS");
    private static String value_serializer_class = ConfProp.getInstance().getProperty("VALUE_SERIALIZER_CLASS");

    public static void main(String[] argv)throws Exception {
        if (argv.length != 1) {
            System.err.println("Please specify 1 parameters ");
            System.exit(-1);
        }

        String topicName = argv[0];
        in = new Scanner(System.in);
        System.out.println("Enter message(type exit to quit)");

        //Configure the Producer
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, key_serializer_class);
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, value_serializer_class);

        org.apache.kafka.clients.producer.Producer producer = new KafkaProducer(configProperties);

        String line = in.nextLine();
        
        while(!line.equals("exit")) {
            //TODO: Make sure to use the ProducerRecord constructor that does not take parition Id
            ProducerRecord<String, String> rec = new ProducerRecord<String, String>(topicName, line);
            producer.send(rec);
            line = in.nextLine();
        }

        in.close();
        producer.close();
    }
}
