package ru.hackaton.backend.repositories;

import org.mapstruct.Named;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hackaton.backend.models.domain.Art;

@Repository
public interface ArtRepository extends JpaRepository<Art, Long> {

    @Named("getArtReferenceById")
    @Override
    @NonNull
    Art getReferenceById(@NonNull Long aLong);
}