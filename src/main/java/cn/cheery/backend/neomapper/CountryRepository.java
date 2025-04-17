package cn.cheery.backend.neomapper;

import cn.cheery.backend.entity.Country;
import cn.cheery.backend.entity.Year;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Date 2025/4/16
 */
@Repository
public interface CountryRepository extends Neo4jRepository<Country, String> {
}
