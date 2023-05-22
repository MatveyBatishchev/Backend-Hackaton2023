package ru.hackaton.backend.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hackaton.backend.dtos.ArtDto;
import ru.hackaton.backend.mappers.ArtMapper;
import ru.hackaton.backend.models.domain.Art;
import ru.hackaton.backend.repositories.ArtRepository;
import ru.hackaton.backend.util.PageWrapper;

@Service
@RequiredArgsConstructor
public class ArtService {

    private final ArtRepository artRepository;

    private final ArtMapper artMapper;

    private Art findArtById(long id) {
        return artRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Направление с id " + id + " не был найден!"));
    }

    public ArtDto createArt(ArtDto artDto) {
        Art newArt = artRepository.save(artMapper.toArt(artDto));
        return artMapper.toDto(newArt);
    }

    public ArtDto getArtById(long id) {
        return artMapper.toDto(findArtById(id));
    }

    public void updateArt(long id, ArtDto artDto) {
        Art art = artMapper.toArt(artDto);
        art.setId(id);
        artRepository.save(art);
    }

    public void deleteArtById(long id) {
        artRepository.deleteById(id);
    }

    public PageWrapper<ArtDto> getAllArts() {
        Page<Art> page = artRepository.findAll(PageRequest.of(0, Integer.MAX_VALUE));
        return new PageWrapper<>(page.getTotalElements(), artMapper.mapToList(page.getContent()));
    }

}