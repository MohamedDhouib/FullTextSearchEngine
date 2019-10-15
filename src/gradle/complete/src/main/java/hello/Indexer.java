package hello;

import java.io.File;


import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import org.apache.commons.io.FilenameUtils;


public class Indexer {
	private hello.ExtractPdf expdf ;
	private hello.ExtractCsv excsv;
	private hello.ExtractWord exword;
   private IndexWriter writer;
   private hello.Searcher searcher;
   private String indexDirectoryPath;

   public Indexer(String indexDirectoryPath) throws IOException {
      Directory indexDirectory = 
         FSDirectory.open(Paths.get(indexDirectoryPath));
      this.indexDirectoryPath=indexDirectoryPath;
      StandardAnalyzer analyzer = new StandardAnalyzer();
      IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
      writer = new IndexWriter(indexDirectory, iwc);
      
   }

   public void close() throws CorruptIndexException, IOException {
      writer.close();
   }

   private Document getDocument(File file) throws IOException {
      Document document = new Document();

      TextField contentField = new TextField(hello.LuceneConst.CONTENTS, new FileReader(file));
      TextField fileNameField = new TextField(hello.LuceneConst.FILE_NAME,
         file.getName(),TextField.Store.YES);
      TextField filePathField = new TextField(hello.LuceneConst.FILE_PATH,
         file.getCanonicalPath(),TextField.Store.YES);

      document.add(contentField);
      document.add(fileNameField);
      document.add(filePathField);

      return document;
   }   

   private void indexFile(File file) throws IOException, ParseException {
      System.out.println("Indexing "+file.getCanonicalPath());
      Document document = getDocument(file);
      //writer.addDocument(document);  
      		writer.commit();
    	  searcher = new hello.Searcher(this.indexDirectoryPath);

    	  TopDocs results = searcher.search(FilenameUtils.removeExtension(file.getName()));
    	  if (results.totalHits == 0){
    		  writer.addDocument(document);
    		  System.out.println("Success : The file "+file.getName()+ " has been indexed" );
    	  }else {
    		  System.out.println("Error : The file "+file.getName()+ " is already indexed" );
    	  }
      
   }

   public int createIndex(String dataDirPath, FileFilter filter) 
      throws IOException, TikaException, SAXException, ParseException, SQLException, ClassNotFoundException {
      File[] files = new File(dataDirPath).listFiles();

      for (File file : files) {
         if(!file.isDirectory()
            && !file.isHidden()
            && file.exists()
            && file.canRead()
            && filter.accept(file)
         ) {
             if (FilenameUtils.isExtension(file.getName(), "pdf")) {
                 expdf = new hello.ExtractPdf(file, dataDirPath);
                 indexFile(expdf.extract());

             }else if(FilenameUtils.isExtension(file.getName(),"csv")) {
                 excsv=new hello.ExtractCsv(file,dataDirPath);
                 indexFile(excsv.extract());
             }else if(FilenameUtils.isExtension(file.getName(),"doc")) {
                exword=new hello.ExtractWord(file,dataDirPath);
                indexFile(exword.extract());
             }else{
        		 indexFile(file);
        	 }
         }
      }
      return writer.numDocs();
   }
} 
