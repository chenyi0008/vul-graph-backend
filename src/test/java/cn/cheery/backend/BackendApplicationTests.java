package cn.cheery.backend;

import cn.cheery.backend.entity.Cve;
import cn.cheery.backend.service.CveService;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.fasterxml.jackson.core.type.TypeReference;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.modelcontextprotocol.spec.McpTransport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

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


    @Test
    void springMcpTest() {


    }

}
