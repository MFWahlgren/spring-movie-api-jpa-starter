package ek.osnb.starter.dto;

public record CreateMovieRequest(String title,
                                 Integer releaseYear,
                                 String genre,
                                 Double ratingScore,
                                 Integer ratingVoteCount) {
}
