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
    private Float incorrectPercentage;

    @Column("is_india_map_detected")
    private Boolean isIndiaMapDetected;

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

    public Float getIncorrectPercentage() {
        return incorrectPercentage;
    }

    public void setIncorrectPercentage(Float incorrectPercentage) {
        this.incorrectPercentage = incorrectPercentage;
    }

    public Boolean getIsIndiaMapDetected() {
        return isIndiaMapDetected;
    }

    public void setIsIndiaMapDetected(Boolean isIndiaMapDetected) {
        this.isIndiaMapDetected = isIndiaMapDetected;
    }
}
