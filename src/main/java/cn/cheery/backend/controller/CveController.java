package cn.cheery.backend.controller;

import cn.cheery.backend.common.response.ApiResponse;
import cn.cheery.backend.entity.Cve;
import cn.cheery.backend.service.CveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ApiResponse getList() {
        List<Cve> cveList = cveService.getCveList();
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
}