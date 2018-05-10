package me.mashyrin.filmLovers.model.entities;

/**
 * Score's count entity class
 *
 * @author mashyrin
 */
public class ScoreCount {
    private Integer score;
    private Integer count;
    
    /**
     * Empty score's count constructor
     */
    public ScoreCount() {
    }
    
    /**
     * @param count
     */
    public void setCount( Integer count ) {
        this.count = count;
    }
    
    /**
     * @return count
     */
    public Integer getCount() {
        return count;
    }
    
    /**
     * @param score
     */
    public void setScore( Integer score ) {
        this.score = score;
    }
    
    /**
     * @return score
     */
    public Integer getScore() {
        return score;
    }
}
