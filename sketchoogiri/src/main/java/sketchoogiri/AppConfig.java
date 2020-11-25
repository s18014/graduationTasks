package sketchoogiri;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
// @ComponentScan("com.example.domain")
@ComponentScan("sketchoogiri.domain")
@EnableTransactionManagement
// @MapperScan("com.example.domain.mapper")
@MapperScan("sketchoogiri.domain.mapper")
public class AppConfig {
	
	// 多分データソースはapplication.propatiesのdatasourceをDIしてくれる
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setConfigLocation(
				new ClassPathResource("/mybatis-config.xml"));
		return sessionFactory.getObject();
	}
}