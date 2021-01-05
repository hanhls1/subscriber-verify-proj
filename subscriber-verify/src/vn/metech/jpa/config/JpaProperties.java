package vn.metech.jpa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hibernate")
public class JpaProperties {

	private String dialect;
	private String ddlAuto;
	private String entityPackageScan;
	private String currentSessionContextClass;
	private String showSql;
	private String batchSize;
	private String generateStatistics;
	private String batchInsert;
	private String batchUpdate;
	private String schema;

	public JpaProperties() {
		this.ddlAuto = "none";
		this.showSql = "false";
		this.batchInsert = "true";
		this.batchUpdate = "true";
		this.batchSize = "32";
		this.currentSessionContextClass = "org.springframework.orm.hibernate5.SpringSessionContext";
		this.schema = "dbo";
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getDdlAuto() {
		return ddlAuto;
	}

	public void setDdlAuto(String ddlAuto) {
		this.ddlAuto = ddlAuto;
	}

	public String getEntityPackageScan() {
		return entityPackageScan;
	}

	public void setEntityPackageScan(String entityPackageScan) {
		this.entityPackageScan = entityPackageScan;
	}

	public String getCurrentSessionContextClass() {
		return currentSessionContextClass;
	}

	public void setCurrentSessionContextClass(String currentSessionContextClass) {
		this.currentSessionContextClass = currentSessionContextClass;
	}

	public String getShowSql() {
		return showSql;
	}

	public void setShowSql(String showSql) {
		this.showSql = showSql;
	}

	public String getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(String batchSize) {
		this.batchSize = batchSize;
	}

	public String getGenerateStatistics() {
		return generateStatistics;
	}

	public void setGenerateStatistics(String generateStatistics) {
		this.generateStatistics = generateStatistics;
	}

	public String getBatchInsert() {
		return batchInsert;
	}

	public void setBatchInsert(String batchInsert) {
		this.batchInsert = batchInsert;
	}

	public String getBatchUpdate() {
		return batchUpdate;
	}

	public void setBatchUpdate(String batchUpdate) {
		this.batchUpdate = batchUpdate;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
}
