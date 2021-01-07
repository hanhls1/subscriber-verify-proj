package vn.metech.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

	private String bootstrapServer;
	private int fetchInterval;
	private String enableAutoCommit;
	private String maxPoll;

	public KafkaProperties() {
		this.bootstrapServer = "localhost:9092";
		this.fetchInterval = 1000;
		this.enableAutoCommit = "true";
		this.maxPoll = "32";
	}

	public String getBootstrapServer() {
		return bootstrapServer;
	}

	public void setBootstrapServer(String bootstrapServer) {
		this.bootstrapServer = bootstrapServer;
	}

	public int getFetchInterval() {
		return fetchInterval;
	}

	public void setFetchInterval(int fetchInterval) {
		this.fetchInterval = fetchInterval;
	}

	public String getEnableAutoCommit() {
		return enableAutoCommit;
	}

	public void setEnableAutoCommit(String enableAutoCommit) {
		this.enableAutoCommit = enableAutoCommit;
	}

	public String getMaxPoll() {
		return maxPoll;
	}

	public void setMaxPoll(String maxPoll) {
		this.maxPoll = maxPoll;
	}
}

