package hello;

import java.io.File;

import java.io.FileFilter;

public class TextFileFilter implements FileFilter {

   @Override
   public boolean accept(File pathname) {
      return ((pathname.getName().toLowerCase().endsWith(".csv"))||(pathname.getName().toLowerCase().endsWith(".pdf"))||(pathname.getName().toLowerCase().endsWith(".doc")));
   }

}
