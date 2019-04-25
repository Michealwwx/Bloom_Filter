package com.reign.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.sql.DriverManager;


/**
 * @ClassName AppConfig
 * @Description 构造mybatis
 * @Author wuwenxiang
 * @Date 2019-04-25 20:48
 * @Version 1.0
 **/
@Configuration
@ComponentScan("com.reign")
@MapperScan("com.reign.mapper")
public class AppConfig {

    @Bean
    @Autowired
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }
    @Bean
    public DataSource source(){
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("123456");
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=UTF-8");
        return driverManagerDataSource;
    }
    /**
     *redis连接；
     */

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(500);
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setMaxWaitMillis(5000);
        return jedisPoolConfig;
    }
    @Bean
    @Autowired
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig){
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"192.168.241.128",6379);
        return jedisPool;
    }


}
