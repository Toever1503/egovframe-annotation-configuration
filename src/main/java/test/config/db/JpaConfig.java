package test.config.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.management.RuntimeErrorException;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@EnableJpaRepositories(basePackages = "test.repositories")
@EnableTransactionManagement
@PropertySource({ "classpath:application.properties" })
public class JpaConfig {
	@Autowired
	private Environment env;

	public JpaConfig() {
		super();
		System.out.println("JpaConfiguration created");
	}
	
	@Bean
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public PageableProxy pageableProxy(@RequestParam(name="page", required = false) Integer page, @RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name="sort", required = false) String sort) {
		System.out.println("creating PageableProxy");
		List<Order> colOrders = new ArrayList<Sort.Order>();

		if (page == null)
			page = 0;
		else if (page < 0)
			page = 0;
		
		if (size == null || size < 0)
			size = 20;

		if (sort != null) {
			String[] sortList = sort.split(";");
			for (String item : sortList) {
				String[] sortCol = item.split(",");
				if (sortCol.length != 2)
					throw new RuntimeException("Sort param is invalid");
				try {
					colOrders.add(new Order(Sort.Direction.fromString(sortCol[1]), sortCol[0]));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					throw new RuntimeException(
							"Sort direction is invalid: column: " + sortCol[0] + ", direction: " + sortCol[1]);
				}
			}
		}
		Pageable pageable = PageRequest.of(page, size, Sort.by(colOrders));
		System.out.println("create page proxy completed: " + pageable);
		
		return new PageableProxy(pageable);
	}

//	@Bean("pageCustom")
//	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//	@RequestScope
//	@Primary
	public Pageable pageable(@RequestParam(defaultValue = "0", required = false) int page,
			@RequestParam(defaultValue = "20", required = false) int size,
			@RequestParam(defaultValue = "", required = false) String sort) {
		System.out.println("creating pageable");
		List<Order> colOrders = new ArrayList<Sort.Order>();
		if (sort != null) {
			String[] sortList = sort.split(";");
			for (String item : sortList) {
				String[] sortCol = item.split(",");
				if (sortCol.length != 2)
					throw new RuntimeException("Sort param is invalid");
				try {
					colOrders.add(new Order(Sort.Direction.fromString(sortCol[1]), sortCol[0]));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					throw new RuntimeException("Sort direction is invalid: column: "+ sortCol[0] + ", direction: " + sortCol[1]);
				}
			}
		}
		return PageRequest.of(page, size).withSort(Sort.by(colOrders));
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		System.out.println("creating emf");
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPackagesToScan(new String[] { "test.entities" });

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setJpaProperties(additionalProperties());

		return entityManagerFactoryBean;
	}

	final Properties additionalProperties() {
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		hibernateProperties.setProperty("hibernate.cache.use_second_level_cache",
				env.getProperty("hibernate.cache.use_second_level_cache"));
		hibernateProperties.setProperty("hibernate.cache.use_query_cache",
				env.getProperty("hibernate.cache.use_query_cache"));
		// hibernateProperties.setProperty("hibernate.globally_quoted_identifiers",
		// "true");
		return hibernateProperties;
	}

	@Bean
	public DataSource dataSource() {
		System.out.println("creating dataSource");
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.pass"));
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
