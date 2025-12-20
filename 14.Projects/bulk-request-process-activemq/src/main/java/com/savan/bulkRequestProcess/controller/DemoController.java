package com.savan.bulkRequestProcess.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/sum/{n}")
    public DemoResultDto getSumOfN(@PathVariable int n){
        int result = n*(n+1)/2;
        return new DemoResultDto("sum of "+n, String.valueOf(result));
    }

    @GetMapping("/devide/{m}/{n}")
    public DemoResultDto getDivMByN(@PathVariable int m, @PathVariable int n){
        Integer result = m/n;
        return new DemoResultDto("devision of "+m+"/"+n, String.valueOf(result));
    }

    @GetMapping("/returnJson/{count}")
    public Object returnJson(@PathVariable int count) {
        if(count==0){
            return new DemoResultDto("operation", "solution");
        } else {
            List<DemoResultDto> demoResultDtos = new ArrayList<>();
            for(int i=0; i<=count; i++){
                demoResultDtos.add(new DemoResultDto("operation", "solution"));
            }
            return demoResultDtos;
        }
    }

    @PostMapping("/message")
    public List<Map<String, String>> sendBulkMessage(@RequestBody List<String> messages){
        // int res = 10/0;
        int i = 1;
        List<Map<String, String>> finalRespList = new ArrayList<>();
        for (String message : messages) {
            Map<String, String> map = new HashMap<>();
            map.put("processed", String.valueOf(i));
            map.put("message", message);
            finalRespList.add(map);
            i++;
        }

        return finalRespList;
    }

    @PostMapping("/processListJson")
    public List<Map<String, String>> processListJson(@RequestBody List<DemoResultDto> listJson){
        int i = 1;
        List<Map<String, String>> finalRespList = new ArrayList<>();
        for (DemoResultDto demoResultDto : listJson) {
            Map<String, String> map = new HashMap<>();
            map.put("processed", String.valueOf(i));
            map.put("message", "processed "+demoResultDto.getOperation());
            finalRespList.add(map);
            i++;
        }

        return finalRespList;
    }
}
