package cn.cheery.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class WebConfig {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);  // 打印查询参数
        filter.setIncludePayload(true);      // 打印请求体内容
        filter.setMaxPayloadLength(10000);   // 设置打印最大长度
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }
}
