/**
 * 
 */
package annotators;


import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;
import com.aliasi.dict.ExactDictionaryChunker;
import com.aliasi.dict.MapDictionary;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;

import dictionaries.Dictionary;
import dictionaries.DrugBankDictionary;

import bioentities.Drug;

/**
 * @author Samuel Croset
 *
 */
public class DrugAnnotator extends JCasAnnotator_ImplBase{
    /** Map from acronyms to their expanded forms */
    private MapDictionary<String> dico;

    /**
     * @see AnalysisComponent#initialize(UimaContext)
     */
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
	super.initialize(aContext);

	String path = (String) aContext.getConfigParameterValue("DrugDictionaryPath");
	DrugBankDictionary dicodb = new DrugBankDictionary();
	dicodb.load(path);
	dico = dicodb.getLingPipeDictionary();
    }

    /**
     * @see JCasAnnotator_ImplBase#process(JCas)
     */
    public void process(JCas aJCas) {

	String text = aJCas.getDocumentText();
	ExactDictionaryChunker chunker = new ExactDictionaryChunker(dico, IndoEuropeanTokenizerFactory.INSTANCE, true,false);

	
	Chunking chunking = chunker.chunk(text);
	for (Chunk chunk : chunking.chunkSet()) {
	    int start = chunk.start();
	    int end = chunk.end();
	    String type = chunk.type();
	    
	    Drug annotation = new Drug(aJCas);
	    annotation.setBegin(start);
	    annotation.setEnd(end);
	    annotation.setName(type);
	    annotation.addToIndexes();
	    
	}
    }

}
