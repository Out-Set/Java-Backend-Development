package com.savan.quartz.genericparam;

import com.savan.quartz.dto.ResponseDto;
import com.savan.quartz.entity.GenericParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/scheduler/genericParameter")
public class GenericParameterController {

    @Autowired
    private IGenericParameterService genericParameterService;

    @PostMapping("/create")
    public ResponseEntity<GenericParameter> create(@RequestBody GenericParameterDto genericParameterDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(genericParameterService.createOrUpdate(genericParameterDto));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<GenericParameter> read(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(genericParameterService.read(id));
    }

    @GetMapping("/read")
    public ResponseEntity<List<GenericParameter>> readAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(genericParameterService.readAll());
    }

    @PutMapping("/update")
    public ResponseEntity<GenericParameter> update(@RequestBody GenericParameterDto genericParameterDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(genericParameterService.createOrUpdate(genericParameterDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Long id) {
        ResponseDto responseDto = new ResponseDto(
                null,
                genericParameterService.delete(id),
                HttpStatus.OK.value()
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/read/dtype/{dtype}")
    public ResponseEntity<List<Map<String, String>>> getGenericParameterType(@PathVariable String dtype) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(genericParameterService.getGenericParameterType(dtype));
    }

    @GetMapping("/read/genericParameterMapping/all")
    public ResponseEntity<List<String>> findAllGenericParameterMappingTypes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(genericParameterService.findAllGenericParameterMappingTypes());
    }

    @GetMapping("/read/genericParameterRecords/all")
    public ResponseEntity<List<GenericParameterDto>> findAllGenericParameterRecords() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(genericParameterService.findAllGenericParameterRecords());
    }

}
