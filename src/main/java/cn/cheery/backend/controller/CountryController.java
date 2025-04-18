package cn.cheery.backend.controller;

import cn.cheery.backend.common.response.ApiResponse;
import cn.cheery.backend.entity.Country;
import cn.cheery.backend.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping()
    public ApiResponse getList() {
        List<Country> countryList = countryService.getCountryList();
        return ApiResponse.success(countryList);
    }

//    @PostMapping
    public ApiResponse create(@RequestBody Country country) {
        Country country1 = countryService.createCountry(country);
        return ApiResponse.success(country1);
    }

//    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable("id") String id) {
        Optional<Country> country = countryService.getCountry(id);
        if (country.isPresent()) {
            return ApiResponse.success(country.get());
        }else{
            return ApiResponse.fail("查询失败");
        }
    }


//    @PutMapping
    public ApiResponse update(@RequestBody Country country) {
        Country country1 = countryService.updateCountry(country);
        return ApiResponse.success(country1);
    }

//    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable("id") String id) {
        countryService.deleteCountry(id);
        return ApiResponse.success();
    }

}