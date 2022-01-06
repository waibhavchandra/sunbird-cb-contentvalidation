package org.sunbird.contentvalidation.repo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.sunbird.contentvalidation.model.ProfanityImageAnalysis;
import org.sunbird.contentvalidation.model.ProfanityIndiaMapAnalysis;
import org.sunbird.contentvalidation.model.ProfanityWordFrequency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table("pdf_validation_response")
@Getter
@Setter
public class PdfDocValidationResponse {
    @PrimaryKey
    private PdfDocValidationResponsePrimaryKey primaryKey;

    @Column("isCompleted")
    private boolean isCompleted;

    @Column("no_of_pages_completed")
    private Integer noOfPagesCompleted;

    @Column("total_pages")
    private Integer totalPages;

    @Column("profanity_word_count")
    private Integer profanityWordCount;

    @Column("total_page_images")
    private Integer totalPageImages;

    @Column("score")
    private double score;

    @Column("image_occrances")
    private String imageOccurances;

    @Column("overall_text_classification")
    private String overallTextClassification;

    @Column("error_message")
    private String errorMessage;

    @Column("profanity_word_list")
    private List<ProfanityWordFrequency> profanityWordList;

    @Column("image_analysis_map")
    private Map<Integer, List<ProfanityImageAnalysis>> profanityImageAnalysisMap;

    @Column("india_map_classification")
    private Map<Integer, List<ProfanityIndiaMapAnalysis>> indiaMapClassification;

    public void addProfanityWordFrequency(List<ProfanityWordFrequency> wordFrequencyList) {
        if (profanityWordList == null || profanityWordList.isEmpty()) {
            profanityWordList = new ArrayList<>();
        }
        profanityWordList.addAll(wordFrequencyList);
    }

    public void addProfanityWordFrequency(ProfanityWordFrequency wordFrequency) {
        if (profanityWordList == null || profanityWordList.isEmpty()) {
            profanityWordList = new ArrayList<>();
        }
        profanityWordList.add(wordFrequency);
    }

    public void incrementTotalNoOfPagesCompleted() {
        if (noOfPagesCompleted == null) {
            noOfPagesCompleted = 1;
        } else {
            noOfPagesCompleted++;
        }
    }

    public void incrementTotalPagesImages() {
        if (totalPageImages == null) {
            totalPageImages = 1;
        } else {
            totalPageImages++;
        }
    }

    public void incrementProfanityWordCount() {
        if (profanityWordCount == null) {
            profanityWordCount = 1;
        } else {
            profanityWordCount++;
        }
    }

    public void addImageOccurances(int pageIndex) {
        if (StringUtils.isEmpty(this.imageOccurances)) {
            this.imageOccurances = String.valueOf(pageIndex + 1);
        } else {
            this.imageOccurances = this.imageOccurances + ", " + (pageIndex + 1);
        }
    }

    public void addProfanityImageAnalysis(Map<Integer, List<ProfanityImageAnalysis>> imageAnalysisMap) {
        if (CollectionUtils.isEmpty(profanityImageAnalysisMap)) {
            profanityImageAnalysisMap = new HashMap<>();
        }
        profanityImageAnalysisMap.putAll(imageAnalysisMap);
    }

    public void addProfanityIndiaMapAnalysis(Map<Integer, List<ProfanityIndiaMapAnalysis>> indiaMapAnalysis) {
        if (CollectionUtils.isEmpty(indiaMapClassification)) {
            indiaMapClassification = new HashMap<>();
        }
        indiaMapClassification.putAll(indiaMapAnalysis);
    }

}
