
/* First created by JCasGen Thu Apr 19 15:10:36 BST 2012 */
package textual_features;

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

/** 
 * Updated by JCasGen Thu Apr 19 15:10:36 BST 2012
 * @generated */
public class MetaInformation_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MetaInformation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MetaInformation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MetaInformation(addr, MetaInformation_Type.this);
  			   MetaInformation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MetaInformation(addr, MetaInformation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MetaInformation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("textual_features.MetaInformation");
 
  /** @generated */
  final Feature casFeat_pmid;
  /** @generated */
  final int     casFeatCode_pmid;
  /** @generated */ 
  public String getPmid(int addr) {
        if (featOkTst && casFeat_pmid == null)
      jcas.throwFeatMissing("pmid", "textual_features.MetaInformation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_pmid);
  }
  /** @generated */    
  public void setPmid(int addr, String v) {
        if (featOkTst && casFeat_pmid == null)
      jcas.throwFeatMissing("pmid", "textual_features.MetaInformation");
    ll_cas.ll_setStringValue(addr, casFeatCode_pmid, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public MetaInformation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_pmid = jcas.getRequiredFeatureDE(casType, "pmid", "uima.cas.String", featOkTst);
    casFeatCode_pmid  = (null == casFeat_pmid) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_pmid).getCode();

  }
}



    