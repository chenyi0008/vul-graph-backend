package cn.cheery.backend.service;

import cn.cheery.backend.entity.Software;
import cn.cheery.backend.neomapper.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<Software> getSoftware(UUID softwareId) {
        return softwareRepository.findById(softwareId);
    }

    public Software updateSoftware(Software software) {
        return softwareRepository.save(software);
    }

    public void deleteSoftware(UUID softwareId) {
        softwareRepository.deleteById(softwareId);
    }

    public List<Software> getSoftwareList(){
        return softwareRepository.findAll();
    }

    public List<Software> getSoftwareByName(String name){
        return softwareRepository.findByName(name);
    }
}