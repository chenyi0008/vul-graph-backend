package cn.cheery.backend.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Year")
@Data
public class Year {

    @Id
    @Property("名称")
    private String yearName;
}
