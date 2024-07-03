package org.sunbird.contentvalidation.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.sunbird.contentvalidation.config.Configuration;
import org.sunbird.contentvalidation.model.contentsearch.sunbirdresp.Child;
import org.sunbird.contentvalidation.model.contentsearch.sunbirdresp.HierarchyResp;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponse;
import org.sunbird.contentvalidation.repo.model.PdfDocValidationResponsePrimaryKey;
import org.sunbird.contentvalidation.service.impl.OutboundRequestHandlerServiceImpl;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
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

	private static final String MIME_TYPE = "mimeType";
	private static final String APPLICATION_PDF = "application/pdf";

	public void insertStartData(String contentId, String fileName) {

		PdfDocValidationResponse pdfResponse = getContentValidationResponseForFile(contentId, fileName);
		log.info("Insert Start Data. Does any response existing ? {} ", pdfResponse != null);
		if (pdfResponse != null) {
			pdfResponse.setCompleted(false);
			pdfResponse.setErrorMessage("");
			pdfResponse.setImageOccurances("");
			pdfResponse.setOverallTextClassification("");
			pdfResponse.setProfanityWordCount(0);
			pdfResponse.setProfanityWordList(null);
			pdfResponse.setScore(0.0);
			pdfResponse.setTotalPageImages(0);
			pdfResponse.setNoOfPagesCompleted(0);
			pdfResponse.setTotalPages(0);
		} else {
			pdfResponse = new PdfDocValidationResponse();
			pdfResponse.setPrimaryKey(
					PdfDocValidationResponsePrimaryKey.builder().contentId(contentId).pdfFileName(fileName).build());
			pdfResponse.setCompleted(false);
		}
		pdfRepo.save(pdfResponse);
	}

	public void updateContentValidationResult(PdfDocValidationResponse newResponse, boolean isCompleted) {
		log.info("inside updateContentValidationResult. newResponse : {}", newResponse.getProfanityWordCount());
		PdfDocValidationResponse responseExisting = getContentValidationResponseForFile(
				newResponse.getPrimaryKey().getContentId(), newResponse.getPrimaryKey().getPdfFileName());
		if (responseExisting != null) {
			responseExisting.setOverallTextClassification(newResponse.getOverallTextClassification());
			responseExisting.setProfanityWordCount(newResponse.getProfanityWordCount());
			responseExisting.setProfanityWordList(newResponse.getProfanityWordList());
			responseExisting.setScore(newResponse.getScore());
			responseExisting.setNoOfPagesCompleted(newResponse.getNoOfPagesCompleted());
			responseExisting.setCompleted(isCompleted);
			responseExisting.setTotalPageImages(newResponse.getTotalPageImages());
			responseExisting.setProfanityWordList(newResponse.getProfanityWordList());
			responseExisting.setImageOccurances(newResponse.getImageOccurances());
			responseExisting.setProfanityImageAnalysisMap(newResponse.getProfanityImageAnalysisMap());
			responseExisting.setIndiaMapClassification(newResponse.getIndiaMapClassification());
			pdfRepo.save(responseExisting);
			log.info("inside updateContentValidationResult. newResponse : {}", responseExisting.getProfanityWordCount());
		} else {
			log.error("Failed to find existing record from latestResponse");
		}
	}

	public PdfDocValidationResponse getContentValidationResponseForFile(String contentId, String pdfFileName) {
		return pdfRepo.findProgressByContentIdAndPdfFileName(contentId, pdfFileName);
	}

	public List<PdfDocValidationResponse> getContentValidationResponse(String rootOrg, String wid, String contentId){
		Map<String, String> identifierAndPdfFileName = getParentAndChildContentId(contentId);
		List<PdfDocValidationResponse> responses = new ArrayList<>();
		identifierAndPdfFileName.forEach((k,v) ->
			responses.add(pdfRepo.findProgressByContentIdAndPdfFileName(k, v)));
		return responses;
	}

	public List<String> getParentAndChildContentIds(String contentId) {
		List<String> contentIds = new ArrayList<>();
		try {
			StringBuilder url = new StringBuilder();
			url.append(configuration.getContentHost()).append(configuration.getHierarchyEndPoint()).append("/" + contentId).append("?mode=edit");
			Map response = mapper.convertValue(requestHandlerService.fetchResult(url.toString()), Map.class);
			if (!ObjectUtils.isEmpty(response.get("result"))) {
				Map<String, Object> result = (Map<String, Object>) response.get("result");
				Map<String, Object> content = (Map<String, Object>) result.get("content");
				if (!CollectionUtils.isEmpty(content)) {
					if (content.get(MIME_TYPE).equals(APPLICATION_PDF))
						contentIds.add(contentId);
					List<Map<String, Object>> children = (List<Map<String, Object>>) content.get("children");
					if (!CollectionUtils.isEmpty(children)) {
						children.forEach(child -> {
							if (!StringUtils.isEmpty(child.get(MIME_TYPE)) && child.get(MIME_TYPE).equals(APPLICATION_PDF)) {
								contentIds.add((String) child.get("identifier"));
							}
						});
					}
				}
			}
		} catch (Exception e) {
			log.error("Parsing error occured!", e);
		}
		return contentIds;
	}

	public Map<String, String> getParentAndChildContentId(String contentId) {
		Map<String, String> contentIdAndFilesName = new HashMap<>();
		try {
			StringBuilder url = new StringBuilder();
			url.append(configuration.getContentHost()).append(configuration.getHierarchyEndPoint()).append("/" + contentId).append("?mode=edit");
			HierarchyResp response = mapper.convertValue(requestHandlerService.fetchResult(url.toString()), HierarchyResp.class);
			if(!ObjectUtils.isEmpty(response.getResult())){
				if(APPLICATION_PDF.equals(response.getResult().getContent().getMediaType())){
				String downloadUrl = response.getResult().getContent().getDownloadUrl();
				if(!StringUtils.isEmpty(downloadUrl)){
					downloadUrl =  downloadUrl.split("/")[7];
					contentIdAndFilesName.put(contentId, downloadUrl);
				}
				}
				if(!CollectionUtils.isEmpty(response.getResult().getContent().getChildren())){
					addValueFromChildren(response.getResult().getContent().getChildren(), contentIdAndFilesName);
				}
			}
		} catch (Exception e) {
			log.error("Parsing error occurred!", e);
		}
		return contentIdAndFilesName;
	}

	private void addValueFromChildren(List<Child> children, Map<String, String> contentIdAndFilesName) {
		if(CollectionUtils.isEmpty(children))
			return;
		for (Child child : children) {
			if (ObjectUtils.isEmpty(child))
				return;
			if (APPLICATION_PDF.equals(child.getMimeType())) {
				String downloadUrl = child.getArtifactUrl();
				if (!StringUtils.isEmpty(downloadUrl)) {
					downloadUrl = downloadUrl.split("/")[7];
					contentIdAndFilesName.put(child.getIdentifier(), downloadUrl);
				}
			}
			addValueFromChildren(child.getChildren(), contentIdAndFilesName);
		}
	}
}
