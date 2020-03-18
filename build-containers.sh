export PATH_ACTOR_REGISTRY=src/actor-registry
export NAME_ACTOR_REGISTRY=actor-registry

export PATH_ACTOR_SIMULATOR=src/actor-simulator
export NAME_ACTOR_SIMULATOR=actor-simulator

export PATH_ACTOR_CONTROL=src/actor-control
export NAME_ACTOR_CONTROL=actor-control

export PATH_STATUS_TRACKING=src/status-tracking
export NAME_STATUS_TRACKING=status-tracking

export PATH_WEB_GUI=src/web-gui
export NAME_WEB_GUI=web-gui

gcloud builds submit $PATH_ACTOR_REGISTRY --tag gcr.io/$PROJECT/$NAME_ACTOR_REGISTRY
#gcloud builds submit $PATH_ACTOR_SIMULATOR --tag gcr.io/$PROJECT/$NAME_ACTOR_SIMULATOR
#gcloud builds submit $PATH_ACTOR_CONTROL --tag gcr.io/$PROJECT/$NAME_ACTOR_CONTROL
#gcloud builds submit $PATH_STATUS_TRACKING --tag gcr.io/$PROJECT/$NAME_STATUS_TRACKING
#gcloud builds submit $PATH_WEB_GUI --tag gcr.io/$PROJECT/$NAME_WEB_GUI


