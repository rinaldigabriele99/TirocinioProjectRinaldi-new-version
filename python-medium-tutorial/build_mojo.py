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
PARAMS_KEY = "train_params"
TARGET_KEY = "target_y"
SPLIT_KEY = "train_test_split"
MODEL_KEY = "model"
FOLDS_KEY = "nfolds"
MOJO_KEY = "mojo_path"
##### <===== CONFIGURATION

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
if config[MODEL_KEY] == "GBE":
    model = H2OGradientBoostingEstimator(nfolds=config[FOLDS_KEY], **params)
else:
    sys.exit(f"ERROR: Model {config[MODEL_KEY]} not supported!")

# TRAIN
model.train(x=train_cols, y=target, training_frame=train)
print(model.model_performance(test))

# write mojo to disk with meaningful name
timestamp = datetime.datetime.now().strftime("%Y%m%d%H%M%S")
# name is < model_dataset_y_timestamp >
mojoname = f"{config[MODEL_KEY]}_{Path(config[DATASET_KEY]).stem}_{config[TARGET_KEY]}_{timestamp}"
mojopath = f"{config[MOJO_KEY]}/{config[MODEL_KEY]}"
if not os.path.exists(mojopath):
    os.makedirs(mojopath)
filepath = model.download_mojo(path=mojopath, genmodel_name=f"{mojoname}.jar", get_genmodel_jar=True)
filename = Path(filepath).stem
# rename zip as option below only controls jar name
os.rename(f"{filepath}", f"{mojopath}/{mojoname}.zip")
