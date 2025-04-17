package cn.cheery.backend.controller;

import cn.cheery.backend.common.response.ApiResponse;
import cn.cheery.backend.entity.Software;
import cn.cheery.backend.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/software")
public class SoftwareController {

    @Autowired
    private SoftwareService softwareService;

    @PostMapping
    public ApiResponse create(@RequestBody Software software) {
        Software software1 = softwareService.createSoftware(software);
        return ApiResponse.success(software1);
    }

    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable("id") String id) {
        Optional<Software> software = softwareService.getSoftware(id);
        if (software.isPresent()) {
            return ApiResponse.success(software.get());
        }else{
            return ApiResponse.fail("查询失败");
        }
    }

    @GetMapping()
    public ApiResponse getList() {
        List<Software> softwareList = softwareService.getSoftwareList();
        return ApiResponse.success(softwareList);
    }

    @PutMapping
    public ApiResponse update(@RequestBody Software software) {
        Software software1 = softwareService.updateSoftware(software);
        return ApiResponse.success(software1);
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable("id") String id) {
        softwareService.deleteSoftware(id);
        return ApiResponse.success();
    }
}