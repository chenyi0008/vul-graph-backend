package cn.cheery.backend.entity;

import lombok.Data;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<Software> softwareList;

    @Relationship(type = "可能影响", direction = Relationship.Direction.OUTGOING)
    private List<SystemNode> systemList;

    @Relationship(type = "报告", direction = Relationship.Direction.INCOMING)
    private Country country;

    @Relationship(type = "报告于", direction = Relationship.Direction.INCOMING)
    private Year year;

    public void generateCveInfo(){
        Cve cve = this;

        // 填充模拟数据
        cve.setCveId("CVE-2025-0001");  // CVE 标识符
        cve.setStatus("PUBLISHED");  // CVE 状态
        cve.setDescription("Abacus ERP is versions older than 2024.210.16036, 2023.205.15833, 2022.105.15542 are affected by an authenticated arbitrary file read vulnerability");
        cve.setCvssScore(6.5);  // CVSS评分
        cve.setReferenceLink("https://borelenzo.github.io/stuff/2025/02/15/CVE-2025-0001.html");
        cve.setAttackMethod("Absolute Path Traversal");  // 攻击手段
        cve.setVulnType("CWE-22");  // 漏洞类型
        cve.setAffectedScope("All versions prior to 2024.210.16036");  // 影响范围
        cve.setAttackVector("LOCAL");  // 攻击向量
        cve.setImpact("C:L/I:L/A:N");  // 影响
        cve.setSolution("Apply the latest patch available from the vendor.");  // 修复方案
        cve.setPublishTime(ZonedDateTime.parse("2025-02-15T09:30:00Z"));  // 发布时间
        cve.setYear(new Year("2025"));

        // 填充影响的软件列表
        Software software1 = new Software();
        software1.setName("Abacus ERP 如果没有影响的软件 可以直接设置softwareList为null");
        software1.setVendor("Abacus Solutions");
        software1.setType("ERP");
        software1.setAffectedVersions(Arrays.asList("2024.210.16036", "2023.205.15833"));

        Software software2 = new Software();
        software2.setName("Abacus ERP");
        software2.setVendor("Abacus Solutions");
        software2.setType("ERP");
        software2.setAffectedVersions(Arrays.asList("2022.105.15542"));

        List<Software> softwareList = new ArrayList<>();
        softwareList.add(software1);
        softwareList.add(software2);
        cve.setSoftwareList(softwareList);

        // 填充可能影响的系统列表
        SystemNode system1 = new SystemNode();
        system1.setSystemName("Linux 如果没有影响的系统 可以直接设置systemList为null");
        system1.setLevel("High");
        system1.setVendor("Linux Foundation");

        SystemNode system2 = new SystemNode();
        system2.setSystemName("Windows");
        system2.setLevel("Medium");
        system2.setVendor("Microsoft");

        List<SystemNode> systemList = new ArrayList<>();
        systemList.add(system1);
        systemList.add(system2);
        cve.setSystemList(systemList);

        // 填充报告国家
        Country country = new Country();
        country.setNameEn("Germany 如果没有国家 可以直接设置country为null");
        country.setNameZh("德国");
        cve.setCountry(country);
    }
}