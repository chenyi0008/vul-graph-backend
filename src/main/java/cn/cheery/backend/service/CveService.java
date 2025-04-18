package cn.cheery.backend.service;

import cn.cheery.backend.entity.Country;
import cn.cheery.backend.entity.Cve;
import cn.cheery.backend.entity.Software;
import cn.cheery.backend.entity.SystemNode;
import cn.cheery.backend.neomapper.CountryRepository;
import cn.cheery.backend.neomapper.CveRepository;
import cn.cheery.backend.neomapper.SoftwareRepository;
import cn.cheery.backend.neomapper.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Autowired
    private CountryRepository countryRepository;

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

    public List<Cve> getCveListBetween(double min, double max, String type){
        return cveRepository.findByCvssScoreBetween(min, max, type);
    }

    @Transactional
    public Optional<Object> createCveSoftwareRelationship(String cveId, List<UUID> softwareIds) {
        Optional<Cve> optionalCve = cveRepository.findById(cveId);

        if (optionalCve.isPresent()) {
            Cve cve = optionalCve.get();
            List<Software> currentList =  new ArrayList<>();
            for (UUID softwareId : softwareIds) {
                softwareRepository.findById(softwareId).ifPresent(currentList::add);
            }
            cve.setSoftwareList(currentList);
            cveRepository.deleteSoftwareRelationshipsByCveId(cveId);
            Cve save = neo4jTemplate.save(cve);
            return Optional.of(save);
        }
        return Optional.empty();
    }


    @Transactional
    public Optional<Cve> createCveSystemRelationship(String cveId, List<UUID> systemIds) {
        Optional<Cve> optionalCve = cveRepository.findById(cveId);

        List<SystemNode> systems = StreamSupport
                .stream(systemRepository.findAllById(systemIds).spliterator(), false)
                .collect(Collectors.toList());

        Cve cve = optionalCve.get();
        cve.setSystemList(systems);
        cveRepository.deleteSystemRelationshipsByCveId(cveId);
        Cve saved = neo4jTemplate.save(cve);
        return Optional.of(saved);
    }

    @Transactional
    public Optional<Cve> createCveCountryRelationship(String cveId, String countryId) {
        Optional<Cve> optionalCve = cveRepository.findById(cveId);
        Optional<Country> optionalCountry = countryRepository.findById(countryId);

        if (optionalCve.isPresent() && optionalCountry.isPresent()) {
            Cve cve = optionalCve.get();
            cve.setCountry(optionalCountry.get());
            cveRepository.deleteCountryRelationById(cveId);
            Cve save = cveRepository.save(cve);
            return Optional.of(save);
        }
        return Optional.empty();
    }
}