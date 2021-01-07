package vn.metech.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;
import vn.metech.kafka.config.KafkaProperties;
import vn.metech.kafka.config.KafkaTopic;

import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
public class KafkaListener {

	private ConsumerFactory<String, String> consumerFactory;
	private KafkaProperties kafkaProperties;

	public KafkaListener(
					KafkaProperties kafkaProperties,
					ConsumerFactory<String, String> consumerFactory) {
		this.consumerFactory = consumerFactory;
		this.kafkaProperties = kafkaProperties;
	}

	public void listenerMessages(
					String topic, String group,
					Consumer<ConsumerRecords<String, String>> onListener) {

		Executors.newFixedThreadPool(1).execute(
						new Thread(new KafkaListenerRunnable(
										consumerFactory, topic, group,
										onListener, kafkaProperties.getFetchInterval())
						));
	}

	public void listenerMessages(
					KafkaTopic kafkaTopic,
					Consumer<ConsumerRecords<String, String>> onListener) {

		listenerMessages(
						kafkaTopic.getTopics(),
						kafkaTopic.getGroupId(),
						onListener
		);
	}
}
