/**
 * 
 */
package analysis;

import java.util.ArrayList;

/**
 * @author Samuel Croset
 *
 */
public class AssociationReport {

    private ArrayList<Association> associations;

    /**
     * 
     */
    public AssociationReport() {
	this.associations = new ArrayList<Association>();
    }


    public ArrayList<Association> getAssociations() {
	return associations;
    }

    public void setAssociations(ArrayList<Association> associations) {
	this.associations = associations;
    }


    /**
     * @param drugName
     * @param diseaseName
     * @param pmid 
     */
    public void addAssociation(String drugName, String diseaseName, String pmid) {
	boolean exists = false;
	for (Association association : this.associations) {
	    if(association.getDrug().equals(drugName) && association.getDisease().equals(diseaseName)){

		if(association.getPmids().contains(pmid)){
		    exists = true;
		}else{
		    int before = association.getNumberOfObservation();
		    association.setNumberOfObservation(before + 1);
		    association.getPmids().add(pmid);
		    exists = true;
		}
	    }
	}
	if(exists == false){
	    Association association = new Association();
	    association.setDrug(drugName);
	    association.setDisease(diseaseName);
	    ArrayList<String> pmids = new ArrayList<String>();
	    association.setNumberOfObservation(1);
	    pmids.add(pmid);
	    association.setPmids(pmids);
	    this.associations.add(association);
	}
    }

}
