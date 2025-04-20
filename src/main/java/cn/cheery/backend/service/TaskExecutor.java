package cn.cheery.backend.service;

import cn.cheery.backend.entity.Cve;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @Description
 * @Date 2025/4/19
 */

@Service
public class TaskExecutor {

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private CveService cveService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Async("threadPoolTaskExecutor")
    public void parseFile(List<String> list) {

        Path uploadPath = Paths.get(uploadDir);
        for (String fileName : list) {
            Path filePath = uploadPath.resolve(fileName);
            try {
                // 读取文件内容
                String json = Files.readString(filePath);
                // 解析 JSON
                JsonNode root = objectMapper.readTree(json);
                JsonNode cveIdNode = root.path("cveMetadata").path("cveId");
                if (!cveIdNode.isMissingNode()) {
                    System.out.println("文件 [" + fileName + "] 的 CVE ID: " + cveIdNode.asText());
                    Cve cve = openAIService.getCveByAI(json);
                    cveService.createCve(cve);
                } else {
                    System.out.println("文件 [" + fileName + "] 中未找到 cveMetadata.cveId");
                }
            } catch (IOException e) {
                System.err.println("读取文件 [" + fileName + "] 出错: " + e.getMessage());
            }
        }
    }
}
