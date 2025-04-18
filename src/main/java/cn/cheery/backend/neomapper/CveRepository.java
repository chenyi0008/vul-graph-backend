package cn.cheery.backend.neomapper;

import cn.cheery.backend.entity.Cve;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query(" MATCH (c:Country)-[r:报告]->(cve:CVE) WHERE id(cve) = $cveId DELETE r")
    void deleteCountryRelationById(@Param("cveId") Long cveId);

}
