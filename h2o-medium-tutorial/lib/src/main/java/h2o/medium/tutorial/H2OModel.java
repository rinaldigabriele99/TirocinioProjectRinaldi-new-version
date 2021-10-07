/**
 * 
 */
package h2o.medium.tutorial;

import java.io.IOException;

import hex.ModelCategory;
import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.RegressionModelPrediction;

/**
 * @author sm
 *
 */
public final class H2OModel {
	
	private MojoModel model;
	private EasyPredictModelWrapper predict;
	
	public H2OModel(String modelPath) throws IOException {
		this.model = MojoModel.load(modelPath);
		this.predict = new EasyPredictModelWrapper(this.model);
	}
	
	public double predict(RowData input) {
		try {
			RegressionModelPrediction prediction = predict.predictRegression(input);
			return prediction.value;
		} catch (PredictException e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public String modelCategory() {
			ModelCategory category = predict.getModelCategory();
			return category.toString();
	}
	
	public String[] leafNode(RowData input) throws PredictException {
		return predict.leafNodeAssignment(input);
}

}
