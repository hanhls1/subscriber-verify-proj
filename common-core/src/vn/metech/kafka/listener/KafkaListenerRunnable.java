package vn.metech.kafka.listener;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.time.Duration;
import java.util.Arrays;


public class KafkaListenerRunnable implements Runnable {

	private final String serviceName = "KafkaListenerRunnable";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Consumer<String, String> consumer;
	private String topics;
	private String groupId;
	private java.util.function.Consumer<ConsumerRecords<String, String>> onListener;
	private int intervalTime;

	public KafkaListenerRunnable(
					ConsumerFactory<String, String> consumerFactory,
					String topics,
					String groupId,
					java.util.function.Consumer<ConsumerRecords<String, String>> onListener,
					int intervalTime) {
		this.topics = topics;
		this.groupId = groupId;
		this.onListener = onListener;
		this.intervalTime = intervalTime;

		this.consumer = consumerFactory.createConsumer(groupId, null);
	}

	public KafkaListenerRunnable(
					ConsumerFactory<String, String> consumerFactory,
					String topics,
					String groupId,
					java.util.function.Consumer<ConsumerRecords<String, String>> onListener) {
		this(consumerFactory, topics, groupId, onListener, 1500);
	}

	@Override
	public void run() {
		consumer.subscribe(Arrays.asList(topics.split(",")));

		logger.info(serviceName +
						" > run() > listening topics: " + topics +
						" | groupId: " + groupId);

		while (intervalTime > 0) {
			try {
				ConsumerRecords<String, String> msgConsumer
								= consumer.poll(Duration.ofHours(1));

				onListener.accept(msgConsumer);

				consumer.commitAsync();

				sleep();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void sleep() {
		try {
			Thread.sleep(intervalTime);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
