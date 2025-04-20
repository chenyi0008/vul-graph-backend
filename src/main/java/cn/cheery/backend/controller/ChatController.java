package cn.cheery.backend.controller;

import cn.cheery.backend.common.response.ApiResponse;
import cn.cheery.backend.entity.Cve;
import cn.cheery.backend.entity.dto.ChatCveVo;
import cn.cheery.backend.service.CveService;
import cn.cheery.backend.service.OpenAIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author chenyi0008
 * @Date 2025/4/20
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private CveService cveService;

    private final Map<String, StringBuilder> sessionMap = new ConcurrentHashMap<>(); // 用户对话上下文

    public ChatController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping()
    public ApiResponse chat(@RequestBody ChatCveVo vo) throws JsonProcessingException {
        StringBuilder context = sessionMap.get(vo.getSessionId());

        // 如果是第一次发起对话（带有 cveId），表示初始化对话
        if (context == null) {
            context = new StringBuilder();
            sessionMap.put(vo.getSessionId(), context);

            if (vo.getCveId() == null) {
                return ApiResponse.fail("初始化对话必须提供 cveId");
            }

            Optional<Cve> cve = cveService.getCve(vo.getCveId());
            if (cve.isEmpty()) {
                return ApiResponse.fail("获取CVE信息失败");
            }

            String reply = openAIService.initChatContext(vo.getQuestion(), cve.get(), context);
            return ApiResponse.success(reply);
        }

        // 如果上下文已存在，表示继续对话
        String reply = openAIService.chat(context, vo.getQuestion());
        return ApiResponse.success(reply);
    }
}
