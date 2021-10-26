#
from distutils.command.config import config

import h2o
from h2o.estimators.gbm import H2OGradientBoostingEstimator
import json
import sys

CONFIG_FILENAME = 'config.json'

DATASET_KEY = "dataset_path"
PARAMS_KEY = "train_params"
TARGET_KEY = "target_y"
SPLIT_KEY = "train_test_split"
MODEL_KEY = "model"
FOLDS_KEY = "nfolds"
MOJO_KEY = "mojo_path"

with open(CONFIG_FILENAME) as infile:
    config = json.load(infile)
#print(json.dumps(config, indent=2))

h2o.init()
data = h2o.upload_file(config[DATASET_KEY])

params = config[PARAMS_KEY]

target = config[TARGET_KEY]
train_cols = [x for x in data.col_names if x != target]
train, test = data.split_frame(ratios=[config[SPLIT_KEY], ])

if config[MODEL_KEY] == "GBE":
    model = H2OGradientBoostingEstimator(nfolds=config[FOLDS_KEY], **params)
else:
    sys.exit(f"ERROR: Model {config[MODEL_KEY]} not supported!")

model.train(x=train_cols, y=target, training_frame=train)

print(model.model_performance(test))

model.download_mojo(path=config[MOJO_KEY], get_genmodel_jar=True)
