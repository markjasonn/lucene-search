package com.icomteq.index.search.models;


public class LuceneSearchParameter {
	
	private String query;
	private String indexFilePath;
	private String metaQuery;
	private Integer pageNum;
	private Integer docsPerPage;
	private Integer totalDocs;
	private Integer caseID;
	private DocumentListSort sortCondition;
	private DocumentListFilter filterCondition;
	private DocumentListProperty documentListProperty;
	private Long[] sourceFolders;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getIndexFilePath() {
		return indexFilePath;
	}

	public void setIndexFilePath(String indexFilePath) {
		this.indexFilePath = indexFilePath;
	}

	public String getMetaQuery() {
		return metaQuery;
	}

	public void setMetaQuery(String metaQuery) {
		this.metaQuery = metaQuery;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getDocsPerPage() {
		return docsPerPage;
	}

	public void setDocsPerPage(Integer docsPerPage) {
		this.docsPerPage = docsPerPage;
	}

	public Integer getTotalDocs() {
		return totalDocs;
	}

	public void setTotalDocs(Integer totalDocs) {
		this.totalDocs = totalDocs;
	}

	public Integer getCaseID() {
		return caseID;
	}

	public void setCaseID(Integer caseID) {
		this.caseID = caseID;
	}

	public DocumentListSort getSortCondition() {
	    return sortCondition;
	}

	public void setSortCondition(DocumentListSort sortCondition) {
	    this.sortCondition = sortCondition;
	}

	public DocumentListFilter getFilterCondition() {
	    return filterCondition;
	}

	public void setFilterCondition(DocumentListFilter filterCondition) {
	    this.filterCondition = filterCondition;
	}

	public DocumentListProperty getDocumentListProperty() {
	    return documentListProperty;
	}

	public void setDocumentListProperty(DocumentListProperty documentListProperty) {
	    this.documentListProperty = documentListProperty;
	}
	
	public Long[] getSourceFolders() {
		return sourceFolders;
	}

	public void setSourceFolders(Long[] sourceFolders) {
		this.sourceFolders = sourceFolders;
	}
	
	
}
