#
import h2o
from h2o.estimators.gbm import H2OGradientBoostingEstimator

h2o.init()
data = h2o.upload_file("winequality-red.csv")

params = {
    "ntrees": 500,
    "learn_rate": 0.01,
    "max_depth": 8,
    "min_rows": 5,
    "sample_rate": 0.8,
    "col_sample_rate": 0.8
}

target = "quality"
train_cols = [x for x in data.col_names if x != target]
train, test = data.split_frame(ratios=[0.7, ])

model = H2OGradientBoostingEstimator(nfolds=5, **params)
model.train(x=train_cols, y=target, training_frame=train)

print(model.model_performance(test))

model.download_mojo(path="model/", get_genmodel_jar=True)
