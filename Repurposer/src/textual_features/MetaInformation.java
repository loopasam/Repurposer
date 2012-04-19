

/* First created by JCasGen Thu Apr 19 15:10:36 BST 2012 */
package textual_features;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Apr 19 15:10:36 BST 2012
 * XML source: /home/samuel/git/Repurposer/Repurposer/descriptors/types/TextualFeaturesDescriptor.xml
 * @generated */
public class MetaInformation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MetaInformation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected MetaInformation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public MetaInformation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public MetaInformation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public MetaInformation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: pmid

  /** getter for pmid - gets 
   * @generated */
  public String getPmid() {
    if (MetaInformation_Type.featOkTst && ((MetaInformation_Type)jcasType).casFeat_pmid == null)
      jcasType.jcas.throwFeatMissing("pmid", "textual_features.MetaInformation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MetaInformation_Type)jcasType).casFeatCode_pmid);}
    
  /** setter for pmid - sets  
   * @generated */
  public void setPmid(String v) {
    if (MetaInformation_Type.featOkTst && ((MetaInformation_Type)jcasType).casFeat_pmid == null)
      jcasType.jcas.throwFeatMissing("pmid", "textual_features.MetaInformation");
    jcasType.ll_cas.ll_setStringValue(addr, ((MetaInformation_Type)jcasType).casFeatCode_pmid, v);}    
  }

    