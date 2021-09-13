package org.sunbird.contentvalidation.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Getter
@Setter
@UserDefinedType("india_map_analysis")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfanityIndiaMapAnalysis {
    @Column("image_no")
    private Integer imageNo;

    @Column("probability")
    private float probability;

    @Column("incorrect_percentage")
    private float incorrect_percentage;

    @Column("is_india_map_detected")
    private boolean is_india_map_detected;

    public Integer getImageNo() {
        return imageNo;
    }

    public void setImageNo(Integer imageNo) {
        this.imageNo = imageNo;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

    public float getIncorrect_percentage() {
        return incorrect_percentage;
    }

    public void setIncorrect_percentage(float incorrect_percentage) {
        this.incorrect_percentage = incorrect_percentage;
    }

    public boolean isIs_india_map_detected() {
        return is_india_map_detected;
    }

    public void setIs_india_map_detected(boolean is_india_map_detected) {
        this.is_india_map_detected = is_india_map_detected;
    }
}
