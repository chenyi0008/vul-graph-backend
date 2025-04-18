package cn.cheery.backend.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Country")
@Data
public class Country {

    @Id
    @Property("英文名")
    private String nameEn;

    @Property("中文名")
    private String nameZh;


}
