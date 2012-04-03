
/* First created by JCasGen Tue Apr 03 15:18:18 BST 2012 */
package type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** An active compound, commercialized by a company.
 * Updated by JCasGen Tue Apr 03 15:18:18 BST 2012
 * @generated */
public class Drug_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Drug_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Drug_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Drug(addr, Drug_Type.this);
  			   Drug_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Drug(addr, Drug_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Drug.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("type.Drug");
 
  /** @generated */
  final Feature casFeat_Name;
  /** @generated */
  final int     casFeatCode_Name;
  /** @generated */ 
  public String getName(int addr) {
        if (featOkTst && casFeat_Name == null)
      jcas.throwFeatMissing("Name", "type.Drug");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Name);
  }
  /** @generated */    
  public void setName(int addr, String v) {
        if (featOkTst && casFeat_Name == null)
      jcas.throwFeatMissing("Name", "type.Drug");
    ll_cas.ll_setStringValue(addr, casFeatCode_Name, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Drug_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Name = jcas.getRequiredFeatureDE(casType, "Name", "uima.cas.String", featOkTst);
    casFeatCode_Name  = (null == casFeat_Name) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Name).getCode();

  }
}



    