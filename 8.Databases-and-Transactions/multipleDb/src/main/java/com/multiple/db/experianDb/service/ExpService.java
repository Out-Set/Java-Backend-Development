package com.multiple.db.experianDb.service;

import com.multiple.db.experianDb.repo.ExperianDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpService {

    @Autowired
    private ExperianDataRepo experianDataRepo;


}
