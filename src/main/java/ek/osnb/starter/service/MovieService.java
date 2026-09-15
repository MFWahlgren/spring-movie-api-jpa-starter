package ek.osnb.starter.service;

import ek.osnb.starter.dto.ActorResponse;
import ek.osnb.starter.dto.CreateMovieRequest;
import ek.osnb.starter.dto.MovieDetailsResponse;
import ek.osnb.starter.dto.MovieResponse;
import ek.osnb.starter.exceptions.NotFoundException;
import ek.osnb.starter.model.Actor;
import ek.osnb.starter.model.Movie;
import ek.osnb.starter.model.MovieDetails;
import ek.osnb.starter.model.Rating;
import ek.osnb.starter.repository.ActorRepository;
import ek.osnb.starter.repository.MovieDetailsRepository;
import ek.osnb.starter.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieDetailsRepository movieDetailsRepository;

    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository, MovieDetailsRepository movieDetailsRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.movieDetailsRepository = movieDetailsRepository;
    }

    public MovieResponse createMovie(CreateMovieRequest request) {
        Movie movie = toMovieEntity(request);
        Movie saved = movieRepository.save(movie);

        return toMovieResponse(saved);
    }

    public List<MovieResponse> getAllMovies() {
        var movies = movieRepository.findAll();
        List<MovieResponse> movieResponses = new ArrayList<>();
        for(Movie m : movies){
            movieResponses.add(toMovieResponse(m));
        }
        return movieResponses;
    }

    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found!"));
        return toMovieResponse(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie addActorToMovie(Long movieId, Long actorId){
        Movie movieById = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        Optional<Actor> actorById = actorRepository.findById(actorId);
        if(actorById.isEmpty()){
            throw new NotFoundException("Actor not found with id: " + actorId);
        }
        movieById.getActorList().add(actorById.get());
        return movieRepository.save(movieById);
    }

    public Movie addDetailsToMovie(Long movieId, MovieDetails movieDetails){
        Movie movieById = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movieById.setMovieDetails(movieDetails);

        movieDetails.setMovie(movieById);
        return movieRepository.save(movieById);
    }

    private MovieResponse toMovieResponse(Movie movie) {
        MovieDetailsResponse movieDetailsResponse = new MovieDetailsResponse(
                movie.getMovieDetails().getPlot(),
                movie.getMovieDetails().getBudget(),
                movie.getMovieDetails().getRuntime(),
                movie.getMovieDetails().getProductionCompany()
        );

        List<ActorResponse> actorResponseList = new ArrayList<>();
        for (Actor actor : movie.getActorList()) {
            actorResponseList.add(new ActorResponse(actor.getId(), actor.getName(), actor.getBirthYear()));
        }

        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getGenre(),
                movie.getRating().getScore(),
                movie.getRating().getVoteCount(),
                movieDetailsResponse,
                actorResponseList
        );
    }

    private Movie toMovieEntity(CreateMovieRequest request) {
        Movie movie = new Movie(
                request.title(),
                request.releaseYear(),
                request.genre()
        );
        Rating rating = new Rating(request.ratingScore(), request.ratingVoteCount());
        movie.setRating(rating);
        return movie;
    }
}