import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import network.Attribute;
import network.Edge;
import network.IntegerAttributeFactory;
import network.Network;
import network.Node;
import network.Relation;
import network.StringAttributeFactory;

/**
 * 
 */

/**
 * @author Samuel Croset
 *
 */
public class GrapherDrugDisease {

    public static void main(String[] args) throws IOException {

	FileInputStream fstream = new FileInputStream("data/report.txt");
	DataInputStream in = new DataInputStream(fstream);
	BufferedReader br = new BufferedReader(new InputStreamReader(in));

	Network network = new Network();

	StringAttributeFactory nameFactory = network.getNewStringAttributeFactory("name");
	StringAttributeFactory typeFactory = network.getNewStringAttributeFactory("type");
	Attribute drugType = typeFactory.getNewAttribute("Drug");
	Attribute diseaseType = typeFactory.getNewAttribute("Disease");

	StringAttributeFactory coOccurenceRelationFactory = network.getNewStringAttributeFactory("co_occurs");
	Attribute coOccursAttribute = coOccurenceRelationFactory.getNewAttribute("co_occurs");
	IntegerAttributeFactory strengthFactory = network.getNewIntegerAttributeFactory("strength");

	network.setIdentifierNodes("name");
	network.setIdentifierEdges("co_occurs");


	String line;

	while ((line = br.readLine()) != null)   {
	    Pattern pattern = Pattern.compile("(.*) associated_with (.*) --> (.*)");
	    Matcher matcher = pattern.matcher(line);
	    boolean matchFound = matcher.find();

	    if (matchFound) {
		int strength = Integer.parseInt(matcher.group(3));
		if(strength > 100){

		    String drugName = matcher.group(1);
		    Node drug = new Node();
		    Attribute drugNameAttribute = nameFactory.getNewAttribute(drugName);
		    drug.addAttribute(drugNameAttribute);
		    drug.addAttribute(drugType);

		    String diseaseName = matcher.group(2);
		    Node disease = new Node();
		    Attribute diseaseNameAttribute = nameFactory.getNewAttribute(diseaseName);
		    disease.addAttribute(diseaseNameAttribute);
		    disease.addAttribute(diseaseType);

		    Edge edge = new Edge();
		    Attribute edgeStrength = strengthFactory.getNewAttribute(strength);
		    edge.addAttribute(edgeStrength);
		    edge.addAttribute(coOccursAttribute);

		    Relation relation = new Relation(drug, edge, disease);
		    network.addRelation(relation);
		}
	    }


	}

	network.saveAll("data/graph", "drud_disease_co_occurence");
	System.out.println("done");
    }

}
