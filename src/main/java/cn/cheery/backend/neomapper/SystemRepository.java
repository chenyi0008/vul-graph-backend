package cn.cheery.backend.neomapper;

import cn.cheery.backend.entity.SystemNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @Description
 * @Date 2025/4/16
 */
@Repository
public interface SystemRepository extends Neo4jRepository<SystemNode, UUID> {
}
