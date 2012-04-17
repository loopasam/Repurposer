/**
 * 
 */
package annotators;

import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;
import com.aliasi.sentences.MedlineSentenceModel;
import com.aliasi.sentences.SentenceChunker;
import com.aliasi.sentences.SentenceModel;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;

import textual_features.Roi;
import textual_features.Sentence;

/**
 * @author Samuel Croset
 *
 */
public class SentenceAnnotator extends JCasAnnotator_ImplBase{

    private SentenceChunker SENTENCE_CHUNKER;

    /**
     * @see AnalysisComponent#initialize(UimaContext)
     */
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
	super.initialize(aContext);

	TokenizerFactory TOKENIZER_FACTORY = IndoEuropeanTokenizerFactory.INSTANCE;
	SentenceModel SENTENCE_MODEL = new MedlineSentenceModel();
	SENTENCE_CHUNKER = new SentenceChunker(TOKENIZER_FACTORY, SENTENCE_MODEL);

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

	    Chunking chunking = SENTENCE_CHUNKER.chunk(roiText.toCharArray(), 0, roiText.length());
	    Set<Chunk> sentences = chunking.chunkSet();

	    for (Chunk sentence : sentences) {
		Sentence sentenceAnnotation = new Sentence(jcas);
		int start = sentence.start();
		int end = sentence.end();
		sentenceAnnotation.setBegin(roiStartPos + start);
		sentenceAnnotation.setEnd(roiStartPos + end);
		sentenceAnnotation.addToIndexes();
	    }
	}
    }

}
