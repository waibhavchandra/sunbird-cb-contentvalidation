package org.sunbird.contentvalidation.model;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profanity {

	@JsonProperty("text_original")
	private String textOriginal;

	@JsonProperty("text_tagged")
	private String textTagged;

	@JsonProperty("possible_profanity")
	private List<String> possibleProfanity;

	@JsonProperty("possible_profane_word_count")
	private Integer possibleProfaneWordCount;

	@JsonProperty("line_analysis")
	private List<ProfanityLineClassification> lineAnalysis;

	@JsonProperty("overall_text_classification")
	private ProfanityClassification overallTextClassification;

	@JsonProperty("possible_profanity_frequency")
	private List<ProfanityWordFrequency> possibleProfanityFrequency;

	@JsonProperty("possible_profanity_categorical")
	private HashMap<String, ProfanityCategorial> possibleProfanityCategorical;
}
