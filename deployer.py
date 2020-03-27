import os
import subprocess


CLUSTER = "dse"
ZONE = "europe-west3-b"
MIN_NODES = 1
MAX_NODES = 3

PATH_ACTOR_REGISTRY = "src/actor-registry"
NAME_ACTOR_REGISTRY = "actor-registry"

PATH_ACTOR_SIMULATOR = "src/actor-simulator"
NAME_ACTOR_SIMULATOR = "actor-simulator"

PATH_ACTOR_CONTROL = "src/actor-control"
NAME_ACTOR_CONTROL = "actor-control"

PATH_STATUS_TRACKING = "src/status-tracking"
NAME_STATUS_TRACKING = "status-tracking"

PATH_API_GATEWAY = "src/api-gateway"
NAME_API_GATEWAY = "api-gateway"

PATH_WEB_GUI = "src/web-gui"
NAME_WEB_GUI = "web-gui"

PROJECT = "dse20-group-20"
PROJECT_TEST = "dse20-group-20-271413"

YAML_DEPLOY_GCP = "deploy-gcp.yaml"
YAML_DEPLOY_GCP_TEST = "deploy-gcp-test.yaml"

GCP_KEY_FILE = "key_gcp.json"
GCP_TEST_KEY_FILE = "key_test_gcp.json"

NAMESPACE = "dse"

def use(project, keyfile):
    subprocess.call(["gcloud", "config", "set", "project", project], shell=True)
    subprocess.call(["gcloud", "auth", "activate-service-account", "--key-file", keyfile ], shell=True)

def deploy(project, yaml_deploy):
    publish(project)
    get_kubectl_access()
    subprocess.call(["kubectl", "apply", "--namespace", NAMESPACE, "-f", yaml_deploy], shell=True)

def publish(project):
     subprocess.call(["gcloud", "builds", "submit", PATH_ACTOR_REGISTRY,  "--tag", "gcr.io/" + project + "/" + NAME_ACTOR_REGISTRY], shell=True)
     subprocess.call(["gcloud", "builds", "submit", PATH_API_GATEWAY,  "--tag", "gcr.io/" + project + "/" + NAME_API_GATEWAY], shell=True)


def deploy_open_api():
    subprocess.call(["gcloud", "endpoints", "services", "deploy", PATH_ACTOR_REGISTRY + "/openapi.yaml"], shell=True)

def get_kubectl_access():
    subprocess.call(["gcloud", "container", "clusters", "get-credentials", CLUSTER, "--zone", ZONE], shell=True)


def deploy_to_local_minikube():
    #subprocess.call(["kubectl", "delete", "deployment", "--all"], shell=True)
    #subprocess.call(["kubectl", "config", "use-context", "minikube"], shell=True)
    #subprocess.call(["kubectl", "config", "set-context", "--namespace=localdev", "docker-for-desktop"])
    #subprocess.call(["kubectl", "config", "set-context", "--namespace=localdev", "minikube"])
    
    # TODO: use own repo
    publish(PROJECT_TEST)

    # SECRET NOT NEEDED ANY MORE B/C REPO IS PUBLIC

    #subprocess.call(["gcloud", "auth", "configure-docker"], shell=True)

    #with open(GCP_TEST_KEY_FILE) as f: # Use file to refer to the file object
    #    key_content = f.read()

    #subprocess.call(["kubectl", "delete", "secret", "gcr-json-key"])
    #subprocess.call(["kubectl", "create", "secret", "docker-registry", "gcr-json-key", "--docker-server=gcr.io", "--docker-username=_json_key", \
    # "--docker-password=" + key_content, "--docker-email=any@valid.email"], shell=True)
    
    #token = (subprocess.check_output(["gcloud", "auth", "print-access-token"], shell=True)).decode("utf-8")
    #subprocess.call(["kubectl", "create", "secret", "docker-registry", "gcr-json-key", "--docker-server=gcr.io", "--docker-username=oauth3accesstoken", \
    # "--docker-password=" + token, "--docker-email=any@valid.email"], shell=True)

    #subprocess.call(["kubectl", "patch", "serviceaccount", "default", "-p", '{"imagePullSecrets": [{"name": "gcr-json-key"}]}'], shell=True)

    subprocess.call(["kubectl", "apply", "--namespace", NAMESPACE, "-f", YAML_DEPLOY_GCP_TEST], shell=True)


def create_gcp_cluster():
    subprocess.call(["gcloud", "container", "clusters", "create", CLUSTER, "--zone", ZONE, "--num-nodes=" + str(MIN_NODES), "--enable-autoscaling", "--max-nodes=" + str(MAX_NODES),"--min-nodes=" + str(MIN_NODES)], shell=True)
    get_kubectl_access()
    subprocess.call(["kubectl", "create", "namespace", NAMESPACE])

def shutdown_gcp_cluster():
    subprocess.call(["gcloud", "container", "clusters", "delete", CLUSTER])
    pass



while (True):
    choice = input("""
    **** PLEASE CHOOSE YOUR OPTION ****
    0) Publish containers to GCP repository
    ---
    1) Deploy to local minikube (includes 0)
    ---
    2) Create GCP cluster
    3) Deploy to GCP (includes 0)
    4) Shut down GCP cluster
    ---
    5) Create test GCP cluster
    6) Publish containers to test GCP
    7) Deploy to test GCP
    ---
    ) Shutdown
    """)

    if (choice == "0"):
        use(PROJECT, GCP_KEY_FILE)
        publish(PROJECT)
    elif (choice == "1"):
        # TODO: use own gcp repo
        use(PROJECT_TEST, GCP_TEST_KEY_FILE)
        deploy_to_local_minikube()
    elif (choice == "2"):
        use(PROJECT, GCP_KEY_FILE)
        create_gcp_cluster()
    elif (choice == "3"):
        use(PROJECT, GCP_KEY_FILE)
        publish(PROJECT)
        deploy(PROJECT, YAML_DEPLOY_GCP)
    elif (choice == "4"):
        use(PROJECT, GCP_KEY_FILE)
        shutdown_gcp_cluster()
    elif (choice == "5"):
        use(PROJECT_TEST, GCP_TEST_KEY_FILE)
        create_gcp_cluster()    
    elif (choice == "6"):
        use(PROJECT_TEST, GCP_TEST_KEY_FILE)
        publish(PROJECT_TEST)
    elif (choice == "7"):
        use(PROJECT_TEST, GCP_TEST_KEY_FILE)
        deploy(PROJECT_TEST, YAML_DEPLOY_GCP_TEST)
    #elif (choice == "8"):
    #    use(PROJECT_TEST, GCP_TEST_KEY_FILE)
    #    deploy_open_api()
    elif (choice == "8"):
        exit()
    else:
        print("try again!")
