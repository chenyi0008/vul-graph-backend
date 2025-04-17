package cn.cheery.backend.service;

import cn.cheery.backend.entity.Country;
import cn.cheery.backend.neomapper.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Date 2025/4/17
 */

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Optional<Country> getCountry(String countryId) {
        return countryRepository.findById(countryId);
    }

    public Country updateCountry(Country country) {
        return countryRepository.save(country);
    }

    public void deleteCountry(String countryId) {
        countryRepository.deleteById(countryId);
    }

    public List<Country> getCountryList(){
        return countryRepository.findAll();
    }
}
