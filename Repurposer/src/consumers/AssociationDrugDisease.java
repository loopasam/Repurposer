/**
 * 
 */
package consumers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

import textual_features.MetaInformation;
import textual_features.Roi;
import bioentities.Disease;
import bioentities.Drug;

import com.aliasi.chunk.Chunk;
import com.aliasi.chunk.Chunking;
import com.aliasi.dict.ExactDictionaryChunker;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;

import dictionaries.DrugBankDictionary;

/**
 * @author Samuel Croset
 *
 */
public class AssociationDrugDisease extends JCasAnnotator_ImplBase {




    /**
     * @see JCasAnnotator_ImplBase#process(JCas)
     */
    public void process(JCas jcas) {

	//	try {
	//	    jcas = jcas.getView("text");
	//	} catch (CASException e) {
	//	    e.printStackTrace();
	//	}

	BufferedWriter out = null;
	try {
	    out = new BufferedWriter(new FileWriter("data/outfilename.txt"));
	} catch (IOException e1) {
	    e1.printStackTrace();
	}

	FSIterator<Annotation> it = jcas.getAnnotationIndex(MetaInformation.type).iterator();
	while(it.hasNext()){
	    MetaInformation meta = (MetaInformation) it.next();
	    try {
		out.write(meta.getPmid());
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    System.out.println("meta: " + meta.getPmid());
	}

	try {
	    out.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}


    }
}
