package hello;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.security.cert.CertPathValidatorException;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;


@Controller
public class Server {

    String result;
    String indexDir = "/home/mohamed/linux/2A/PIDR/final/Index";
    String dataDir = "/home/mohamed/linux/2A//PIDR/final/Data/documents";
    hello.Indexer indexer;
    hello.Searcher searcher;
    String query;
    ArrayList<String> motCle;

    public Server(){
        this.result="";
    }



    @RequestMapping("/")
    public @ResponseBody String greeting2() {
        return "Hello";
    }




    @RequestMapping("/slt")
    public @ResponseBody String search() throws ParseException, SQLException, IOException, InterruptedException, CertPathValidatorException {
        getData();
        System.out.println("methode search");
        String MYSQL_URL = "jdbc:mysql://localhost:3306/SearchEngine";
        result="";
        Connection con;
        Statement st;
        con= DriverManager.getConnection(MYSQL_URL, "root", "password");
        st=con.createStatement();
        String sql3 = "SELECT COUNT(*) AS total FROM key_words";
        ResultSet res =st.executeQuery(sql3);
        String sql4="SELECT * FROM key_words";
        //
        ResultSet res2=st.executeQuery(sql4);
        while (res2.next()){
            search2(res2.getString("word"));
            System.out.println(res2.getString("word"));
        }

        return result;
    }

    @RequestMapping("/getData")
    public @ResponseBody String greeting() {
        return "Hello to the server man";
    }

    public static void getData() throws IOException, SQLException, InterruptedException,CertPathValidatorException {
        String MYSQL_URL = "jdbc:mysql://localhost:3306/SearchEngine?useSSL=false";

        Connection con;
        Statement st;
        con= DriverManager.getConnection(MYSQL_URL, "root", "password");
        st=con.createStatement();
        System.out.println("---------------------connection crée----------");
/*
        HttpURLConnection conn = (HttpURLConnection) new URL("http://localhost:8000/getData").openConnection();
        conn.setReadTimeout(15000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        System.out.println("---------------------connection au serveur"+conn.toString()+"----------");
        //conn.getOutputStream().write("LOGIN".getBytes("UTF-8  "));
        conn.connect();
        String result= "hello from other side";*/
        FileReader input = new FileReader("../../www/file.txt");
        BufferedReader bufRead = new BufferedReader(input);
        String myLine = null;
        String[] words=null;
        while ( (myLine = bufRead.readLine()) != null)
        {
            words = myLine.split(" ");
            // check to make sure you have valid data
        String sql = "Delete from key_words";
        st.executeUpdate(sql);

        }
        for (int i = 0; i <words.length ; i++) {
            String sql = "INSERT INTO key_words (word) VALUES ('" + words[i] + "')";
            st.executeUpdate(sql);
            System.out.println("------------"+words[i]);
        }

            /*InputStream inputstream = conn.getInputStream();
            System.out.println("---------------------connection au serveur----------");

             result = IOUtils.toString(inputstream, StandardCharsets.UTF_8);
            inputstream.close();*/

/*
        int index= result.lastIndexOf(" ");

        result=result.substring(0, index);
        Scanner scanner = new Scanner(result);
        System.out.println(result+"oooooooooooooooooooooooo");

        //System.out.println(result.substring(0, index)+"oooooooooooooo");
        if(!result.equals("Hello to the server")) {
            while (scanner.hasNext()) {
                String sql = "INSERT INTO key_words (word) VALUES ('" + scanner.next() + "')";
                st.executeUpdate(sql);
            }
        }*/

    }
    public void search2(String searchQuery) throws IOException, ParseException, SQLException {
        searcher = new hello.Searcher(indexDir);
        long startTime = System.currentTimeMillis();
        TopDocs hits = searcher.search(searchQuery);
        long endTime = System.currentTimeMillis();
      /*String MYSQL_URL = "jdbc:mysql://localhost:3306/SearchEngine";

      Connection con;
      Statement st;
      con= DriverManager.getConnection(MYSQL_URL, "root", "password");
      st=con.createStatement();
      String sql1 = "CREATE table result"+i+" (word    VARCHAR(300))";
      st.executeUpdate(sql1);*/

        System.out.println(hits.totalHits +
                " documents found. Time :" + (endTime - startTime));
        for(ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            result=result+"File:"+ doc.get(hello.LuceneConst.FILE_PATH)+"\n|";
            System.out.println("File: "+ doc.get(hello.LuceneConst.FILE_PATH));
            //Ajout dans la base de données
       /*  String sql2 = "INSERT INTO result"+i+" (word) VALUES ('"+doc.get(hello.LuceneConst.FILE_PATH)+"')";
         st.executeUpdate(sql2);
*/

        }
        System.out.println("le résultat est :\n"+result+"---\n");

    }


}
