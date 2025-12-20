package com.pk.development.persistence.config;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PersistenceDescriptorService {

    private final List<PersistenceConfigDescriptor> descriptors;

    public PersistenceDescriptorService(List<PersistenceConfigDescriptor> descriptors) {
        this.descriptors = descriptors;
    }

    public List<String> getDatabaseNames() {
        return descriptors.stream()
                          .map(PersistenceConfigDescriptor::getDatabaseName)
                          .toList();
    }
}
