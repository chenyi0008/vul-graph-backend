package cn.cheery.backend.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/**
 * @Description
 * @Author chenyi0008
 * @Date 2025/4/20
 */
public class CommonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule()); // 支持 Java8 日期时间
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 禁用时间戳格式
    }

    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String object2Json(Object obj) {
        try{
            String json = mapper.writeValueAsString(obj);
            return json;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
