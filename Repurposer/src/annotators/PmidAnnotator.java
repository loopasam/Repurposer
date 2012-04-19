package annotators;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.codehaus.stax2.XMLInputFactory2;

import textual_features.MetaInformation;


public class PmidAnnotator extends JCasAnnotator_ImplBase {

    private XMLInputFactory inFactory;
    private StringWriter writer = new StringWriter();

    @Override
    public void initialize(UimaContext aContext)
    throws ResourceInitializationException {
	super.initialize(aContext);
	inFactory = XMLInputFactory2.newInstance();
	inFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
	inFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
    }

    protected int getNewPosition(int oldPos, XMLEvent event)
    throws XMLStreamException {
	event.writeAsEncodedUnicode(writer);
	int newPos = oldPos + writer.getBuffer().length();
	writer.getBuffer().setLength(0);
	return newPos;
    }

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {
	try {

	    try {
		jcas = jcas.getView("text");
	    } catch (CASException e) {
		e.printStackTrace();
	    }


	    String docText = jcas.getDocumentText();
	    StringReader docReader = new StringReader(docText);

	    XMLEventReader parser = inFactory.createXMLEventReader(docReader);
	    int pos = 0;
	    while (parser.hasNext()) {
		XMLEvent event = parser.nextEvent();
		pos = getNewPosition(pos, event);

		if (event.isStartElement()
			&& event.asStartElement().getName().getLocalPart()
			.equals("PMID")) {
		    int startPos = pos;
		    event = parser.nextEvent();
		    String pmidString = event.asCharacters().getData();
		    pos = getNewPosition(pos, event);
		    int endPos = pos;
		    MetaInformation annot = new MetaInformation(jcas);
		    annot.setBegin(startPos - 1);
		    annot.setEnd(endPos - 1);
		    annot.setPmid(pmidString);
		    annot.addToIndexes();
		}
	    }
	} catch (XMLStreamException e) {
	    throw new AnalysisEngineProcessException(e);
	}
    }
}
