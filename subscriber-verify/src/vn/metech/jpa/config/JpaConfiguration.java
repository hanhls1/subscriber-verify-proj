package vn.metech.jpa.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties(value = {
				DatasourceProperties.class,
				JpaProperties.class
})
public class JpaConfiguration {

	private DatasourceProperties datasourceProperties;
	private JpaProperties jpaProperties;

	public JpaConfiguration(
					DatasourceProperties datasourceProperties,
					JpaProperties jpaProperties) {
		this.datasourceProperties = datasourceProperties;
		this.jpaProperties = jpaProperties;
	}

	@Bean("hibernateProperties")
	public Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", jpaProperties.getDialect());
		properties.put("hibernate.hbm2ddl.auto", jpaProperties.getDdlAuto());
		properties.put("current_session_context_class", jpaProperties.getCurrentSessionContextClass());
		properties.put("hibernate.show_sql", jpaProperties.getShowSql());
		properties.put("hibernate.jdbc.batch_size", jpaProperties.getBatchSize());
		properties.put("hibernate.generate_statistics", jpaProperties.getGenerateStatistics());
		properties.put("hibernate.order_inserts", jpaProperties.getBatchInsert());
		properties.put("hibernate.order_updates", jpaProperties.getBatchUpdate());
		properties.put("hibernate.default_schema", jpaProperties.getSchema());
		try {
			System.out.println(new ObjectMapper().writeValueAsString(properties));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}


		return properties;
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(datasourceProperties.getDriverClassName());
		dataSource.setUrl(datasourceProperties.getUrl());
		dataSource.setUsername(datasourceProperties.getUsername());
		dataSource.setPassword(datasourceProperties.getPassword());

		return dataSource;
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		factoryBean.setJpaProperties(hibernateProperties());
		factoryBean.setDataSource(dataSource);
		factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factoryBean.setPackagesToScan(jpaProperties.getEntityPackageScan());

		return factoryBean;
	}

	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}
}
