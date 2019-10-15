package hello;

import
        java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException; 

import au.com.bytecode.opencsv.CSVReader;


public class CSVTikaParser implements Parser {
    private XHTMLContentHandler fXHTML;

    private static final Set<MediaType> SUPPORTED_TYPES =
        Collections.singleton(MediaType.text("csv"));

    public static final String CSV_MIME_TYPE = "text/csv";

    public Set<MediaType> getSupportedTypes(ParseContext context) {
        return SUPPORTED_TYPES;
    }

    public void parse(
            InputStream stream, ContentHandler handler,
            Metadata metadata, ParseContext context)
            throws IOException, SAXException, TikaException {
        parse(stream,handler,metadata,context,null);

    }




        public void parse(
            InputStream stream, ContentHandler handler,
            Metadata metadata, ParseContext context, BasicFileAttributes attr)
    throws IOException, SAXException, TikaException {

        fXHTML = new XHTMLContentHandler(handler, metadata);

        BufferedReader lBufferedReader = new BufferedReader(new InputStreamReader(stream));

        CSVReader lReader = new CSVReader(lBufferedReader, detectSeparators(lBufferedReader));
        List<String[]> lCSVEntries = lReader.readAll();

        getTable(lCSVEntries);

        metadata.set("Type","csv");
        metadata.set("Date",attr.creationTime().toString());

        lBufferedReader.close();
    }

    private char detectSeparators(BufferedReader pBuffered) throws IOException {
        int lMaxValue = 0;
        char lCharMax = ',';
        pBuffered.mark(2048);

        ArrayList<Separators> lSeparators = new ArrayList<Separators>();
        lSeparators.add(new Separators(','));
        lSeparators.add(new Separators(';'));
        lSeparators.add(new Separators('\t'));

        Iterator<Separators> lIterator = lSeparators.iterator();
        while (lIterator.hasNext()) {
            Separators lSeparator = lIterator.next();
            CSVReader lReader = new CSVReader(pBuffered, lSeparator.getSeparator());
            String[] lLine;
            lLine = lReader.readNext();
            lSeparator.setCount(lLine.length);

            if (lSeparator.getCount() > lMaxValue) {
                lMaxValue = lSeparator.getCount();
                lCharMax = lSeparator.getSeparator();
            }
            pBuffered.reset();
        }

        return lCharMax;
    }

    private void getTable(List<String[]> pCSVContent) throws SAXException 
    {
        Iterator<String[]> lIterator = pCSVContent.iterator();
        
        fXHTML.startDocument();
        fXHTML.startElement("table");
        while (lIterator.hasNext()) {
            fXHTML.startElement("tr");
            getRows(lIterator.next());
            fXHTML.endElement("tr");
        }
        fXHTML.endElement("table");
        fXHTML.endDocument();
    }
    
    private void getRows(String[] pRow) throws SAXException
    {
        for (String lRowValue: pRow) {
            fXHTML.startElement("td");
            fXHTML.characters(lRowValue);
            fXHTML.endElement("td");
        }
    }


    public void parse(
            InputStream stream, ContentHandler handler, Metadata metadata)
    throws IOException, SAXException, TikaException {
        parse(stream, handler, metadata, new ParseContext());
    }
    
    
    private class Separators {
        private char fSeparatorChar;
        private int  fFieldCount;
        
        public Separators(char pSeparator) {
            fSeparatorChar = pSeparator;
        }
        public void setSeparator(char pSeparator) {
            fSeparatorChar = pSeparator;
        }
        
        public void setCount(int pCount) {
            fFieldCount = pCount;
        }
        public char getSeparator() {
            return fSeparatorChar;
        }
        public int getCount() {
            return fFieldCount;
        }        
    }

}
