package cn.cheery.backend;

import cn.cheery.backend.entity.Cve;
import cn.cheery.backend.service.CveService;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads(){
        System.out.println("123");
    }

    @Value("${spring.datasource.username}")
    String dbUsername;

    @Value("${spring.datasource.password}")
    String dbPassword;

    @Value("${spring.datasource.url}")
    String dbUrl;


    @Test
    void generator(){
        FastAutoGenerator.create(dbUrl, dbUsername, dbPassword)
                .globalConfig(builder -> builder
                        .author("c")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("cn.cheery.backend")
                        .entity("entity")
                        .mapper("CVEDao")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("CVEDao.xml")
                )
                .strategyConfig(builder -> builder
                        .entityBuilder()
                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    @Autowired
    CveService cveService;


    @Test
    void neoTest(){
//        cveService.createCveSoftwareRelationship("CVE-2025-0055", UUID.fromString("f7625f20-3412-458c-b1fb-8d56fa4b548a"));
    }

}
