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
    private DrugBankDictionary dico;

    /**
     * @see AnalysisComponent#initialize(UimaContext)
     */
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
	super.initialize(aContext);

	String path = (String) aContext.getConfigParameterValue("DrugDictionaryPath");
	dico = new DrugBankDictionary();
	System.out.println("new dico created");
	System.out.println("starts loading...");
	dico.load(path);
	System.out.println("loading done");
    }

    /**
     * @see JCasAnnotator_ImplBase#process(JCas)
     */
    public void process(JCas aJCas) {

	String text = aJCas.getDocumentText();
	System.out.println("creating lingpipe dico");
	ExactDictionaryChunker chunker = new ExactDictionaryChunker(dico.getLingPipeDictionary(), IndoEuropeanTokenizerFactory.INSTANCE, true,false);

	
	Chunking chunking = chunker.chunk(text);
	System.out.println("iterates over the chunks: " + chunking.chunkSet().size());
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
