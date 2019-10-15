package hello;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.BodyContentHandler;

import org.xml.sax.SAXException;


import java.io.File;
import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileWriter;


import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.commons.io.FilenameUtils;






import org.xml.sax.SAXException;



public class ExtractWord {
    File file;
    String path;
    static int exist =0;

    public ExtractWord(File file, String path) throws SQLException, ClassNotFoundException {
        this.file=file;
        this.path=path;

    }


    public File extract() throws IOException,TikaException, SAXException, SQLException {
        String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
        String MYSQL_URL = "jdbc:mysql://localhost:3306/SearchEngine";

        Connection con;
        Statement st;
        con=DriverManager.getConnection(MYSQL_URL, "root", "password");
        st=con.createStatement();
        String sql1 = "CREATE TABLE IF NOT EXISTS METADATA " +
                "(name VARCHAR(255) NOT NULL, " +
                " type VARCHAR(255), " +
                " cerationDate VARCHAR(255), " +
                " PRIMARY KEY ( name ))";
        st.executeUpdate(sql1);
        System.out.println("TABLE METADATA is created");

        int i =0;
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext pcontext = new ParseContext();

        //parsing the document using PDF parser
        OOXMLParser  msofficeparser = new OOXMLParser ();
        msofficeparser.parse(inputstream, handler, metadata,pcontext);


        String newPath="/home/mohamed/linux/2A/PIDR/final/Data/word2txt";
        File newFile=new File(newPath+"/"+ FilenameUtils.removeExtension(file.getName())+".txt");
        newFile.createNewFile();
        FileWriter ffw=new FileWriter(newFile);
        ffw.write(handler.toString());
        ffw.write("\n");
        ffw.write("\n");
        ffw.write("\n");
        ffw.write("\n");
        ffw.write("Metadata of the PDF:\n");
        ffw.write("File Name : "+FilenameUtils.removeExtension(file.getName())+"\n");
        ffw.write("PATH : "+newPath+"/"+file.getName()+"\n");
        String[] metadataNames = metadata.names();

        String nom = FilenameUtils.removeExtension(file.getName());
        String type=null;;
        String date=null;;
        for(String name : metadataNames) {
            ffw.write(name+ " : " + metadata.get(name)+"\n");
            if( i==0 ) {
                type=name;
            }else if( i==6) {
                date=metadata.get(name);
            }
            i++;
        }
       /* String sql="INSERT INTO METADATA " +"VALUES ('"+newPath+"/"+file.getName()+"', '"+type.substring(0,3)+"', '"+date.substring(0,10)+"')";
        System.out.println(sql);
        st.executeUpdate(sql);*/
        ffw.close();
        st.executeQuery("SELECT * FROM METADATA");
        return newFile;




    }
}

