/**
 * 
 */
package readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.evt.XMLEventFactory2;

/**
 * @author Samuel Croset
 *
 */
public class MedlineCollectionReader extends CollectionReader_ImplBase{
    /**
     * Name of configuration parameter that must be set to the path of a directory containing input
     * files.
     */
    public static final String PARAM_INPUTDIR = "InputDirectory";


    private ArrayList<File> medlineBigFiles;
    int medlineBigFilesCurrentIndex;

    private ArrayList<String> medlineDocs;
    int medlineDocsCurrentIndex;

    /**
     * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
     */
    public void initialize() throws ResourceInitializationException {

	File directory = new File(((String) getConfigParameterValue(PARAM_INPUTDIR)).trim());
	medlineBigFilesCurrentIndex = 0;
	medlineDocsCurrentIndex = 0;

	// if input directory does not exist or is not a directory, throw exception
	if (!directory.exists() || !directory.isDirectory()) {
	    throw new ResourceInitializationException(ResourceConfigurationException.DIRECTORY_NOT_FOUND,
		    new Object[] { PARAM_INPUTDIR, this.getMetaData().getName(), directory.getPath() });
	}

	medlineBigFiles = new ArrayList<File>();
	medlineDocs = new ArrayList<String>();
	try {
	    addFilesFromDir(directory);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (XMLStreamException e) {
	    e.printStackTrace();
	}
    }

    /**
     * @param dir
     * @throws XMLStreamException 
     * @throws FileNotFoundException 
     */
    private void addFilesFromDir(File dir) throws FileNotFoundException, XMLStreamException {
	File[] files = dir.listFiles();

	for (int i = 0; i < files.length; i++) {
	    if (!files[i].isDirectory()) {

		XMLOutputFactory writerFactory = XMLOutputFactory2.newInstance();
		XMLEventFactory eventFactory = XMLEventFactory2.newInstance();
		XMLInputFactory readerFactory = XMLInputFactory2.newInstance();
		XMLEventReader reader = readerFactory.createXMLEventReader(new FileReader(files[i].getPath()));

		int counter = 0;
		while(reader.hasNext()){
		    XMLEvent event = reader.nextEvent();
		    if(event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("MedlineCitation") && counter < 5){
			StringWriter citationContent = new StringWriter();
			XMLEventWriter writer = writerFactory	.createXMLEventWriter(citationContent);
			StartElement rootElem = eventFactory.createStartElement("","", "Root");
			writer.add(rootElem);
			counter++;
			while(!(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("MedlineCitation"))){
			    event = reader.nextEvent();
			    writer.add(event);
			}
			writer.close();
			medlineDocs.add(citationContent.toString());
		    }
		}
	    }
	}
    }


    /**
     * @see org.apache.uima.collection.CollectionReader#hasNext()
     */
    public boolean hasNext() {
	return medlineDocsCurrentIndex < medlineDocs.size();
    }

    /**
     * @see org.apache.uima.collection.CollectionReader#getNext(org.apache.uima.cas.CAS)
     */
    public void getNext(CAS aCAS) throws IOException, CollectionException {
	JCas jcas;
	try {
	    jcas = aCAS.getJCas();
	} catch (CASException e) {
	    throw new CollectionException(e);
	}

	// open input stream to file
	String medlineDoc = (String) medlineDocs.get(medlineDocsCurrentIndex++);
	// put document in CAS
	jcas.setDocumentText(medlineDoc);


	// Also store location of source document in CAS. This information is critical
	// if CAS Consumers will need to know where the original document contents are located.
	// For example, the Semantic Search CAS Indexer writes this information into the
	// search index that it creates, which allows applications that use the search index to
	// locate the documents that satisfy their semantic queries.
	//TODO Un-comment and implement meta-info here. The type should be declared in the descriptors.
	//      SourceDocumentInformation srcDocInfo = new SourceDocumentInformation(jcas);
	//      srcDocInfo.setUri(file.getAbsoluteFile().toURL().toString());
	//      srcDocInfo.setOffsetInSource(0);
	//      srcDocInfo.setDocumentSize((int) file.length());
	//      srcDocInfo.setLastSegment(mCurrentIndex == mFiles.size());
	//      srcDocInfo.addToIndexes();
    }

    /**
     * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#close()
     */
    public void close() throws IOException {
    }

    /**
     * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#getProgress()
     */
    public Progress[] getProgress() {
	return new Progress[] { new ProgressImpl(medlineDocsCurrentIndex, medlineDocs.size(), Progress.ENTITIES) };
    }

    /**
     * Gets the total number of documents that will be returned by this collection reader. This is not
     * part of the general collection reader interface.
     * 
     * @return the number of documents in the collection
     */
    public int getNumberOfDocuments() {
	return medlineDocs.size();
    }

}
