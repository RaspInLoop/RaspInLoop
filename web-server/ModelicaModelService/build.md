 h1. how to build image
 mvn package && docker build --tag raspinloop/modelicamodelservice .
 
 h1. Run

 docker-compose -f ..\docker-compose.yml up
 
 h1. build and restart
 mvn package && docker build --tag raspinloop/modelicamodelservice .&& docker-compose -f ..\docker-compose.yml up -d --no-deps modelicamodelservice
 
 h1. other
 docker -ps 
 # find modelicamodelservice container 
 docker container restart $modelicamodelservice_container
 
