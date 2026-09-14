package ek.osnb.starter.service;

import ek.osnb.starter.exceptions.NotFoundException;
import ek.osnb.starter.model.Actor;
import ek.osnb.starter.model.Movie;
import ek.osnb.starter.model.MovieDetails;
import ek.osnb.starter.repository.ActorRepository;
import ek.osnb.starter.repository.MovieDetailsRepository;
import ek.osnb.starter.repository.MovieRepository;
import org.springframework.stereotype.Service;

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

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty()) {
            throw new NotFoundException("Movie not found with id: " + id);
        }
        // Or use shortcut:
        // return movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Movie not found with id: " + id));

        return movieOptional.get();
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie addActorToMovie(Long movieId, Long actorId){
        Movie movieById = getMovieById(movieId);
        Optional<Actor> actorById = actorRepository.findById(actorId);
        if(actorById.isEmpty()){
            throw new NotFoundException("Actor not found with id: " + actorId);
        }
        movieById.getActorList().add(actorById.get());
        return movieRepository.save(movieById);
    }

    public Movie addDetailsToMovie(Long movieId, MovieDetails movieDetails){
        Movie movieById = getMovieById(movieId);

        movieById.setMovieDetails(movieDetails);

        movieDetails.setMovie(movieById);
        return movieRepository.save(movieById);
    }
}