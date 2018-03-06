package com.example.lcdemo.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.example.lcdemo.base.datasource.DruidProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 *
 */
@Configuration                              //这里只能扫描dao层，扫描service层会出错
@MapperScan(basePackages = {"com.example.lcdemo.modular.admin.dao","com.example.lcdemo.modular.backend.dao"})
public class MybatisPlusConfig {
    @Autowired
    DruidProperties druidProperties;
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

//    @Bean
//    public SqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException{
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setCallSettersOnNulls(true);
//        sqlSessionFactoryBean.setConfiguration(configuration);
//        return sqlSessionFactoryBean;
//    }
}
