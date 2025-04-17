package cn.cheery.backend.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.UUID;

@Node("System")
@Data
public class SystemNode {

    @Id
    @GeneratedValue
    private UUID id; // 改为 UUID 类型并自动生成

    @Property("系统名称")
    private String systemName;

    @Property("等级")
    private String level;

    @Property("厂商")
    private String vendor;
}
