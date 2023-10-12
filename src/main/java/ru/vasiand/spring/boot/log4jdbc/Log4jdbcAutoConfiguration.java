package ru.vasiand.spring.boot.log4jdbc;

import net.sf.log4jdbc.sql.jdbcapi.DataSourceSpy;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for log4jdbc.
 *
 */
@AutoConfiguration
@ConditionalOnClass(DataSourceSpy.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class Log4jdbcAutoConfiguration {
	@Bean
	public static Log4jdbcBeanPostProcessor log4jdbcBeanPostProcessor() {
		return new Log4jdbcBeanPostProcessor();
	}
}
