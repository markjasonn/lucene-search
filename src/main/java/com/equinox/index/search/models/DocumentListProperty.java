package com.equinox.index.search.models;

/**
 * POJO binded in Angular 2. Warning! Any change on below instance variables
 * should be reflected in the equivalent Angular class.
 * 
 * @author marianne.rey
 *
 */
public class DocumentListProperty {

	private DocumentListFilter documentListFilter;
	private DocumentListSort documentListSort;
	private Long docCount;
	private Long parentCount;
	private Long attachmentCount;

	public DocumentListFilter getDocumentListFilter() {
		return documentListFilter;
	}

	public void setDocumentListFilter(DocumentListFilter documentListFilter) {
		this.documentListFilter = documentListFilter;
	}

	public DocumentListSort getDocumentListSort() {
		return documentListSort;
	}

	public void setDocumentListSort(DocumentListSort documentListSort) {
		this.documentListSort = documentListSort;
	}

	public Long getDocCount() {
		return docCount;
	}

	public void setDocCount(Long docCount) {
		this.docCount = docCount;
	}

	public Long getParentCount() {
		return parentCount;
	}

	public void setParentCount(Long parentCount) {
		this.parentCount = parentCount;
	}

	public Long getAttachmentCount() {
		return attachmentCount;
	}

	public void setAttachmentCount(Long attachmentCount) {
		this.attachmentCount = attachmentCount;
	}

}
