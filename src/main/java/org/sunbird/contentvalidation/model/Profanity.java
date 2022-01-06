package org.sunbird.contentvalidation.model;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profanity {

	private String textOriginal;

	private String textTagged;

	private List<String> possibleProfanity;

	private Integer possibleProfaneWordCount;

	private List<ProfanityLineClassification> lineAnalysis;

	private ProfanityClassification overallTextClassification;

	private List<ProfanityWordFrequency> possibleProfanityFrequency;

	private HashMap<String, ProfanityCategorial> possibleProfanityCategorical;
}
