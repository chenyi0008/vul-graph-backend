package cn.cheery.backend.controller;

import cn.cheery.backend.common.response.ApiResponse;
import cn.cheery.backend.entity.Cve;
import cn.cheery.backend.service.CveService;
import cn.cheery.backend.service.TaskExecutor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/cve")
public class CveController {

    @Autowired
    private CveService cveService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Value("${file.upload-dir}")
    private String uploadDir;


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
                               @RequestParam(value = "type", required = false, defaultValue = "") String type,
                               @RequestParam(value = "id", required = false, defaultValue = "") String id
    ) {
        List<Cve> cveList = cveService.getCveListBetween(min, max, type, id);
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




    @PostMapping("/upload-file")
    public ApiResponse handleMultipleFileUpload(@RequestParam("files") MultipartFile[] files) throws InterruptedException {
        if (files == null || files.length == 0) {
            return ApiResponse.fail("未上传文件");
        }

        List<String> uploadedFiles = new ArrayList<>();
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            for (MultipartFile file : files) {
                String originalFileName = file.getOriginalFilename();
                if (originalFileName == null || originalFileName.isEmpty()) continue;

                Path filePath = uploadPath.resolve(originalFileName);
                file.transferTo(filePath.toFile());
                uploadedFiles.add(originalFileName);
            }


        } catch (IOException e) {
            e.printStackTrace();
            return ApiResponse.fail("上传失败");
        }
        List<String> fileList = new ArrayList<>();
        for (MultipartFile file : files) {
            fileList.add(file.getOriginalFilename());
        }

        taskExecutor.parseFile(fileList);
        return ApiResponse.success();
    }
}