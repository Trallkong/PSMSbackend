/**
 * PsmsBackendApplication.java
 * 物业管理系统后端
 * 框架：Spring Boot
 * 数据库: PostgreSQL
 * entity包 存放实体类
 * repository包 实现数据访问接口 (JPA)
 * service包 存放业务逻辑
 * controller包 存放控制器
 */

package com.trallkong.psmsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PsmsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PsmsBackendApplication.class, args);
    }

}
