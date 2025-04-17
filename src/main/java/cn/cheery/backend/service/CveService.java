package cn.cheery.backend.service;

import cn.cheery.backend.entity.Cve;
import cn.cheery.backend.neomapper.CveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Date 2025/4/16
 */

@Service
public class CveService {

    @Autowired
    private CveRepository cveRepository;

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
}