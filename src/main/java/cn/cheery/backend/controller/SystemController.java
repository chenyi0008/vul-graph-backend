package cn.cheery.backend.controller;

import cn.cheery.backend.common.response.ApiResponse;
import cn.cheery.backend.entity.SystemNode;
import cn.cheery.backend.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @PostMapping
    public ApiResponse create(@RequestBody SystemNode system) {
        SystemNode system1 = systemService.createSystem(system);
        return ApiResponse.success(system1);
    }

    @GetMapping("/{id}")
    public ApiResponse get(@PathVariable("id") UUID id) {
        Optional<SystemNode> system = systemService.getSystem(id);
        if (system.isPresent()) {
            return ApiResponse.success(system.get());
        }else{
            return ApiResponse.fail("查询失败");
        }
    }

    @GetMapping()
    public ApiResponse getList() {
        List<SystemNode> systemList = systemService.getSystemList();
        return ApiResponse.success(systemList);
    }

    @PutMapping
    public ApiResponse update(@RequestBody SystemNode system) {
        SystemNode system1 = systemService.updateSystem(system);
        return ApiResponse.success(system1);
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable("id") UUID id) {
        systemService.deleteSystem(id);
        return ApiResponse.success();
    }
}