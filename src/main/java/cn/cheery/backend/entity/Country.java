package cn.cheery.backend.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Country")
@Data
public class Country {

    @Id
    private String id;

    @Property("中文名")
    private String nameZh;

    @Property("英文名")
    private String nameEn;
}
