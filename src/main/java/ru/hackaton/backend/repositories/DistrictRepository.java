package ru.hackaton.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hackaton.backend.models.domain.District;

public interface DistrictRepository extends JpaRepository<District, Long> {

}