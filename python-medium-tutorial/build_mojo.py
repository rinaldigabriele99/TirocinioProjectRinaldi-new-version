import h2o
from h2o.estimators.gbm import H2OGradientBoostingEstimator
import json
import sys
import os
import datetime
from pathlib import Path

##### CONFIGURATION PARAMS =====>
CONFIG_FILENAME = 'config.json'

DATASET_KEY = "dataset_path"
PARAMS_KEY = "train_params"  # these are model-specific
TARGET_KEY = "target_y"
SPLIT_KEY = "train_test_split"
MODEL_KEY = "model"
FOLDS_KEY = "nfolds"
MOJO_KEY = "mojo_path"

GBE_SEL = "GBE"
PARAMS_OUT = "_trainparams"
PERFORMANCE = "_performance"
##### <===== CONFIGURATION

MRD_KEY = "mean_residual_deviance"
RMSLE_KEY = "RMSLE"
MAE_KEY = "MAE"
RMSE_KEY = "RMSE"
MSE_KEY = "MSE"

# load configuration
with open(CONFIG_FILENAME) as infile:
    config = json.load(infile)
#print(json.dumps(config, indent=2))

h2o.init()

# load dataset
data = h2o.upload_file(config[DATASET_KEY])

# config train and test
params = config[PARAMS_KEY]
target = config[TARGET_KEY]
train_cols = [x for x in data.col_names if x != target]
train, test = data.split_frame(ratios=[config[SPLIT_KEY], ])

# model selection
if config[MODEL_KEY] == GBE_SEL:
    model = H2OGradientBoostingEstimator(nfolds=config[FOLDS_KEY], **params)
else:  # add more models here, provided they are imported
    sys.exit(f"ERROR: Model {config[MODEL_KEY]} not supported!")

# TRAIN
model.train(x=train_cols, y=target, training_frame=train)
#print(model.model_performance(test))  # TODO this could be also print out to a file and later shown by frontend

# write mojo to disk with meaningful name
timestamp = datetime.datetime.now().strftime("%Y%m%d%H%M%S")
# name is < model_dataset_target_timestamp >
mojoname = f"{config[MODEL_KEY]}_{Path(config[DATASET_KEY]).stem}_{config[TARGET_KEY]}_{timestamp}"
mojopath = f"{config[MOJO_KEY]}/{config[MODEL_KEY]}"
# create dirs structure if not existing
if not os.path.exists(mojopath):
    os.makedirs(mojopath)
filepath = model.download_mojo(path=mojopath, genmodel_name=f"{mojoname}.jar", get_genmodel_jar=True)
# rename zip as option below only controls jar name
filename = Path(filepath).stem
os.rename(f"{filepath}", f"{mojopath}/{mojoname}.zip")

# also write out training params
with open(f'{mojopath}/{mojoname}{PARAMS_OUT}.json', 'w') as params_json:
    json.dump(params, params_json, indent=2)

# also write out performance params
perf_dict = {}
perf_dict["learnt_params"] = {}
perf_dict["performance"] = {}
perf_dict["features_importance"] = {}
if model.type == "regressor":
    metrics = model.model_performance(test)
    perf_dict["performance"][MSE_KEY] = metrics.mse()
    perf_dict["performance"][RMSE_KEY] = metrics.rmse()
    perf_dict["performance"][MAE_KEY] = metrics.mae()
    perf_dict["performance"][RMSLE_KEY] = metrics.rmsle()
    perf_dict["performance"][MRD_KEY] = metrics.mean_residual_deviance()
    for f in model.varimp():
        perf_dict["features_importance"][f[0]] = {}
        perf_dict["features_importance"][f[0]]['relative'] = f[1]
        perf_dict["features_importance"][f[0]]['scaled'] = f[2]
        perf_dict["features_importance"][f[0]]['percentage'] = f[3]

with open(f'{mojopath}/{mojoname}{PERFORMANCE}.json', 'w') as params_json:
    json.dump(perf_dict, params_json, indent=2)
