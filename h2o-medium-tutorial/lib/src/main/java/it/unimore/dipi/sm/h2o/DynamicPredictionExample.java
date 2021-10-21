/**
 * 
 */
package it.unimore.dipi.sm.h2o;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hex.ModelCategory;
import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.AbstractPrediction;

/**
 * @author sm
 *
 */
public final class DynamicPredictionExample {
	
	private static final Logger logger = LoggerFactory.getLogger(DynamicPredictionExample.class);

	public static final String CSV_SEPARATOR = ",";
	public static final String[] MOJO_PATHS = { "src/main/resources/GBM_model_python_1632315299562_4.zip",
			"src/main/resources/glm_f62a2764_cf25_4d29_86e1_79f93779e554.zip",
			"src/main/resources/drf_47bb7c5b_0398_403b_aac8_305a8aa47972.zip" };
	public static final String TEST_DATA_PATH = "src/main/resources/sample.csv";
	public static final String PREDICTION_CLASSNAME_TEMPLATE = "hex.genmodel.easy.prediction.%sModelPrediction";

	/**
	 * @param args
	 * @throws IOException
	 * @throws PredictException
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, PredictException, ClassNotFoundException {
		List<MojoModel> mojos = loadMojos();
		List<EasyPredictModelWrapper> models = wrapMojos(mojos);
		List<RowData> testDataSet = loadTestData();
		Map<EasyPredictModelWrapper, List<AbstractPrediction>> predictionsPerModel = predictAbstract(models,
				testDataSet);
		ModelCategory modelCat;
		Class<?> clazz;
		for (EasyPredictModelWrapper model : predictionsPerModel.keySet()) {
			modelCat = model.getModelCategory();
			for (AbstractPrediction aPrediction : predictionsPerModel.get(model)) {
				logger.info("Abstract prediction for model <{}>: {}", model.toString(), aPrediction.toString());
				clazz = Class.forName(String.format(PREDICTION_CLASSNAME_TEMPLATE, modelCat));
				logger.info("\tcorresponding <{}> prediction: {}", clazz.getSimpleName(), clazz.cast(aPrediction).toString());
				switch (key) {
				case value:
					
					break;

				default:
					break;
				}
			}
		}
	}

	/**
	 * @param models
	 * @param testDataSet
	 * @return
	 * @throws PredictException
	 */
	private static Map<EasyPredictModelWrapper, List<AbstractPrediction>> predictAbstract(
			List<EasyPredictModelWrapper> models, List<RowData> testDataSet) throws PredictException {
		Map<EasyPredictModelWrapper, List<AbstractPrediction>> predictionsPerModel = new HashMap<>();
		List<AbstractPrediction> predictions;
		for (EasyPredictModelWrapper model : models) {
			predictions = new ArrayList<>();
			predictionsPerModel.put(model, predictions);
			for (RowData rowData : testDataSet) {
				predictions.add(model.predict(rowData, model.getModelCategory()));
			}
		}
		return predictionsPerModel;
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static List<RowData> loadTestData() throws IOException, FileNotFoundException {
		String[] header;
		String line;
		String[] row;
		List<RowData> testDataSet = new ArrayList<>();
		RowData testDataRow = new RowData();
		try (BufferedReader csvReader = new BufferedReader(new FileReader(TEST_DATA_PATH))) {
			header = csvReader.readLine().split(CSV_SEPARATOR);
			while ((line = csvReader.readLine()) != null) {
				row = line.split(CSV_SEPARATOR);
				for (int i = 0; i < row.length; i++) {
					testDataRow.put(header[i], row[i]);
				}
				testDataSet.add(testDataRow);
			}
		}
		return testDataSet;
	}

	/**
	 * @param mojos
	 * @return
	 */
	private static List<EasyPredictModelWrapper> wrapMojos(List<MojoModel> mojos) {
		List<EasyPredictModelWrapper> models = new ArrayList<>();
		for (MojoModel mojo : mojos) {
			models.add(new EasyPredictModelWrapper(mojo));
		}
		return models;
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private static List<MojoModel> loadMojos() throws IOException {
		List<MojoModel> mojos = new ArrayList<>();
		for (String path : MOJO_PATHS) {
			mojos.add(MojoModel.load(path));
		}
		return mojos;
	}

}
