package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.DistrictDto;
import ru.hackaton.backend.services.DistrictService;
import ru.hackaton.backend.util.PageWrapper;

@RestController
@RequiredArgsConstructor
public class DistrictControllerImpl implements DistrictController {

    private final DistrictService districtService;

    @Override
    public DistrictDto create(DistrictDto districtDto) {
        return districtService.createDistrict(districtDto);
    }

    @Override
    public DistrictDto read(long id) {
        return districtService.getDistrictById(id);
    }

    @Override
    public void update(long id, DistrictDto districtDto) {
        districtService.updateDistrict(id, districtDto);
    }

    @Override
    public void delete(long id) {
        districtService.deleteDistrictById(id);
    }

    @Override
    public PageWrapper<DistrictDto> readAll() {
        return districtService.getAllDistricts();
    }

}
