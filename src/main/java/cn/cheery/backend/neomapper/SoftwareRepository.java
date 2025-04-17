package cn.cheery.backend.neomapper;

import cn.cheery.backend.entity.Cve;
import cn.cheery.backend.entity.Software;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @Description
 * @Date 2025/4/16
 */
@Repository
public interface SoftwareRepository extends Neo4jRepository<Software, UUID> {
}
