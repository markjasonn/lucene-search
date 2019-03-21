package com.equinox.index.search.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * POJO binded in Angular 2. Warning! Any change on below instance variables
 * should be reflected in the equivalent Angular class.
 *
 */
public class DocumentItem {
	private long documentObjectId;
	private String documentName;
	private String loadFileDocId;
	private String nativeFileName;
	private String snippet;
	private boolean favorite;
	private boolean hasAnnotations;
	private Date documentDate;

	// WARNING: SECURITY DO NOT CHANGE - Do not exposed.
	@JsonIgnore
	private String documentPath;

	private String libUdvSearchQuery;
	private Integer totalResultCount;
	private String highlightTextContent;

	private List<String> docListCustomView;
	private String docListCustomView_string;
	private String actualFileTypeExtension;
	private String thumbnailJsonData;
	private String mediaType;
	private boolean mediaFlag;
	private String notes;
	private List<String> myLabels;
	private Integer resultIndex;

	// WARNING: SECURITY DO NOT CHANGE - Do not exposed.
	@JsonIgnore
	private String importExtractedTextFileName;

	private String extractedTextContent;
	private String custodian;

	public long getDocumentObjectId() {
		return documentObjectId;
	}

	public void setDocumentObjectId(long documentObjectId) {
		this.documentObjectId = documentObjectId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public boolean isHasAnnotations() {
		return hasAnnotations;
	}

	public void setHasAnnotations(boolean hasAnnotations) {
		this.hasAnnotations = hasAnnotations;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getLibUdvSearchQuery() {
		return libUdvSearchQuery;
	}

	public void setLibUdvSearchQuery(String libUdvSearchQuery) {
		this.libUdvSearchQuery = libUdvSearchQuery;
	}

	public List<String> getDocListCustomView() {
		return docListCustomView;
	}

	public void setDocListCustomView(List<String> docListCustomView) {
		this.docListCustomView = docListCustomView;
	}

	public String getDocListCustomView_string() {
		return docListCustomView_string;
	}

	public void setDocListCustomView_string(String docListCustomView_string) {
		this.docListCustomView_string = docListCustomView_string;
	}

	public String getActualFileTypeExtension() {
		return actualFileTypeExtension;
	}

	public void setActualFileTypeExtension(String actualFileTypeExtension) {
		this.actualFileTypeExtension = actualFileTypeExtension;
	}

	public boolean isMediaFlag() {
		return mediaFlag;
	}

	public void setMediaFlag(boolean mediaFlag) {
		this.mediaFlag = mediaFlag;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getThumbnailJsonData() {
		return thumbnailJsonData;
	}

	public void setThumbnailJsonData(String thumbnailJsonData) {
		this.thumbnailJsonData = thumbnailJsonData;
	}

	public Integer getTotalResultCount() {
		return totalResultCount;
	}

	public void setTotalResultCount(Integer totalResultCount) {
		this.totalResultCount = totalResultCount;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public List<String> getMyLabels() {
		return myLabels;
	}

	public void setMyLabels(List<String> myLabels) {
		this.myLabels = myLabels;
	}

	public String getLoadFileDocId() {
		return loadFileDocId;
	}

	public void setLoadFileDocId(String loadFileDocId) {
		this.loadFileDocId = loadFileDocId;
	}

	public String getNativeFileName() {
		return nativeFileName;
	}

	public void setNativeFileName(String nativeFileName) {
		this.nativeFileName = nativeFileName;
	}

	public Integer getResultIndex() {
		return resultIndex;
	}

	public void setResultIndex(Integer resultIndex) {
		this.resultIndex = resultIndex;
	}

	public String getImportExtractedTextFileName() {
		return importExtractedTextFileName;
	}

	public void setImportExtractedTextFileName(String importExtractedTextFileName) {
		this.importExtractedTextFileName = importExtractedTextFileName;
	}

	public String getExtractedTextContent() {
		return extractedTextContent;
	}

	public void setExtractedTextContent(String extractedTextContent) {
		this.extractedTextContent = extractedTextContent;
	}

	public String getCustodian() {
		return custodian;
	}

	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}

	public String getHighlightTextContent() {
		return highlightTextContent;
	}

	public void setHighlightTextContent(String highlightTextContent) {
		this.highlightTextContent = highlightTextContent;
	}

}
