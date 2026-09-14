package ek.osnb.starter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class MovieDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plot;
    private Integer budget;
    private Integer runtime; // in minutes
    private String productionCompany;

    @OneToOne(mappedBy = "movieDetails")
    @JsonBackReference
    private Movie movie;

    // Constructors
    public MovieDetails() {}

    public MovieDetails(String plot, Integer budget, Integer runtime, String productionCompany) {
        this.plot = plot;
        this.budget = budget;
        this.runtime = runtime;
        this.productionCompany = productionCompany;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
