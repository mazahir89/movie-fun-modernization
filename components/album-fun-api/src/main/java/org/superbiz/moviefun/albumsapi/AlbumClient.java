package org.superbiz.moviefun.albumsapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.util.List;

public class AlbumClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestOperations restOperations;
    private final String albumsEndpoint;

    private ParameterizedTypeReference<List<AlbumInfo>> parameterizedTypeReference =
            new ParameterizedTypeReference<List<AlbumInfo>>() {
            };

    public AlbumClient(String albumsEndpoint, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.albumsEndpoint = albumsEndpoint;
    }


    public AlbumInfo addAlbum(AlbumInfo album) {
        HttpEntity<AlbumInfo> request = new HttpEntity<>(album);
        logger.debug("Creating album {}", album);
        ResponseEntity<AlbumInfo> res = restOperations
                .exchange(albumsEndpoint + "/albums",
                        HttpMethod.POST,
                        request, AlbumInfo.class);

        return res.getBody();
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumsEndpoint + "/albums", HttpMethod.GET,
                null, parameterizedTypeReference).getBody();
    }

    public AlbumInfo find(long albumId) {
        return restOperations.getForObject(albumsEndpoint + "/albums/" + albumId, AlbumInfo.class);
    }

}
