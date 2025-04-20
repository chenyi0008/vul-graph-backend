package cn.cheery.backend.entity.dto;

/**
 * @Description
 * @Date 2025/4/20
 */
public class ChatMessage {
    private String role;     // "user" / "assistant" / "system"
    private String content;  // 消息内容

    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public static ChatMessage ofUser(String content) {
        return new ChatMessage("user", content);
    }

    public static ChatMessage ofAssistant(String content) {
        return new ChatMessage("assistant", content);
    }

    public static ChatMessage ofSystem(String content) {
        return new ChatMessage("system", content);
    }
}