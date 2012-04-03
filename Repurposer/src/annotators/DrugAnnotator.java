/**
 * 
 */
package annotators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.Level;

import bioentities.Drug;

/**
 * @author Samuel Croset
 *
 */
public class DrugAnnotator extends JCasAnnotator_ImplBase{
    
    //TODO do the lingpipe dicos
    
    private Pattern mYorktownPattern = Pattern.compile("\\bmicroscope\\b");
    
    /**
     * @see JCasAnnotator_ImplBase#process(JCas)
     */
    public void process(JCas aJCas) {
      // get document text
      String docText = aJCas.getDocumentText();
      Matcher matcher = mYorktownPattern.matcher(docText);
      while (matcher.find()) {
        // found one - create annotation
        Drug annotation = new Drug(aJCas);
        annotation.setBegin(matcher.start());
        annotation.setEnd(matcher.end());
        annotation.setName("Yorktown");
        annotation.addToIndexes();
        getContext().getLogger().log(Level.INFO, "Found: " + annotation);
      }
    }
}
