/**
 * 
 */
package h2o.medium.tutorial;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import hex.genmodel.easy.RowData;
import io.javalin.Javalin;
import io.javalin.core.util.FileUtil;

/**
 * @author sm
 *
 */
public final class ModelEndpoint {

	public static Integer port = 8080;
	public static String modelPath = "src/main/resources/GBM_model_python_1632315299562_4.zip";
	public static String modelPathGLM = "src/main/resources/glm_f62a2764_cf25_4d29_86e1_79f93779e554.zip";
	public static String modelPathDFR = "src/main/resources/drf_47bb7c5b_0398_403b_aac8_305a8aa47972.zip";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		H2OModel model = new H2OModel(modelPath);
		H2OModel model1 = new H2OModel(modelPathGLM);
		H2OModel model2 = new H2OModel(modelPathDFR);
		Javalin endpoint = Javalin.create().start(port);
		endpoint.get("/predict", ctx -> {
			RowData modelParams = new RowData();
			ctx.queryParamMap().forEach((param, value) -> {
				modelParams.put(param, value.get(0));
			});
			double prediction = model.predict(modelParams);
			String category = model.modelCategory();
			//String[] leafNode = model.leafNode(modelParams); 
			double prediction1 = model1.predict(modelParams);
			String category1 = model1.modelCategory();
			//String[] leafNode1 = model1.leafNode(modelParams);
			double prediction2 = model2.predict(modelParams);
			String category2 = model2.modelCategory();
			//String[] leafNode2 = model2.leafNode(modelParams);
			JsonObject resp = new JsonObject();
			resp.addProperty("prediction", prediction);
			resp.addProperty("status", "ok");
			resp.addProperty("modello", "Gbm");
			resp.addProperty("category", category);
			//resp.addProperty("leafNode", leafNode.toString());
			resp.addProperty("prediction1", prediction1);
			resp.addProperty("status1", "ok");
			resp.addProperty("modello1", "Glm");
			resp.addProperty("category1", category1);
			//resp.addProperty("leafNode1", leafNode1.toString());
			resp.addProperty("prediction2", prediction2);
			resp.addProperty("status2", "ok");
			resp.addProperty("modello2", "Drf");
			resp.addProperty("category2", category2);
			//resp.addProperty("leafNode2", leafNode2.toString());
			ctx.result(resp.toString());
		});
		endpoint.post("/upload-files", ctx -> {
			ctx.uploadedFiles("files").forEach(file -> {
			FileUtil.streamToFile(file.getContent(), "upload/" + file.getFilename());
			ArrayList<RowData> list = new ArrayList<RowData>();
			JsonArray resp = new JsonArray();
			String pathToCsv = "upload/" + file.getFilename();
			BufferedReader csvReader = null;
			try {
				csvReader = new BufferedReader(new FileReader(pathToCsv));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String row;
			String[] first_row = null;
			try {
				first_row = csvReader.readLine().split(",");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int c = 0;
			try {
				while ((row = csvReader.readLine()) != null) {
					JsonObject jo = new JsonObject();
					RowData rd = new RowData();
					c++;
					//if (c == 1) continue;
				    String[] data = row.split(",");
				    for (int i=0; i<data.length; i++) {
				    	rd.put(first_row[i], data[i]);
				    }
				    list.add(rd);
				    double prediction = model.predict(rd);
				    double prediction1 = model1.predict(rd);
				    double prediction2 = model.predict(rd);
				    jo.addProperty("gbm", prediction);
				    jo.addProperty("glm", prediction1);
				    jo.addProperty("drf", prediction2);
				    resp.add(jo);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//ctx.json(resp);
			ctx.result(resp.toString());
			});
			//ctx.html("Upload complete");
		});

	}

}
