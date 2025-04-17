package cn.cheery.backend.service;

import cn.cheery.backend.entity.Software;
import cn.cheery.backend.neomapper.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Date 2025/4/16
 */

@Service
public class SoftwareService {

    @Autowired
    private SoftwareRepository softwareRepository;

    public Software createSoftware(Software software) {
        return softwareRepository.save(software);
    }

    public Optional<Software> getSoftware(String softwareId) {
        return softwareRepository.findById(softwareId);
    }

    public Software updateSoftware(Software software) {
        return softwareRepository.save(software);
    }

    public void deleteSoftware(String softwareId) {
        softwareRepository.deleteById(softwareId);
    }

    public List<Software> getSoftwareList(){
        return softwareRepository.findAll();
    }
}