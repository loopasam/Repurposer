package annotators;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLOutputFactory2;

import textual_features.Roi;

public class RoiAnnotator extends JCasAnnotator_ImplBase {

	private String[] roiTagNames;
	protected XMLInputFactory inFactory;
	protected XMLOutputFactory outFactory = XMLOutputFactory2.newInstance();

	protected class ParserPosition {

		private StringWriter writer;
		private XMLEventWriter xwriter;

		protected ParserPosition() throws XMLStreamException {

			writer = new StringWriter();
			xwriter = outFactory.createXMLEventWriter(writer);
		}

		protected void update(XMLEvent event) throws XMLStreamException {
			xwriter.add(event);
		}

		protected int getPos() throws XMLStreamException {
			xwriter.flush();
			return writer.getBuffer().length();
		}
	}

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		roiTagNames = (String[]) aContext.getConfigParameterValue("RoiTagNames");

		inFactory = XMLInputFactory2.newInstance();
		inFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
		inFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
		inFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
	}

	protected boolean isROIstart(XMLEvent event) {
		if (!event.isStartElement()) {
			return false;
		} else {
			String tagName = event.asStartElement().getName().getLocalPart();
			for (String roiTagName : roiTagNames) {
				if (tagName.equals(roiTagName))
					return true;
			}
		}
		return false;
	}

	protected boolean isROIend(XMLEvent event) {
		if (!event.isEndElement()) {
			return false;
		} else {
			String tagName = event.asEndElement().getName().getLocalPart();
			for (String roiTagName : roiTagNames) {
				if (tagName.equals(roiTagName))
					return true;
			}
		}
		return false;
	}

	protected void generateParsedView(JCas jcas, String viewName)
			throws XMLStreamException, CASException, IOException {
		StringReader reader = new StringReader(jcas.getDocumentText());
		XMLEventReader parser = inFactory.createXMLEventReader(reader);

		StringWriter writer = new StringWriter();
		XMLEventWriter xwriter = outFactory.createXMLEventWriter(writer);

		while (parser.hasNext()) {
			XMLEvent event = parser.nextEvent();
			if (event.getEventType() != 11) // skip DTD
				xwriter.add(event);
		}
		JCas parsedView = jcas.createView(viewName);
		parsedView.setDocumentLanguage("en");
		parsedView.setDocumentText(writer.toString());
		xwriter.close();
		writer.close();
	}

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		try {
		    generateParsedView(jcas, "text");
			jcas = jcas.getView("text");

			String docText = jcas.getDocumentText();
			StringReader docReader = new StringReader(docText);
			XMLEventReader parser = inFactory.createXMLEventReader(docReader);

			ParserPosition pos = new ParserPosition();
			while (parser.hasNext()) {
				XMLEvent event = parser.nextEvent();
				pos.update(event);

				if (isROIstart(event)) {
					int roiStartPos = pos.getPos() + 1; // xmlwriter doesn't
														// write '>'
					event = parser.nextEvent();
					if (!isROIend(event)) { // not empty element
						do {
							pos.update(event);
							event = parser.nextEvent();
						} while (!isROIend(event));
					} else {
						roiStartPos -= 1; // no '>' before the next event, since
											// element is empty, subtract above
					}
					int roiEndPos = pos.getPos();
					Roi annot = new Roi(jcas);
					annot.setBegin(roiStartPos);
					annot.setEnd(roiEndPos);
					annot.addToIndexes();

					pos.update(event);
				}
			}
			parser.close();
			docReader.close();
		} catch (XMLStreamException e) {
			throw new AnalysisEngineProcessException(e);
		} catch (CASException e) {
			throw new AnalysisEngineProcessException(e);
		} catch (IOException e) {
			throw new AnalysisEngineProcessException(e);
		}
	}

}
