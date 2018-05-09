package me.mashyrin.filmLovers.model.entities;

public class ScoreCount {
    private Integer score;
    private Integer count;
    
    public ScoreCount() {
    }
    
    public void setCount( Integer count ) {
        this.count = count;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setScore( Integer score ) {
        this.score = score;
    }
    
    public Integer getScore() {
        return score;
    }
}
