package hello;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
@SpringBootApplication

public class LuceneTester {

   String indexDir = "/home/mohamed/linux/2A/PIDR/final/Index/";
   String dataDir = "/home/mohamed/linux/2A/PIDR/final/Data/documents";
   hello.Indexer indexer;
   hello.Searcher searcher;
   //static hello.DataBase db;
   private static ArrayList<String> motCle ;
	

   public static void main(String[] args) throws TikaException, SAXException, SQLException, ClassNotFoundException {
      SpringApplication.run(LuceneTester.class, args);
      LuceneTester tester;
      hello.Server server;
      motCle= new ArrayList<>();
      //db= new hello.DataBase();
      try {
         tester = new LuceneTester();
         server=new hello.Server();
         tester.createIndex();
         /*String MYSQL_URL = "jdbc:mysql://localhost:3306/SearchEngine";

         Connection con;
         Statement st;
         con= DriverManager.getConnection(MYSQL_URL, "root", "password");
         st=con.createStatement();
         String sql1 = "CREATE table key_words(word    VARCHAR(100))";
         st.executeUpdate(sql1);
         String sql2 = "INSERT INTO key_words (word) VALUES ('hello*'),('qsd')";
         st.executeUpdate(sql2);*/
         /*
         motCle.add("hello*");
         motCle.add("qsd");
         server.search(motCle);*/
        // hello.Server.getData();
         //server.search();

        /* String sql3 = "SELECT COUNT(*) AS total FROM key_words";
         ResultSet res =st.executeQuery(sql3);*/
         /*String sql4="SELECT * FROM key_words";
        //
         int cont=1;
         if(res.next()){
            System.out.println("On a "+res.getInt("total")+" à cherhcer dans la base de données");
         }
         ResultSet res2=st.executeQuery(sql4);
         while (res2.next()){
            gt.search(res2.getString("word")/*,cont);
         //gt.search("world");
            cont++;

         }*/

      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }

   private void createIndex() throws IOException, TikaException, SAXException, ParseException, SQLException, ClassNotFoundException {
      indexer = new hello.Indexer(indexDir);
      int numIndexed;
      long startTime = System.currentTimeMillis();  
      numIndexed = indexer.createIndex(dataDir, new hello.TextFileFilter());
      long endTime = System.currentTimeMillis();
      indexer.close();
      System.out.println(numIndexed+" File indexed, time taken: "
         +(endTime-startTime)+" ms");    
   }






}
