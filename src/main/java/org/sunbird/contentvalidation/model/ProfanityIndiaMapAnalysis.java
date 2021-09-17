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
    private Float probability;

    @Column("incorrect_percentage")
    private Float incorrect_percentage;

    @Column("is_india_map_detected")
    private Boolean is_india_map_detected;

    public Integer getImageNo() {
        return imageNo;
    }

    public void setImageNo(Integer imageNo) {
        this.imageNo = imageNo;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    public Float getIncorrect_percentage() {
        return incorrect_percentage;
    }

    public void setIncorrect_percentage(Float incorrect_percentage) {
        this.incorrect_percentage = incorrect_percentage;
    }

    public Boolean getIs_india_map_detected() {
        return is_india_map_detected;
    }

    public void setIs_india_map_detected(Boolean is_india_map_detected) {
        this.is_india_map_detected = is_india_map_detected;
    }
}
