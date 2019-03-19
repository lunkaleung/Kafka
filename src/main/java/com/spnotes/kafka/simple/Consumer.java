package com.spnotes.kafka.simple;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

import com.spnotes.kafka.commons.*;

public class Consumer {
    private static Scanner in;

    private static String bootstrap_servers = ConfProp.getInstance().getProperty("BOOTSTRAP_SERVERS");
    private static String key_serializer_class = ConfProp.getInstance().getProperty("KEY_SERIALIZER_CLASS");
    private static String value_serializer_class = ConfProp.getInstance().getProperty("VALUE_SERIALIZER_CLASS");

    public static void main(String[] argv)throws Exception {
        if (argv.length != 2) {
            System.err.printf("Usage: %s <topicName> <groupId>\n", Consumer.class.getSimpleName());
            System.exit(-1);
        }

        in = new Scanner(System.in);
        
        String topicName = argv[0];
        String groupId = argv[1];

        ConsumerThread consumerRunnable = new ConsumerThread(topicName, groupId);
        consumerRunnable.start();
        String line = "";
        
        while(!line.equals("exit")) {
            line = in.next();
        }

        consumerRunnable.getKafkaConsumer().wakeup();
        System.out.println("Stopping consumer .....");
        consumerRunnable.join();
    }

    private static class ConsumerThread extends Thread {
        private String topicName;
        private String groupId;
        private KafkaConsumer<String,String> kafkaConsumer;

        public ConsumerThread(String topicName, String groupId) {
            this.topicName = topicName;
            this.groupId = groupId;
        }

        public void run() {
            Properties configProperties = new Properties();
            configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
            configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, key_serializer_class);
            configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, value_serializer_class);
            configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");

            //Figure out where to start processing messages from
            kafkaConsumer = new KafkaConsumer<String, String>(configProperties);
            kafkaConsumer.subscribe(Arrays.asList(topicName));
            //Start processing messages
            try {
                while(true) {
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

                    for(ConsumerRecord<String, String> record : records) {
                        System.out.println(record.value());
                    }
                }
            } catch(WakeupException ex) {
                System.out.println("Exception caught " + ex.getMessage());
            } finally {
                kafkaConsumer.close();
                System.out.println("After closing KafkaConsumer");
            }
        }

        public KafkaConsumer<String, String> getKafkaConsumer() {
           return this.kafkaConsumer;
        }
    }
}

