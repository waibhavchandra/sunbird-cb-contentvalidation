package org.sunbird.contentvalidation.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Getter
@Setter
@UserDefinedType("profanity_image_analysis")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfanityImageAnalysis {
    @Column("image_no")
    private Integer imageNo;

    @Column("classification")
    private String classification;

    @Column("is_safe")
    private boolean isSafe;

    public Integer getImageNo() {
        return imageNo;
    }

    public void setImageNo(Integer imageNo) {
        this.imageNo = imageNo;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void setSafe(boolean safe) {
        isSafe = safe;
    }
}
