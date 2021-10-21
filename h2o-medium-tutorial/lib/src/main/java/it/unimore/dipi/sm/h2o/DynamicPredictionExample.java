/**
 * 
 */
package it.unimore.dipi.sm.h2o;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLogger;

import hex.ModelCategory;
import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.AbstractPrediction;
import hex.genmodel.easy.prediction.RegressionModelPrediction;

/**
 * @author sm
 *
 */
public final class DynamicPredictionExample {
	
	private static Logger logger;

	public static final String MOJO_FOLDER = "src/main/resources/";
	private static final String MOJO_EXT = ".zip";
	public static final String TEST_DATA_PATH = "src/main/resources/sample.csv";
	public static final String CSV_SEPARATOR = ",";

	/**
	 * @param args
	 * @throws IOException
	 * @throws PredictException
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, PredictException {
		System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");
		logger = LoggerFactory.getLogger(DynamicPredictionExample.class);
		
		Set<String> mojoPaths = findMojos();
		logger.info("MOJOs found in folder {}: {}", MOJO_FOLDER, mojoPaths);
		List<MojoModel> mojos = loadMojos(mojoPaths);
		logger.info("MOJOs loaded");
		List<EasyPredictModelWrapper> models = wrapMojos(mojos);
		logger.info("Models created");
		List<RowData> testDataSet = loadTestData();
		logger.info("Data loaded");
		Map<EasyPredictModelWrapper, List<AbstractPrediction>> predictionsPerModel = doPredictions(models,
				testDataSet);
		logger.info("Predictions made");
		logPredictions(predictionsPerModel);
	}

	/**
	 * @param predictionsPerModel
	 */
	private static void logPredictions(Map<EasyPredictModelWrapper, List<AbstractPrediction>> predictionsPerModel) {
		ModelCategory modelCat;
		RegressionModelPrediction prediction;
		for (EasyPredictModelWrapper model : predictionsPerModel.keySet()) {
			modelCat = model.getModelCategory();
			for (AbstractPrediction aPrediction : predictionsPerModel.get(model)) {
				switch (modelCat) {
				case Regression:
					prediction = (RegressionModelPrediction) aPrediction;
					logger.info("{} prediction: {}", modelCat, prediction.value);
					break;
				default:
					logger.warn("{} model not currently supported", modelCat);
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
	private static Map<EasyPredictModelWrapper, List<AbstractPrediction>> doPredictions(
			List<EasyPredictModelWrapper> models, List<RowData> testDataSet) throws PredictException {
		Map<EasyPredictModelWrapper, List<AbstractPrediction>> predictionsPerModel = new HashMap<>();
		List<AbstractPrediction> predictions;
		for (EasyPredictModelWrapper model : models) {
			predictions = new ArrayList<>();
			predictionsPerModel.put(model, predictions);
			for (RowData rowData : testDataSet) {
				logger.debug("Predicting row {}", rowData);
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
		String line;
		String[] header;
		String[] row;
		List<RowData> testDataSet = new ArrayList<>();
		RowData testDataRow = new RowData();
		try (BufferedReader csvReader = new BufferedReader(new FileReader(TEST_DATA_PATH))) {
			line = csvReader.readLine();
			header = line.split(CSV_SEPARATOR);
			logger.debug("Dataset header: {}", line);
			while ((line = csvReader.readLine()) != null) {
				logger.debug("Dataset row: {}", line);
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
	private static List<MojoModel> loadMojos(Set<String> mojoPaths) throws IOException {
		List<MojoModel> mojos = new ArrayList<>();
		for (String path : mojoPaths) {
			mojos.add(MojoModel.load(path));
		}
		return mojos;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	private static Set<String> findMojos() throws IOException {
		try (Stream<Path> stream = Files.list(Paths.get(MOJO_FOLDER))) {
	        return stream
	          .filter(file -> !Files.isDirectory(file))
//	          .map(Path::getFileName)
	          .map(Path::toString)
	          .filter(filename -> filename.endsWith(MOJO_EXT))
	          .collect(Collectors.toSet());
	    }
	}

}
