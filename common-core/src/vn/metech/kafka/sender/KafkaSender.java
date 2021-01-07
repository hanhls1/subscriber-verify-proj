package vn.metech.kafka.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import vn.metech.kafka.config.KafkaProperties;
import vn.metech.kafka.config.KafkaTopic;
import vn.metech.util.JsonUtils;

import java.util.function.Consumer;

@Component
public class KafkaSender {

	private final String serviceName = "KafkaSender";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public KafkaSender(
					KafkaProperties kafkaProperties,
					KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaProperties = kafkaProperties;
		this.kafkaTemplate = kafkaTemplate;
	}

	private KafkaProperties kafkaProperties;
	private KafkaTemplate<String, String> kafkaTemplate;

	public void send(String topic, Object objMsg,
	                 Consumer<SendResult<?, ?>> successConsumer,
	                 Consumer<Throwable> errorConsumer) {

		String msg = JsonUtils.toJson(objMsg);
		send(topic, msg, successConsumer, errorConsumer);
	}

	public void send(String topic, String key, Object objMsg,
	                 Consumer<SendResult<?, ?>> successConsumer,
	                 Consumer<Throwable> errorConsumer) {

		String msg = JsonUtils.toJson(objMsg);
		send(topic, key, msg, successConsumer, errorConsumer);
	}

	public void send(String topic, String msg,
	                 Consumer<SendResult<?, ?>> successConsumer,
	                 Consumer<Throwable> errorConsumer) {
		send(topic, null, msg, successConsumer, errorConsumer);
	}

	public void send(String topic, Object msg) {
		send(topic, msg,
						sendResult -> logger.info(serviceName + " > pushMsg() > msg sent to: " + topic),
						throwable -> logger.info(serviceName + " > pushMsg() > error msg: " + throwable.getMessage())
		);
	}

	public void send(String topic, String key, String msg,
	                 Consumer<SendResult<?, ?>> successConsumer,
	                 Consumer<Throwable> errorConsumer) {
		ListenableFuture<SendResult<String, String>>
						msgSendResult = kafkaTemplate.send(topic, key, msg);

		msgSendResult.addCallback(new ListenableFutureCallback<SendResult<?, ?>>() {
			@Override
			public void onFailure(Throwable ex) {
				if (errorConsumer != null) {
					errorConsumer.accept(ex);
				}
			}

			@Override
			public void onSuccess(SendResult<?, ?> sendResult) {
				if (successConsumer != null) {
					successConsumer.accept(sendResult);
				}
			}
		});
	}

	public void send(
					KafkaTopic topic, String key, String msg,
					Consumer<SendResult<?, ?>> success, Consumer<Throwable> error) {
		send(topic.getTopics(), key, msg, success, error);
	}

	public void send(
					KafkaTopic topic, String key, Object msg,
					Consumer<SendResult<?, ?>> success, Consumer<Throwable> error) {

		String json = JsonUtils.toJson(msg);

		send(topic.getTopics(), key, json, success, error);
	}

	public void send(
					KafkaTopic topic, String key,
					Object msg, Consumer<SendResult<?, ?>> success) {
		send(topic, key, msg, success,
						throwable -> logger.warn(
										serviceName + " > send() > error: " + throwable.getMessage())
		);
	}

	public void send(
					KafkaTopic topic, Object msg,
					Consumer<SendResult<?, ?>> success, Consumer<Throwable> error) {
		send(topic, null, msg, success, error);
	}

	public void send(
					KafkaTopic topic, Object msg,
					Consumer<SendResult<?, ?>> success) {
		send(topic, null, msg, success, throwable -> {
			logger.warn(serviceName + " > send() > error: " + throwable.getMessage());
		});
	}

	public void send(
					String topic, Object msg,
					Consumer<SendResult<?, ?>> success) {
		send(topic, msg, success, throwable -> {
			logger.warn(serviceName + " > send() > error: " + throwable.getMessage());
		});
	}
}
