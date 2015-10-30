#!/bin/bash
#   ______________________________________________________
#   
#   This script is responsible for building the 
#   application and create a docker 
#   image for every module of the application
#   ______________________________________________________
#
#   Author : Lonceny MARA (@mlonceny)
#
SCRIPT_BASE_DIR="$(cd $(dirname $0) && pwd)"
ACTIVEMQ_DIR="${SCRIPT_BASE_DIR}/middlewares/activemq"
APACHE2_DIR="${SCRIPT_BASE_DIR}/middlewares/apache2"
JBOSS_DIR="${SCRIPT_BASE_DIR}/middlewares/jboss"
POSTGRESQL_DIR="${SCRIPT_BASE_DIR}/middlewares/postgresql"
APP_VERSION=1.0

usage(){
    echo "Usage: "$0
}

_log(){
    now=$(date +"%d/%m/%y-%H:%M:%S")
    case $1 in
	1) shift && echo "[INFO] - $@";;
	2) shift && echo "[WARN] - $@";;
	3) shift && echo "[ERROR] - $@";;	
    esac
}

info(){
    _log 1 $@
}

warn(){
    _log 2 $@
}

error(){
    _log 3 $@
    exit 1
}

execute(){
    info "running command [$@]"
    "$@"
    return_code=$?    
    if [ $return_code -eq 0 ]
    then
	info "command [$@] successfully executed"
    else
	error "command [$@] failed"
    fi
}

get_image_tag(){
    image_pattern=$1
    echo $(docker images | grep ${image_pattern} | awk '{print $3}')
}

rm_docker_image(){
    for image in $(docker images | grep $1 | awk '{print $1}')
    do
        info "removing docker image $image"
	docker rmi ${image}
    done
}

publish_to_dokerhub(){
    image_name=$1    
    #execute docker push ${image_name}:${APP_VERSION}
    execute docker push ilsregistry/${image_name}
}

build_dokerfile() {
    app_name=$1
    execute docker build -t ${app_name} .
}

build_and_publish_image(){
    image_name=$1
    rm_docker_image ${image_name}
    build_dokerfile ${image_name}
    publish_to_dokerhub ${image_name}    
}

build_and_publish_jboss(){
    # create a docker image for jboss
    # first copy the ear file here
    execute cp ${SCRIPT_BASE_DIR}/ils-ear/target/ils-ear-*.ear .
    # TODO
    cd ${JBOSS_DIR}
    build_and_publish_image "jboss-ils"
    rm -rf ils-ear-*.ear
}

build_and_publish_apache2(){
    # create a docker image for apache2
    cd ${APACHE2_DIR}
    build_and_publish_image "apache2-ils"
}

build_and_publish_activemq(){
    # create a docker image for activemq
    cd ${ACTIVEMQ_DIR}
    build_and_publish_image "activemq-ils"
}

build_and_publish_postgresql(){
    # create a docker image for postgresql
    cd ${POSTGRESQL_DIR}
    build_and_publish_image "postgresql-ils"
}

build_sources(){

    # first build the application sources
    execute mvn clean install -Dmaven.test.skip
    
    # create a docker images for all components
    build_and_publish_jboss
    build_and_publish_apache2
    build_and_publish_activemq
    build_and_publish_postgresql
}

main(){
    build_sources
    # TODO do something else
}
# TODO
# main "$@"
