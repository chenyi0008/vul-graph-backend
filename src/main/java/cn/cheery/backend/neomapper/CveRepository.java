package cn.cheery.backend.neomapper;

import cn.cheery.backend.entity.Cve;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description
 * @Date 2025/4/16
 */
@Repository
public interface CveRepository extends Neo4jRepository<Cve, String> {

    @Query("MATCH (c:CVE)-[r:影响]->(s:Software) WHERE c.cveId = $cveId DELETE r")
    void deleteSoftwareRelationshipsByCveId(String cveId);

    @Query("MATCH (c:CVE)-[r:可能影响]->(s:System) WHERE c.cveId = $cveId DELETE r")
    void deleteSystemRelationshipsByCveId(String cveId);

    @Query("MATCH (c:Country)-[r:报告]->(cve:CVE) WHERE cve.cveId = $cveId  DELETE r")
    void deleteCountryRelationById(String cveId);

    @Query("MATCH (c:CVE) WHERE c.`CVSS评分` >= $min AND c.`CVSS评分` <= $max AND c.`漏洞类型` CONTAINS $type RETURN c")
    List<Cve> findByCvssScoreBetween(@Param("min") double min, @Param("max") double max, String type);


}
