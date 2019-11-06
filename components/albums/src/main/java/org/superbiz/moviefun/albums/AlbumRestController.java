package org.superbiz.moviefun.albums;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumRestController {

    private final AlbumsRepository albumsRepository;

    public AlbumRestController(AlbumsRepository albumsRepository) {
        this.albumsRepository = albumsRepository;
    }

    @GetMapping
    public List<Album> getAlbums() {
        return albumsRepository.getAlbums();
    }

    @GetMapping("{id}")
    public Album getAlbum(@PathVariable Long id) {
        return albumsRepository.find(id);
    }

    @PostMapping
    public Album addAlbum(@RequestBody Album album) {
        albumsRepository.addAlbum(album);
        return album;
    }

}
