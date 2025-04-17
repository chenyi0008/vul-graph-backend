package cn.cheery.backend.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.List;
import java.util.UUID;

@Data
@Node("Software")
public class Software {

    @Id
    @GeneratedValue
    private UUID id; // 改为 UUID 类型并自动生成

    @Property("名称")
    private String name;

    @Property("厂商")
    private String vendor;

    @Property("类型")
    private String type;

    @Property("受影响版本")
    private List<String> affectedVersions;

}
