package vn.metech.kafka.config;

public class KafkaTopic {

	private String topics;
	private String groupId;

	public KafkaTopic() {
		groupId = "default";
	}

	public KafkaTopic(String topics) {
		this();
		this.topics = topics;
	}

	public KafkaTopic(String topics, String groupId) {
		this(topics);
		this.groupId = groupId;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
