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

1. `gradlew test`

## Contributors

For now only me :).

## License

A short snippet describing the license (MIT, Apache, etc.)