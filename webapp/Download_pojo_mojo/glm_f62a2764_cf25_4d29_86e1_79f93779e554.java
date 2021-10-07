/*
  Licensed under the Apache License, Version 2.0
    http://www.apache.org/licenses/LICENSE-2.0.html

  AUTOGENERATED BY H2O at 2021-09-29T11:35:21.661+02:00
  3.32.1.7
  
  Standalone prediction code with sample test data for GLMModel named glm_f62a2764_cf25_4d29_86e1_79f93779e554

  How to download, compile and execute:
      mkdir tmpdir
      cd tmpdir
      curl http://192.168.1.150:54321/3/h2o-genmodel.jar > h2o-genmodel.jar
      curl http://192.168.1.150:54321/3/Models.java/glm-f62a2764-cf25-4d29-86e1-79f93779e554 > glm_f62a2764_cf25_4d29_86e1_79f93779e554.java
      javac -cp h2o-genmodel.jar -J-Xmx2g -J-XX:MaxPermSize=128m glm_f62a2764_cf25_4d29_86e1_79f93779e554.java

     (Note:  Try java argument -XX:+PrintCompilation to show runtime JIT compiler behavior.)
*/
import java.util.Map;
import hex.genmodel.GenModel;
import hex.genmodel.annotations.ModelPojo;

@ModelPojo(name="glm_f62a2764_cf25_4d29_86e1_79f93779e554", algorithm="glm")
public class glm_f62a2764_cf25_4d29_86e1_79f93779e554 extends GenModel {
  public hex.ModelCategory getModelCategory() { return hex.ModelCategory.Regression; }

  public boolean isSupervised() { return true; }
  public int nfeatures() { return 11; }
  public int nclasses() { return 1; }

  // Names of columns used by model.
  public static final String[] NAMES = NamesHolder_glm_f62a2764_cf25_4d29_86e1_79f93779e554.VALUES;

  // Column domains. The last array contains domain of response column.
  public static final String[][] DOMAINS = new String[][] {
    /* fixed acidity */ null,
    /* volatile acidity */ null,
    /* citric acid */ null,
    /* residual sugar */ null,
    /* chlorides */ null,
    /* free sulfur dioxide */ null,
    /* total sulfur dioxide */ null,
    /* density */ null,
    /* pH */ null,
    /* sulphates */ null,
    /* alcohol */ null,
    /* quality */ null
  };
  // Prior class distribution
  public static final double[] PRIOR_CLASS_DISTRIB = null;
  // Class distribution used for model building
  public static final double[] MODEL_CLASS_DISTRIB = null;

  public glm_f62a2764_cf25_4d29_86e1_79f93779e554() { super(NAMES,DOMAINS,"quality"); }
  public String getUUID() { return Long.toString(7834260851879503488L); }

  // Pass in data in a double[], pre-aligned to the Model's requirements.
  // Jam predictions into the preds[] array; preds[0] is reserved for the
  // main prediction (class for classifiers or value for regression),
  // and remaining columns hold a probability distribution for classifiers.
  public final double[] score0( double[] data, double[] preds ) {
    final double [] b = BETA.VALUES;
    for(int i = 0; i < 0; ++i) if(Double.isNaN(data[i])) data[i] = CAT_MODES.VALUES[i];
    for(int i = 0; i < 11; ++i) if(Double.isNaN(data[i + 0])) data[i+0] = NUM_MEANS.VALUES[i];
    double eta = 0.0;
    for(int i = 0; i < CATOFFS.length-1; ++i) {
      int ival = (int)data[i];
      if(ival != data[i]) throw new IllegalArgumentException("categorical value out of range");
      ival += CATOFFS[i];
      if(ival < CATOFFS[i + 1])
        eta += b[ival];
    }
    for(int i = 0; i < b.length-1-0; ++i)
    eta += b[0+i]*data[i];
    eta += b[b.length-1]; // reduce intercept
    double mu = hex.genmodel.GenModel.GLM_identityInv(eta);
    preds[0] = mu;
    return preds;
  }
    public static class BETA implements java.io.Serializable {
      public static final double[] VALUES = new double[12];
      static {
        BETA_0.fill(VALUES);
      }
      static final class BETA_0 implements java.io.Serializable {
        static final void fill(double[] sa) {
          sa[0] = 0.023054742531205416;
          sa[1] = -1.079861165052899;
          sa[2] = -0.171069236312473;
          sa[3] = 0.015355297251481022;
          sa[4] = -1.869185073591647;
          sa[5] = 0.004274676319029101;
          sa[6] = -0.0032398723608033425;
          sa[7] = -16.453965956195933;
          sa[8] = -0.41493621202994335;
          sa[9] = 0.910736002591971;
          sa[10] = 0.27700843116084345;
          sa[11] = 20.55542313407043;
        }
      }
}
// Imputed numeric values
    static class NUM_MEANS implements java.io.Serializable {
      public static final double[] VALUES = new double[11];
      static {
        NUM_MEANS_0.fill(VALUES);
      }
      static final class NUM_MEANS_0 implements java.io.Serializable {
        static final void fill(double[] sa) {
          sa[0] = 8.31963727329581;
          sa[1] = 0.5278205128205126;
          sa[2] = 0.27097560975609775;
          sa[3] = 2.5388055034396495;
          sa[4] = 0.08746654158849282;
          sa[5] = 15.874921826141334;
          sa[6] = 46.46779237023139;
          sa[7] = 0.996746679174484;
          sa[8] = 3.311113195747341;
          sa[9] = 0.6581488430268919;
          sa[10] = 10.422983114446529;
        }
      }
}
// Imputed categorical values.
    static class CAT_MODES implements java.io.Serializable {
      public static final int[] VALUES = new int[0];
      static {
      }
}
    // Categorical Offsets
    public static final int[] CATOFFS = {0};
}
// The class representing training column names
class NamesHolder_glm_f62a2764_cf25_4d29_86e1_79f93779e554 implements java.io.Serializable {
  public static final String[] VALUES = new String[11];
  static {
    NamesHolder_glm_f62a2764_cf25_4d29_86e1_79f93779e554_0.fill(VALUES);
  }
  static final class NamesHolder_glm_f62a2764_cf25_4d29_86e1_79f93779e554_0 implements java.io.Serializable {
    static final void fill(String[] sa) {
      sa[0] = "fixed acidity";
      sa[1] = "volatile acidity";
      sa[2] = "citric acid";
      sa[3] = "residual sugar";
      sa[4] = "chlorides";
      sa[5] = "free sulfur dioxide";
      sa[6] = "total sulfur dioxide";
      sa[7] = "density";
      sa[8] = "pH";
      sa[9] = "sulphates";
      sa[10] = "alcohol";
    }
  }
}
