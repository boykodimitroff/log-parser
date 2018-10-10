# log-parser

Simple Spring Boot application which parses log file with specific model. After the parsing is done the application finds all the IPs which have made more than a certain amount of requests based on a given period and duration.

## Getting Started

After cloning the repo you will need to build the project with maven.

### Prerequisites

Running MySQL server instance with initialized LOGS database.

### Run

java -jar log-parser-0.0.1-SNAPSHOT.jar --startDate=<date in format yyyy-MM-dd.HH:mm:ss> --threshold=<number of requests> --accessLog=<path to file> --duration=<daily/hourly> 

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
