package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.DistrictDto;
import ru.hackaton.backend.mappers.DistrictMapper;
import ru.hackaton.backend.models.domain.District;
import ru.hackaton.backend.repositories.DistrictRepository;
import ru.hackaton.backend.util.PageWrapper;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    private final DistrictMapper districtMapper;

    private District findDistrictById(long id) {
        return districtRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Округ с id " + id + " не был найден!"));
    }

    public DistrictDto createDistrict(DistrictDto districtDto) {
        District newDistrict = districtRepository.save(districtMapper.toDistrict(districtDto));
        return districtMapper.toDto(newDistrict);
    }

    public DistrictDto getDistrictById(long id) {
        return districtMapper.toDto(findDistrictById(id));
    }

    public void updateDistrict(long id, DistrictDto districtDto) {
        District district = districtMapper.toDistrict(districtDto);
        district.setId(id);
        districtRepository.save(district);
    }

    public void deleteDistrictById(long id) {
        districtRepository.deleteById(id);
    }

    public PageWrapper<DistrictDto> getAllDistricts() {
        Page<District> page = districtRepository.findAll(PageRequest.of(0, Integer.MAX_VALUE));
        return new PageWrapper<>(page.getTotalElements(), districtMapper.mapToList(page.getContent()));
    }

}
