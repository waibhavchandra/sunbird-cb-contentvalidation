package org.sunbird.contentvalidation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentPdfValidationResponse {

	@Id
	private String id;

	private int totalPages;
	private int profanityWordCount;
	private int totalPageImages;
	private double score;
	private String pdfFileName;
	private String imageOccurances;
	private String overall_text_classification;
	private List<ProfanityWordFrequency> profanityWordList = Collections.emptyList();
	private String contentId;
	private boolean isCompleted;
	private String errorMessage;

	public void incrementTotalPages() {
		totalPages++;
	}

	public void incrementProfanityWordCount() {
		profanityWordCount++;
	}

	public void addProfanityWordDetails(ProfanityWordFrequency profanityWord) {
		if (profanityWordList.isEmpty()) {
			profanityWordList = new ArrayList<>();
		}
		profanityWordList.add(profanityWord);
	}
}
