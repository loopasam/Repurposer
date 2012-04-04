/**
 * 
 */
package annotators;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.tutorial.RoomNumber;
import org.apache.uima.util.Level;

import resources.Dictionary;

import bioentities.Drug;

/**
 * @author Samuel Croset
 *
 */
public class DrugAnnotator extends JCasAnnotator_ImplBase{
    /** Map from acronyms to their expanded forms */
    private Dictionary mMap;

    /**
     * @see AnalysisComponent#initialize(UimaContext)
     */
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
      super.initialize(aContext);
      // get a reference to the String Map Resource
      try {
        mMap = (Dictionary) getContext().getResourceObject("DrugDictionary");
      } catch (ResourceAccessException e) {
        throw new ResourceInitializationException(e);
      }
    }

    /**
     * @see JCasAnnotator_ImplBase#process(JCas)
     */
    public void process(JCas aJCas) {
      // go through document word-by-word
      String text = aJCas.getDocumentText();
      
     
      Pattern mYorktownPattern = Pattern.compile(mMap.get("STM"));
      Matcher matcher = mYorktownPattern.matcher(text);
      while (matcher.find()) {
        // found one - create annotation
        Drug annotation = new Drug(aJCas);
        annotation.setBegin(matcher.start());
        annotation.setEnd(matcher.end());
        annotation.setName("Yorktown");
        annotation.addToIndexes();
      }

      
      
      int pos = 0;
      StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r.<.>/?\";:[{]}\\|=+()!", true);
      while (tokenizer.hasMoreTokens()) {
        String token = tokenizer.nextToken();
        // look up token in map to see if it is an acronym
        String expandedForm = mMap.get(token);
        if (expandedForm != null) {
          // create annotation
          Drug annot = new Drug(aJCas);
          annot.setName(expandedForm);
          annot.addToIndexes();
        }
        // incrememnt pos and go to next token
        pos += token.length();
      }
    }

}
