package com.savan.quartz.comm;

import com.savan.quartz.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.savan.quartz.comm.Constants.NEW;
import static com.savan.quartz.comm.Constants.PROCESSED;

@RestController
@RequestMapping("/comm-data")
public class CommDataController {

    @Autowired
    private CommDataService commDataService;

    @PostMapping("/create")
    public ResponseEntity<CommData> create(@RequestBody CommData commData){
        return ResponseEntity.ok(commDataService.create(commData));
    }

    @GetMapping("/read")
    public ResponseEntity<Object> getRecords(@RequestParam(required = false) String status){
        if(status==null) {
            ResponseDto response = new ResponseDto(
                    null,
                    "query-param 'status' is missing, pass either P(processed) or N(new) records",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else if(status.equalsIgnoreCase(NEW))
            return ResponseEntity.ok(commDataService.getUnProcessedRecords());
        else if(status.equalsIgnoreCase(PROCESSED))
            return ResponseEntity.ok(commDataService.getProcessedRecords());
        else {
            ResponseDto response = new ResponseDto(
                    null,
                    "Incorrect type queried, pass either P(processed) or N(new) records",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
