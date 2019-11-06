package org.superbiz.moviefun.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;


public class MoviesClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestOperations restOperations;
    private final String moviesEndpoint;

    private ParameterizedTypeReference<List<MovieInfo>> parameterizedTypeReference =
            new ParameterizedTypeReference<List<MovieInfo>>() {
            };



    public MoviesClient(String moviesEndpoint, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.moviesEndpoint = moviesEndpoint;
    }

    public MovieInfo find(Long id) {
        return restOperations.getForObject(moviesEndpoint + "/movies/" + id, MovieInfo.class);
    }

    public List<MovieInfo> getMovies() {
        return restOperations.exchange(moviesEndpoint + "/movies", HttpMethod.GET,
                null, parameterizedTypeReference).getBody();
    }

    public MovieInfo addMovie(MovieInfo movie) {

        HttpEntity<MovieInfo> request = new HttpEntity<>(movie);
        logger.debug("Creating movie {}", movie);
        ResponseEntity<MovieInfo> res = restOperations
                .exchange(moviesEndpoint + "/movies",
                        HttpMethod.POST,
                        request, MovieInfo.class);

        return res.getBody();
    }


    public void deleteMovieId(long id) {
        restOperations.delete(moviesEndpoint + "/movies/" + id);

    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) {

        String uriString = UriComponentsBuilder.fromUriString(moviesEndpoint)
                .path("/movies")
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults)
                .build().toUriString();

        ResponseEntity<List<MovieInfo>> responseEntity = restOperations
                .exchange(uriString,
                        HttpMethod.GET, null, parameterizedTypeReference);

        return responseEntity.getBody();
    }

    public int countAll() {

        Integer result = restOperations.getForObject(moviesEndpoint + "/movies/count", Integer.class);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public int count(String field, String searchTerm) {

        String uriString = UriComponentsBuilder.fromUriString(moviesEndpoint)
                .path("/movies/count")
                .queryParam("field", field)
                .queryParam("searchTerm", searchTerm)
                .build().toUriString();


        Integer result = restOperations.getForObject(uriString, Integer.class);

        if (result == null) {
            return 0;
        }
        return result;
    }

    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        String uriString = UriComponentsBuilder.fromUriString(moviesEndpoint)
                .path("/movies")
                .queryParam("field", field)
                .queryParam("key", searchTerm)
                .queryParam("start", firstResult)
                .queryParam("pageSize", maxResults)
                .build().toUriString();

        ResponseEntity<List<MovieInfo>> responseEntity = restOperations
                .exchange(uriString, HttpMethod.GET, null, parameterizedTypeReference);
        return responseEntity.getBody();
    }
}
