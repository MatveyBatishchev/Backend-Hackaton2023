package ru.hackaton.backend.repositories;

import org.mapstruct.Named;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import ru.hackaton.backend.models.domain.District;

public interface DistrictRepository extends JpaRepository<District, Long> {

    @Named("getDistrictReferenceById")
    @Override
    @NonNull
    District getReferenceById(@NonNull Long aLong);

}