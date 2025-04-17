package cn.cheery.backend.neomapper;

import cn.cheery.backend.entity.Cve;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Date 2025/4/16
 */
@Repository
public interface CveRepository extends Neo4jRepository<Cve, String> {
}
