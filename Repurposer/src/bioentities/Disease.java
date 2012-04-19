

/* First created by JCasGen Thu Apr 19 13:12:04 BST 2012 */
package bioentities;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Apr 19 13:12:04 BST 2012
 * XML source: /home/samuel/git/Repurposer/Repurposer/descriptors/types/BioentitiesDescriptor.xml
 * @generated */
public class Disease extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Disease.class);
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
  protected Disease() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Disease(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Disease(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Disease(JCas jcas, int begin, int end) {
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
  //* Feature: name

  /** getter for name - gets 
   * @generated */
  public String getName() {
    if (Disease_Type.featOkTst && ((Disease_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "bioentities.Disease");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Disease_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets  
   * @generated */
  public void setName(String v) {
    if (Disease_Type.featOkTst && ((Disease_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "bioentities.Disease");
    jcasType.ll_cas.ll_setStringValue(addr, ((Disease_Type)jcasType).casFeatCode_name, v);}    
  }

    