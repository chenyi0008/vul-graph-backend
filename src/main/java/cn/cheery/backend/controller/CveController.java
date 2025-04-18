package cn.cheery.backend.controller;

import cn.cheery.backend.common.response.ApiResponse;
import cn.cheery.backend.entity.Cve;
import cn.cheery.backend.service.CveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cve")
public class CveController {

    @Autowired
    private CveService cveService;

    @PostMapping
    public ApiResponse create(@RequestBody Cve cve) {
        Cve cve1 = cveService.createCve(cve);
        return ApiResponse.success(cve1);
    }

    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable("id") String id) {
        Optional<Cve> cve = cveService.getCve(id);
        if (cve.isPresent()) {
            return ApiResponse.success(cve.get());
        }else{
            return ApiResponse.fail("查询失败");
        }
    }

    @GetMapping()
    public ApiResponse getList(@RequestParam(value = "min", defaultValue = "0.0") Double min,
                               @RequestParam(value = "max", defaultValue = "99.9") Double max,
                               @RequestParam(value = "type", required = false, defaultValue = "") String type) {
        List<Cve> cveList = cveService.getCveListBetween(min, max, type);
        return ApiResponse.success(cveList);
    }

    @PutMapping
    public ApiResponse update(@RequestBody Cve cve) {
        Cve cve1 = cveService.updateCve(cve);
        return ApiResponse.success(cve1);
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable("id") String id) {
        cveService.deleteCve(id);
        return ApiResponse.success();
    }


    @PostMapping("/bind-software")
    public ApiResponse bindSoftware(@RequestParam String cveId, @RequestParam(defaultValue = "") List<UUID> softwareId) {
        Optional<Object> op = cveService.createCveSoftwareRelationship(cveId, softwareId);
        if (op.isPresent()) {
            return ApiResponse.success(op.get());
        }
        return ApiResponse.fail("插入失败");
    }

    @PostMapping("/bind-system")
    public ApiResponse bingSystem(@RequestParam String cveId, @RequestParam(defaultValue = "") List<UUID> systemId) {
        Optional<Cve> op = cveService.createCveSystemRelationship(cveId, systemId);
        if(op.isPresent()){
            return ApiResponse.success(op.get());
        }
        return ApiResponse.fail("插入失败");
    }

    @PostMapping("/bind-country")
    public ApiResponse bingCountry(@RequestParam String cveId, @RequestParam String countryId) {
        Optional<Cve> op = cveService.createCveCountryRelationship(cveId, countryId);
        if(op.isPresent()){
            return ApiResponse.success(op.get());
        }
        return ApiResponse.fail("插入失败");
    }


}