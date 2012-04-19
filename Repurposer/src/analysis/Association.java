/**
 * 
 */
package analysis;

import java.util.ArrayList;

/**
 * @author Samuel Croset
 *
 */
public class Association {
    
    private String drug;
    private String disease;
    private int numberOfObservation;
    private ArrayList<String> pmids;
    
    
    public String getDrug() {
        return drug;
    }
    public void setDrug(String drug) {
        this.drug = drug;
    }
    public String getDisease() {
        return disease;
    }
    public void setDisease(String disease) {
        this.disease = disease;
    }
    public int getNumberOfObservation() {
        return numberOfObservation;
    }
    public void setNumberOfObservation(int numberOfObservation) {
        this.numberOfObservation = numberOfObservation;
    }
    public ArrayList<String> getPmids() {
        return pmids;
    }
    public void setPmids(ArrayList<String> pmids) {
        this.pmids = pmids;
    }
    

}
