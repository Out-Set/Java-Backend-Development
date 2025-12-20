package com.savan.ruleengine.controller;

import com.savan.ruleengine.model.LabTest;
import com.savan.ruleengine.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tests")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/test-index/{testIndex}/create")
    public ResponseEntity<String> createRule(@RequestBody LabTest labTest, @PathVariable String testIndex) {
        try {
            String result = testService.saveTest(labTest, testIndex);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
