package org.sunbird.contentvalidation.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.sunbird.contentvalidation.config.Configuration;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponse;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponsePrimaryKey;
import org.sunbird.contentvalidation.service.impl.OutboundRequestHandlerServiceImpl;

import lombok.extern.log4j.Log4j2;

import java.util.*;

@Service
@Log4j2
public class ContentValidationRepoServiceImpl {
	@Autowired
	PdfDocValidationRepository pdfRepo;

	@Autowired
	private OutboundRequestHandlerServiceImpl requestHandlerService;

	@Autowired
	private Configuration configuration;

	@Autowired
	private ObjectMapper mapper;

	public void insertStartData(String contentId, String fileName) {

		PdfDocValidationResponse pdfResponse = getContentValidationResponseForFile(contentId, fileName);
		log.info("Insert Start Data. Does any response existing ? {} ", pdfResponse != null);
		if (pdfResponse != null) {
			pdfResponse.setCompleted(false);
			pdfResponse.setErrorMessage("");
			pdfResponse.setImage_occurances("");
			pdfResponse.setOverall_text_classification("");
			pdfResponse.setProfanity_word_count(0);
			pdfResponse.setProfanityWordList(null);
			pdfResponse.setScore(0.0);
			pdfResponse.setTotal_page_images(0);
			pdfResponse.setNoOfPagesCompleted(0);
			pdfResponse.setTotal_pages(0);
		} else {
			pdfResponse = new PdfDocValidationResponse();
			pdfResponse.setPrimaryKey(
					PdfDocValidationResponsePrimaryKey.builder().contentId(contentId).pdfFileName(fileName).build());
			pdfResponse.setCompleted(false);
		}
		pdfRepo.save(pdfResponse);
	}

	public void updateContentValidationResult(PdfDocValidationResponse newResponse, boolean isCompleted) {
		PdfDocValidationResponse responseExisting = getContentValidationResponseForFile(
				newResponse.getPrimaryKey().getContentId(), newResponse.getPrimaryKey().getPdfFileName());
		if (responseExisting != null) {
			responseExisting.setOverall_text_classification(newResponse.getOverall_text_classification());
			responseExisting.setProfanity_word_count(newResponse.getProfanity_word_count());
			responseExisting.setProfanityWordList(newResponse.getProfanityWordList());
			responseExisting.setScore(newResponse.getScore());
			responseExisting.setNoOfPagesCompleted(newResponse.getNoOfPagesCompleted());
			responseExisting.setCompleted(isCompleted);
			responseExisting.setTotal_page_images(newResponse.getTotal_page_images());
			responseExisting.setProfanityWordList(newResponse.getProfanityWordList());
			responseExisting.setImage_occurances(newResponse.getImage_occurances());

			pdfRepo.save(responseExisting);
		} else {
			log.error("Failed to find existing record from latestResponse");
		}
	}

	public PdfDocValidationResponse getContentValidationResponseForFile(String contentId, String pdfFileName) {
		return pdfRepo.findProgressByContentIdAndPdfFileName(contentId, pdfFileName);
	}

	public List<PdfDocValidationResponse> getContentValidationResponse(String rootOrg, String wid, String contentId){
		return pdfRepo.findProgressByContentIds(getParentAndChildContentIds(contentId));
	}

	public List<String> getParentAndChildContentIds(String contentId) {
		List<String> contentIds = new ArrayList<>();
		contentIds.add(contentId);
		try {
			StringBuilder url = new StringBuilder();
			url.append(configuration.getContentHost()).append(configuration.getHierarchyEndPoint()).append("/"+contentId).append("?mode=edit");
			log.info("URL for Hierarchy End Point :: {}", url);
			Map response = mapper.convertValue(requestHandlerService.fetchResult(url.toString()), Map.class);
			log.info("Response of Hierarchy search request {}", mapper.writeValueAsString(response));
			if (!ObjectUtils.isEmpty(response.get("result"))) {
				Map<String, Object> result = (Map<String, Object>) response.get("result");
				Map<String, Object> content = (Map<String, Object>)result.get("content");
				if(!CollectionUtils.isEmpty(content)){
					List<String> childIds = (List<String>) content.get("childNodes");
					if (!CollectionUtils.isEmpty(childIds)) {
						contentIds.addAll(childIds);
					}
				}
			}
			log.info("ContentIds {}", contentIds);
		} catch (Exception e) {
			log.error("Parsing error occured!", e);
		}
		return contentIds;
	}
}
