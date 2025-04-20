package cn.cheery.backend.entity.dto;

/**
 * @Description
 * @Author chenyi0008
 * @Date 2025/4/20
 */
public class ChatCveVo {

    private String sessionId;

    private String question;

    private String cveId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCveId() {
        return cveId;
    }

    public void setCveId(String cveId) {
        this.cveId = cveId;
    }
}
