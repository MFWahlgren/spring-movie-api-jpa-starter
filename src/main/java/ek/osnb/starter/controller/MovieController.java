package ek.osnb.starter.controller;

import ek.osnb.starter.dto.CreateMovieRequest;
import ek.osnb.starter.dto.MovieResponse;
import ek.osnb.starter.model.Movie;
import ek.osnb.starter.model.MovieDetails;
import ek.osnb.starter.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody CreateMovieRequest request) {
        return ResponseEntity.ok(movieService.createMovie(request));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{movieId}/actors/{actorId}")
    public ResponseEntity<Movie> addActorToMovie(@PathVariable Long movieId,
                                                 @PathVariable Long actorId){
        return ResponseEntity.ok(movieService.addActorToMovie(movieId, actorId));
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<Movie> addDetailsToMovie(@PathVariable Long id,
                                                   @RequestBody MovieDetails movieDetails){
        return ResponseEntity.ok(movieService.addDetailsToMovie(id, movieDetails));
    }
}
