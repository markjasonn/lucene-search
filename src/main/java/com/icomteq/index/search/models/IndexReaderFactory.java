package com.icomteq.index.search.models;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

public class IndexReaderFactory {
	public static IndexReader openReader(String indexFilePath) throws CorruptIndexException, IOException {
		File file = new File(indexFilePath);
		
		return IndexReader.open(FSDirectory.open(file));
	}
}
