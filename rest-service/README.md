# RESTful patient data retreival service

This service is written in Java (8) and is designed to be deployed in Docker in a Wildfly 15 container, Postgres 12 is used as DB.

Requirements: Docker, Maven, git, jdk8, curl (for POST requests). Tested on 2 Windows 10 Pro PCs.

In order to build and deploy this service following steps must be followed:
1. clone this repository with 
git clone https://github.com/asemon/fhir.git 
to your local machine.

2. /fhir directory will be created. Inside you will find /rest-service directory -> change there.

3. inside /rest-service execute following maven command:
mvn clean install
This will build the project and create our restservice.war artefact (located in /rest-service/target)

4. In /rest-service you will also find a Dockerfile and a docker-compose.yml. These are needed for container setup and deployment of our service.

5. The Dockerfile ensures that a Wildfly 15 docker container is downloaded and configured. During the configuration a module with a Postgres JDBC driver and a modified standalone.xml are copied to the Wildfly server. The driver is needed for communication with the Postgres datasource and standalone.xml keeps the configuration of the datasource (credentials, connection details and other). Afterwards our artifact is copied in the deployment directory of the server. Deployment scanner will ensure its startup. Server module and standalone.xml are located within the project and can be found in /src/main/resources/jboss. 

6. docker-compose.yml keeps the formalized description of our deployment structure. I configured two services: postgres (sql server) and restservice (our webservice). It also holds port configurations, DB credentials and ensures that create.sql is copied to the container (script file can be found here: src\main\resources\ddl). It will be executed during container startup and will create necessary SQL tables.

7. So, now you only need to execute 
docker-compose up
from within the /rest-service directory (where both docker files reside). After container download, setup and startup we can test the service with curl. The service has two methods: transferFhirPatient(POST) and transferedPatient(GET), both have a single QUERY parameter fhirUrl, which expects a UTF-8 encoded URL. There are lots of free online En- and Decoders for URLs (for example https://www.urlencoder.org/). For testing I created a resource, which can be found here: https://lforms-fhir.nlm.nih.gov/baseR4/Patient/5770420. To test both methods you can use following curl commands:
curl --request POST http://localhost:8080/restservice/patient/transferFhirPatient?fhirUrl=https%3A%2F%2Flforms-fhir.nlm.nih.gov%2FbaseR4%2FPatient%2F5770420
curl --request GET http://localhost:8080/restservice/patient/transferedPatient?fhirUrl=https%3A%2F%2Flforms-fhir.nlm.nih.gov%2FbaseR4%2FPatient%2F5770420
GET method can also be called from the browser by simply navigating to this website:
http://localhost:8080/restservice/patient/transferedPatient?fhirUrl=https%3A%2F%2Flforms-fhir.nlm.nih.gov%2FbaseR4%2FPatient%2F5770420

To stop the containers simply call docker-compose stop. A container restart should not delete persisted data from the DB. 

To summarize it should be said, that some assumptions were made during the development of the service, which I tried to reflect in the comments to my code. Also the testing part was completely omitted in order to keep things simple for the sake of this task.


    

