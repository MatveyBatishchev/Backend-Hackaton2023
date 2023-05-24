package ru.hackaton.backend.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.hackaton.backend.dtos.DistrictDto;
import ru.hackaton.backend.models.domain.District;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", config = IgnoreUnmappedMapperConfig.class)
public interface DistrictMapper {

    @Named("toDistrictDto")
    DistrictDto toDto(District district);

    @Mapping(target = "id", ignore = true)
    District toDistrict(DistrictDto districtDto);

    @IterableMapping(qualifiedByName = "toDistrictDto")
    List<DistrictDto> mapToList(Collection<District> districts);

}
