package cn.cheery.backend.service;

import cn.cheery.backend.entity.Cve;
import cn.cheery.backend.entity.Software;
import cn.cheery.backend.entity.SystemNode;
import cn.cheery.backend.neomapper.CveRepository;
import cn.cheery.backend.neomapper.SoftwareRepository;
import cn.cheery.backend.neomapper.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @Description
 * @Date 2025/4/16
 */

@Service
public class CveService {

    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @Autowired
    private SoftwareRepository softwareRepository;

    @Autowired
    private SystemRepository systemRepository;

    public Cve createCve(Cve cve) {
        return cveRepository.save(cve);
    }

    public Optional<Cve> getCve(String cveId) {
        return cveRepository.findById(cveId);
    }

    public Cve updateCve(Cve cve) {
        return cveRepository.save(cve);
    }

    public void deleteCve(String cveId) {
        cveRepository.deleteById(cveId);
    }

    public List<Cve> getCveList(){
        return cveRepository.findAll();
    }

    @Transactional
    public void createCveSoftwareRelationship(String cveId, UUID softwareId) {
        Optional<Cve> cve = cveRepository.findById(cveId);
        Optional<Software> software = softwareRepository.findById(softwareId);

        if (cve.isPresent() && software.isPresent()) {
            Cve cve1 = cve.get();
            Software software1 = software.get();
            cve1.setSoftware(software1);
            neo4jTemplate.save(cve1);
        }
    }


    @Transactional
    public void createCveSystemRelationship(String cveId, UUID systemId) {
        Optional<Cve> cve = cveRepository.findById(cveId);
        Optional<SystemNode> systemNode = systemRepository.findById(systemId);

        if (cve.isPresent() && systemNode.isPresent()) {
            Cve cve1 = cve.get();
            SystemNode systemNode1 = systemNode.get();
            cve1.setSystem(systemNode1);
            neo4jTemplate.save(cve1);
        }
    }

}