/**
 * 
 */
package annotators;



import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import textual_features.Roi;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;
import com.aliasi.dict.ExactDictionaryChunker;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;

import dictionaries.DrugBankDictionary;

import bioentities.Drug;

/**
 * @author Samuel Croset
 *
 */
public class DrugAnnotator extends JCasAnnotator_ImplBase{
    /** Map from acronyms to their expanded forms */
    private DrugBankDictionary dico;
    private ExactDictionaryChunker chunker;

    /**
     * @see AnalysisComponent#initialize(UimaContext)
     */
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
		
	super.initialize(aContext);

	String path = (String) aContext.getConfigParameterValue("DrugDictionaryPath");
	dico = new DrugBankDictionary();
	dico.load(path);
	chunker = new ExactDictionaryChunker(dico, IndoEuropeanTokenizerFactory.INSTANCE, true,true);

    }

    /**
     * @see JCasAnnotator_ImplBase#process(JCas)
     */
    public void process(JCas jcas) {

	try {
	    jcas = jcas.getView("text");
	} catch (CASException e) {
	    e.printStackTrace();
	}

	FSIterator<Annotation> it = jcas.getAnnotationIndex(Roi.type).iterator();
	while (it.hasNext()) {
	    Roi roiAnnot = (Roi) it.next();
	    int roiStartPos = roiAnnot.getBegin();
	    String roiText = roiAnnot.getCoveredText();
	    Chunking chunking = chunker.chunk(roiText);
	    for (Chunk chunk : chunking.chunkSet()) {
		int start = chunk.start();
		int end = chunk.end();
		String type = chunk.type();
		Drug annotation = new Drug(jcas);
		annotation.setBegin(roiStartPos + start);
		annotation.setEnd(roiStartPos + end);
		annotation.setName(type);
		annotation.addToIndexes();
	    }
	}

    }

}
