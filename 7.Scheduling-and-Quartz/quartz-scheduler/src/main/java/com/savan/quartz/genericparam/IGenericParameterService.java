package com.savan.quartz.genericparam;

import com.savan.quartz.entity.GenericParameter;

import java.util.List;
import java.util.Map;

public interface IGenericParameterService {

    GenericParameter createOrUpdate(GenericParameterDto genericParameterDto);

    GenericParameter read(Long id);

    List<GenericParameter> readAll();

    String delete(Long id);

    void loadGenericParameterDetailsOnStartUp();

    Map<String, String> loadGenericParameterChildDetails();

    List<Map<String, String>> getGenericParameterType(String dType);

    List<String> findAllGenericParameterMappingTypes();

    List<GenericParameterDto> findAllGenericParameterRecords();

}
