

/* First created by JCasGen Tue Apr 17 12:45:44 BST 2012 */
package textual_features;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Apr 17 12:45:44 BST 2012
 * XML source: /home/samuel/git/Repurposer/Repurposer/descriptors/types/TextualFeaturesDescriptor.xml
 * @generated */
public class Roi extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Roi.class);
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
  protected Roi() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Roi(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Roi(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Roi(JCas jcas, int begin, int end) {
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
     
}

    