## Synopsis

This is simple project that has REST / JSON web service written in Java 8 using Spring MVC (RestController) with an API that 
supports the following: 
* Create new company 
* Get a String list of all companies 
* Get details about a company 
* Able to update a company 
* Able to add beneficial owner(s) of the company 

## Requirements
* Java 8

## Installation

1. `git clone https://github.com/karolgle/demo-spring.git`
2. `cd demo-spring`
3. `gradlew build`
4. `java -jar build/libs/demo-spring-ng6-0.0.1-SNAPSHOT.jar`
5. ...or `gradlew bootRun`

## API Reference

Available endpoints for the application.

### Endpoints

| resource                    | description                       |
|:----------------------------|:----------------------------------|
| <code>GET</code> `/companies`               | returns list of available company names |
| <code>GET</code> `/companies/{name}`        | returns detailed data for company with specific `{name}`|
| <code>POST</code> `/companies`              | create new company if not exist |
| <code>PUT</code> `/companies`               | updates company data  |
| <code>POST</code> `/companies/{name}/owner` | adds owner(if not exist already) to specific company  |


## Tests
Run following command:

1. Run `gradlew test`
2. or ...test API manually using e.g. curl

#### cUrl commands

1. `curl -X GET http://localhost:8080/companies`
2. `curl -X GET http://localhost:8080/companies/{name}`
   * e.g. `curl -X GET http://localhost:8080/companies/Dis%20Parturient%20Montes%20Company`
3. `curl -X POST -H "Content-Type: application/json" -d {jsonString} http://localhost:8080/companies`
   * e.g. `curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"Company Name 55\",\"address\":\"Address 1\",\"city\":\"CITY 69\",\"country\":\"Poland\",\"email\":\"aaa@aaa.pl\",\"phone\":\"+48 556 222 333\", \"owners\":[\"Jenny Bliz\", \"John Fritz\"]}" http://localhost:8080/companies`
4. `curl -X PUT -H "Content-Type: application/json" -d {jsonString} http://localhost:8080/companies`
   * e.g. `curl -X PUT -H "Content-Type: application/json" -d "{\"name\":\"Company Name 55\",\"address\":\"AddressZZZZZZZZZZ\",\"city\":\"CITY 76\",\"country\":\"Poland\",\"email\":\"aaa@aaa.pl\",\"phone\":\"+48 556 222 333\", \"owners\":[\"Jenny Bliz\", \"John Fritz\"]}" http://localhost:8080/companies`     
5. `curl -X POST -H "Content-Type: application/json" -d {jsonString} http://localhost:8080/companies/{name}` 
   * e.g. `curl -X POST -H "Content-Type: application/json" -d "new owner name" http://localhost:8080/companies/Dis%20Parturient%20Montes%20Company/owner`
   
## Contributors

For now only me :).

## License

A short snippet describing the license (MIT, Apache, etc.)

## Considerations*
1. What Authentication method should be used:
   Basic Spring Security is first choice as it is already integrated in Spring Framework and is easy to test and config. If application would have only intranet users I would consider Spring Security Single-SignOn using Waffle(https://github.com/Waffle/waffle).
2. How can you make the service redundant? 
   Service is usually redundant when "manual" work done with the help of the service can be automated. 
   E.g. if we know what is the source of inserted companies and where the companies should be inserted we can prepare ETL process with usage of SSIS or schedule MSSQL job or schedule some task using Quartz Scheduler etc.
3. What considerations should you do?
    * What is quality of data(is there known schema for the imported data)
    * What is availability of data(the same country/continent, 24/7)
    * What are security risks
    * Is there any additional software/hardware need for client
    * Cost of support for the solutions
         