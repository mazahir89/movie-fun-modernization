package org.superbiz.moviefun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.superbiz.moviefun.albumsapi.AlbumClient;
import org.superbiz.moviefun.moviesapi.MoviesClient;

@Configuration
public class ClientConfiguration {

    private final String moviesUrl, albumsUrl;

    public ClientConfiguration(@Value("${movies.url}") String moviesUrl,
                               @Value("${albums.url}") String albumsUrl) {
        this.moviesUrl = moviesUrl;
        this.albumsUrl = albumsUrl;
    }

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @Bean
    public MoviesClient moviesClient(RestOperations restOperations) {
        return new MoviesClient(moviesUrl, restOperations);
    }

    @Bean
    public AlbumClient albumClient(RestOperations restOperations) {
        return new AlbumClient(albumsUrl, restOperations);
    }
}