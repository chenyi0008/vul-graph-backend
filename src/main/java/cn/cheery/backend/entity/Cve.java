package cn.cheery.backend.entity;

import lombok.Data;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.ZonedDateTime;

@Data
@Node("CVE")
public class Cve {

    @Id
    @Property("cveId")
    private String cveId;

    @Property("状态")
    private String status;

    @Property("漏洞描述")
    private String description;

    @Property("CVSS评分")
    private Double cvssScore;

    @Property("参考链接")
    private String referenceLink;

    @Property("攻击手段")
    private String attackMethod;

    @Property("漏洞类型")
    private String vulnType;

    @Property("影响范围")
    private String affectedScope;

    @Property("攻击向量")
    private String attackVector;

    @Property("影响")
    private String impact;

    @Property("修复方案")
    private String solution;

    @Property("发布时间")
    private ZonedDateTime publishTime;

    @Relationship(type = "影响", direction = Relationship.Direction.OUTGOING)
    private Software software;

    @Relationship(type = "可能影响", direction = Relationship.Direction.OUTGOING)
    private SystemNode system;
}