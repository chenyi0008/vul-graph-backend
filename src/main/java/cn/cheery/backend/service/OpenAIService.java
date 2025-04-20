package cn.cheery.backend.service;

import cn.cheery.backend.common.utils.CommonUtils;
import cn.cheery.backend.config.OpenAIConfig;
import cn.cheery.backend.entity.Cve;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description
 * @Date 2025/4/19
 */

@Service
public class OpenAIService {

    private final OpenAIClient client;

    public OpenAIService(OpenAIConfig config) {
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(config.getApiKey())
                .baseUrl(config.getBaseUrl())
                .build();
    }

    public Cve getCveByAI(String content) throws JsonProcessingException {

        Cve c = new Cve();
        c.generateCveInfo();
        String cveString = CommonUtils.object2Json(c);

        String prompt = String.format(
                "根据这些信息:%s 根据我给的JSON严格提炼我要的信息：%s。" +
                         "提取的内容 请尽量用中文 请你严格按照JSON格式返回，禁止返回其他内容",
                content, cveString
                );

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(prompt)
                .model("qwen-plus")
                .build();

        ChatCompletion chatCompletion = client.chat().completions().create(params);

        String json = chatCompletion.choices().get(0).message().content().orElse("{}");
        return CommonUtils.json2Object(json, Cve.class);
    }


    // 初始化对话上下文（带系统提示词和 CVE 信息）
    public String initChatContext(String question, Cve cve, StringBuilder contextBuilder) throws JsonProcessingException {
        String cveJson = CommonUtils.object2Json(cve);
        contextBuilder.setLength(0); // 清空上下文

        // 系统提示词
        contextBuilder.append("你是一个安全领域的知识专家，请用中文简洁清晰地回答用户提出的问题。禁止使用markdown语法！！禁止使用加粗 你要用聊天的方式和我交流\n");

        // 首次提问 + CVE
        contextBuilder.append("用户：根据这些CVE信息：").append(cveJson).append("，回答这个问题：").append(question).append("\n");

        String prompt = contextBuilder.toString();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("qwen-plus")
                .addUserMessage(prompt)
                .build();

        ChatCompletion chatCompletion = client.chat().completions().create(params);
        String reply = chatCompletion.choices().get(0).message().content().orElse("");

        contextBuilder.append("助手：").append(reply).append("\n");

        return reply;
    }

    // 后续对话
    public String chat(StringBuilder contextBuilder, String newQuestion) {
        contextBuilder.append("用户：").append(newQuestion).append("\n");

        String prompt = contextBuilder.toString();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("qwen-plus")
                .addUserMessage(prompt)
                .build();

        ChatCompletion chatCompletion = client.chat().completions().create(params);
        String reply = chatCompletion.choices().get(0).message().content().orElse("");

        contextBuilder.append("助手：").append(reply).append("\n");

        return reply;
    }

}
