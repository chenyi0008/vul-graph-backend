package cn.cheery.backend.service;

import cn.cheery.backend.entity.Year;
import cn.cheery.backend.neomapper.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Date 2025/4/17
 */

@Service
public class YearService {

    @Autowired
    private YearRepository yearRepository;

    public Year createYear(Year year) {
        return yearRepository.save(year);
    }

    public Optional<Year> getYear(String yearId) {
        return yearRepository.findById(yearId);
    }

    public Year updateYear(Year year) {
        return yearRepository.save(year);
    }

    public void deleteYear(String yearId) {
        yearRepository.deleteById(yearId);
    }

    public List<Year> getYearList(){
        return yearRepository.findAll();
    }
}
