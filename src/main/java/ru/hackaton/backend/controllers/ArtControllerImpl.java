package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.hackaton.backend.dtos.ArtDto;
import ru.hackaton.backend.services.ArtService;
import ru.hackaton.backend.util.PageWrapper;

@RestController
@RequiredArgsConstructor
public class ArtControllerImpl implements ArtController {

    private final ArtService artService;

    @Override
    public ArtDto create(ArtDto artDto) {
        return artService.createArt(artDto);
    }

    @Override
    public ArtDto read(long id) {
        return artService.getArtById(id);
    }

    @Override
    public void update(long id, ArtDto artDto) {
        artService.updateArt(id, artDto);
    }

    @Override
    public void delete(long id) {
        artService.deleteArtById(id);
    }

    @Override
    public PageWrapper<ArtDto> readAll() {
        return artService.getAllArts();
    }

}
