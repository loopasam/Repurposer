

/* First created by JCasGen Tue Apr 03 15:18:18 BST 2012 */
package bioentities;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** An active compound, commercialized by a company.
 * Updated by JCasGen Tue Apr 03 15:47:01 BST 2012
 * XML source: /home/samuel/git/Repurposer/Repurposer/descriptors/types/typeSystemDescriptor.xml
 * @generated */
public class Drug extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Drug.class);
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
  protected Drug() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Drug(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Drug(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Drug(JCas jcas, int begin, int end) {
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
  //* Feature: Name

  /** getter for Name - gets The name of the drug
   * @generated */
  public String getName() {
    if (Drug_Type.featOkTst && ((Drug_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "bioentities.Drug");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Drug_Type)jcasType).casFeatCode_name);}
    
  /** setter for Name - sets The name of the drug 
   * @generated */
  public void setName(String v) {
    if (Drug_Type.featOkTst && ((Drug_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "bioentities.Drug");
    jcasType.ll_cas.ll_setStringValue(addr, ((Drug_Type)jcasType).casFeatCode_name, v);}    
  }

    