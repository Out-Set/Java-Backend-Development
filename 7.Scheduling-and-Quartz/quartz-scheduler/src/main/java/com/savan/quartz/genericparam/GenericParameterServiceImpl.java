package com.savan.quartz.genericparam;

import com.savan.quartz.entity.GenericParameter;
import com.savan.quartz.exceptionhandler.RecordNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.metamodel.EntityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.savan.quartz.utils.ValidatorUtils.notNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenericParameterServiceImpl implements IGenericParameterService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private GenericParameterRepo genericParameterRepo;

    public Map<String, String> genericParameterDetails = new HashMap<>();

    @Override
    @PostConstruct
    public void loadGenericParameterDetailsOnStartUp() {
        try {
            genericParameterDetails = loadGenericParameterChildDetails();
            log.info("interfaceCodeDetails are loaded : {}", genericParameterDetails);
        } catch (Exception e) {
            log.info("Error occurred during loading interfaceCodeDetails :{}", e.getLocalizedMessage());
        }
    }

    public Map<String, String> loadGenericParameterChildDetails() {
        Map<String, String> genericParameterChildDtls = new HashMap<>();
        Set<EntityType<?>> allEntityTypes = entityManager.getMetamodel().getEntities();
        for (EntityType<?> et : allEntityTypes) {
            if (GenericParameter.class.isAssignableFrom(et.getJavaType()) && !GenericParameter.class.getSimpleName().equals(et.getJavaType().getSimpleName())) {
                genericParameterChildDtls.put( et.getJavaType().getSimpleName(), et.getJavaType().getName());
            }
        }
        return genericParameterChildDtls;
    }

    @Override
    public GenericParameter createOrUpdate(GenericParameterDto genericParameterDto) {
        Class<?> clazz;
        GenericParameter genericParameter = null;
        try {
            clazz = Class.forName(genericParameterDetails.get(genericParameterDto.getDType()));
            if (notNull(genericParameterDto.getId())) {
                if(genericParameterRepo.findById(genericParameterDto.getId()).isEmpty())
                    throw new RecordNotFoundException("Record with id "+genericParameterDto.getId()+", doesn't exist!");
                genericParameter = genericParameterRepo.getReferenceById(genericParameterDto.getId());
                genericParameter.setLastUpdatedTimeStamp(LocalDateTime.now());
                genericParameter.setLastUpdatedBy(genericParameterDto.getCreatedBy());
                genericParameter.setCode(genericParameterDto.getCode());
                genericParameter.setName(genericParameterDto.getName());
                genericParameter.setDescription(genericParameterDto.getDescription());
                genericParameter.setParentCode(genericParameterDto.getParentCode());
            } else {
                genericParameter = (GenericParameter) clazz.getDeclaredConstructor().newInstance();
                genericParameter.setCode(genericParameterDto.getCode());
                genericParameter.setName(genericParameterDto.getName());
                genericParameter.setDescription(genericParameterDto.getDescription());
                genericParameter.setCreatedBy(genericParameterDto.getCreatedBy());
                genericParameter.setParentCode(genericParameterDto.getParentCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("genericParameter :{}", genericParameter);
        return genericParameterRepo.save(genericParameter);
    }

    @Override
    public GenericParameter read(Long id) {
        return genericParameterRepo.findById(id)
                .orElseThrow(()->new RecordNotFoundException("Record with id "+id+", doesn't exist!"));
    }

    @Override
    public List<GenericParameter> readAll() {
        return genericParameterRepo.findAll();
    }

    @Override
    public String delete(Long id) {
        genericParameterRepo.deleteById(id);
        return "Record with id "+id+", deleted successfully";
    }

    public List<Map<String, String>> getGenericParameterType(String dType) {
        TypedQuery<Object[]> query = entityManager.createQuery(
                "Select s.name,s.code from " + dType + " s ",Object[].class);
        List<Object[]> genericParameterType = query.getResultList();
        List<Map<String, String>> resultList = new ArrayList<>();
        for (Object[] result : genericParameterType) {
            Map<String, String> map = new HashMap<>();
            map.put("name", result[0].toString());
            map.put("code", result[1].toString());
            resultList.add(map);
        }
        return resultList;
    }

    @Override
    public List<String> findAllGenericParameterMappingTypes() {
        List<String> allInterfaceCodeMappingTypes = new ArrayList<>();
        Set<EntityType<?>> allEntityTypes = this.entityManager.getMetamodel().getEntities();
        for (EntityType<?> et : allEntityTypes) {
            if (!GenericParameter.class.getSimpleName().equals(et.getJavaType().getSimpleName()) && GenericParameter.class.isAssignableFrom(et.getJavaType())) {
                allInterfaceCodeMappingTypes.add(et.getJavaType().getSimpleName());
            }
        }
        return allInterfaceCodeMappingTypes;
    }

    public List<GenericParameterDto> findAllGenericParameterRecords() {
        List<GenericParameterDto> genericParameterDtos = new ArrayList<>();
        String sql = "SELECT id, code, name, dtype,parent_code,description,created_by FROM generic_parameter_config";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultList=query.getResultList();
        for(Object[] object: resultList) {
            GenericParameterDto dto= new GenericParameterDto();
            dto.setId(object[0] != null ? (Long)object[0] : null);
            dto.setCode(object[1] != null ? (String)object[1]:"");
            dto.setName(object[2] != null ? (String)object[2]:"");
            dto.setDType(object[3] != null ? (String)object[3]:"");
            dto.setParentCode(object[4] != null ? (String)object[4]:"");
            dto.setDescription(object[5] != null ? (String)object[5]:"");
            dto.setCreatedBy(object[6] != null ? (String)object[6]:"");
            genericParameterDtos.add(dto);
        }
        return genericParameterDtos;
    }

}
