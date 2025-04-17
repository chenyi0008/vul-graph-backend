package cn.cheery.backend.service;

import cn.cheery.backend.entity.SystemNode;
import cn.cheery.backend.neomapper.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Date 2025/4/17
 */

@Service
public class SystemService {

    @Autowired
    private SystemRepository systemRepository;

    public SystemNode createSystem(SystemNode system) {
        return systemRepository.save(system);
    }

    public Optional<SystemNode> getSystem(String systemId) {
        return systemRepository.findById(systemId);
    }

    public SystemNode updateSystem(SystemNode system) {
        return systemRepository.save(system);
    }

    public void deleteSystem(String systemId) {
        systemRepository.deleteById(systemId);
    }

    public List<SystemNode> getSystemList(){
        return systemRepository.findAll();
    }
}
