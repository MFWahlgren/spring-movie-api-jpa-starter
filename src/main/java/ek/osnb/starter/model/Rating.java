package ek.osnb.starter.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Rating {
    private Double score;
    private Integer voteCount;

    protected Rating(){}

    public Rating (Double score, Integer voteCount){
        this.score = score;
        this.voteCount = voteCount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

}
