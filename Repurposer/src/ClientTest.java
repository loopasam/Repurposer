import java.io.IOException;
import java.util.HashMap;

import org.apache.uima.UIMAFramework;
import org.apache.uima.aae.client.UimaAsBaseCallbackListener;
import org.apache.uima.aae.client.UimaAsynchronousEngine;
import org.apache.uima.adapter.jms.client.BaseUIMAAsynchronousEngine_impl;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.EntityProcessStatus;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;



import bioentities.Drug;

/**
 * 
 */

/**
 * @author Samuel Croset
 *
 */
public class ClientTest {

    private static class CallbackListener extends UimaAsBaseCallbackListener {
	@Override
	public void entityProcessComplete(CAS cas, EntityProcessStatus aStatus) {
	    JCas jcas = null;
	    try {
		jcas = cas.getJCas();
	    } catch (CASException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }


	    FSIterator<Annotation> drugIterator = jcas.getAnnotationIndex(Drug.type).iterator();
	    while (drugIterator.hasNext()) {
		Drug drugannot = (Drug) drugIterator.next();
//		System.out.println("Name: " + drugannot.getName());
	    }


	}
    }

    public static void main(String[] args) throws InvalidXMLException, IOException, ResourceInitializationException, ResourceProcessException {
	
	System.out.println("Starting client...");

	
	
	BaseUIMAAsynchronousEngine_impl asClient = new BaseUIMAAsynchronousEngine_impl();

	
//	UimaAsynchronousEngine asClient = new BaseUIMAAsynchronousEngine_impl();
	asClient.addStatusCallbackListener(new CallbackListener());
//
	HashMap<String, Object> context = new HashMap<String, Object>();
	context.put(UimaAsynchronousEngine.ServerUri, "tcp://samuel-Latitude-E6510:61616");
	context.put(UimaAsynchronousEngine.Endpoint, "sam_test");
	context.put(UimaAsynchronousEngine.CasPoolSize, 1);
	context.put(UimaAsynchronousEngine.SerializationStrategy, "binary");

	CollectionReaderDescription collectionReaderDescription = UIMAFramework.getXMLParser()
	.parseCollectionReaderDescription(new XMLInputSource("/home/samuel/git/Repurposer/Repurposer/descriptors/readers/MedlineCollectionReader.xml"));

	CollectionReader collectionReader = UIMAFramework.produceCollectionReader(collectionReaderDescription);

	asClient.setCollectionReader(collectionReader);
	asClient.initialize(context);
	asClient.process();
	asClient.collectionProcessingComplete();
	asClient.stop();

    }
}
