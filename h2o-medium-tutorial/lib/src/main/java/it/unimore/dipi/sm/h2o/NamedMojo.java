/**
 * 
 */
package it.unimore.dipi.sm.h2o;

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
public final class NamedMojo {

	private final String name;
	private MojoModel mojo;
	private EasyPredictModelWrapper model;

	/**
	 * @param name
	 * @param mojo
	 * @param model
	 */
	public NamedMojo(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @param mojo
	 * @return
	 */
	public NamedMojo setMojo(MojoModel mojo) {
		this.mojo = mojo;
		return this;
	}
	
	public NamedMojo setModel(EasyPredictModelWrapper model) {
		this.model = model;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the mojo
	 */
	public MojoModel getMojo() {
		return mojo;
	}

	/**
	 * @return the model
	 */
	public EasyPredictModelWrapper getModel() {
		return model;
	}

	@Override
	public String toString() {
		return String.format("NamedMojo [name=%s, mojo=%s, model=%s]", name, mojo, model);
	}

	/**
	 * 
	 * @param rowData
	 * @param modelCategory
	 * @return
	 * @throws PredictException 
	 */
	public AbstractPrediction predict(RowData rowData, ModelCategory modelCategory) throws PredictException {
		return model.predict(rowData, modelCategory);
	}

	public ModelCategory getModelCategory() {
		return model.getModelCategory();
	}

}
