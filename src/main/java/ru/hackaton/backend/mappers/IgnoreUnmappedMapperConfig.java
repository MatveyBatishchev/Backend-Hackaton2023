package ru.hackaton.backend.mappers;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IgnoreUnmappedMapperConfig {
    // ignore unmapped properties in several mappers by setting the unmappedTargetPolicy via
    // @MapperConfig to share a setting across several mappers
}