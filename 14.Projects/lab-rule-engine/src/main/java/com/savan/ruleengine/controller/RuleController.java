package com.savan.ruleengine.controller;

import com.savan.ruleengine.model.LabRule;
import com.savan.ruleengine.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping("/rule-index/{ruleIndex}/create")
    public ResponseEntity<String> createRule(@RequestBody LabRule labRule, @PathVariable String ruleIndex) {
        try {
            String result = ruleService.saveRule(labRule, ruleIndex);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/execute/rule-index/{ruleIndex}/test-index/{testIndex}")
    public ResponseEntity<String> executeRules(@PathVariable String ruleIndex, @PathVariable String testIndex) {
        try {
            String result = ruleService.executeRules(ruleIndex, testIndex);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("Error executing rules: " + e.getMessage());
        }

    }

}
