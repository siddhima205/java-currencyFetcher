

## Steps

1.Please run http://localhost:8080/swagger-ui.html to enlist and run all the APIs
2.There is a flow  diagram snapshot attached to unerstand the flow of the application
3.For demo, have used USD and AUD because the data being for each currency was 8k+  and there are a lot of currencies
4.To test the date , a sample input can be "2021-05-21" and currencyCode can be "AUD"
5.Please note , mote all the negative scenarios have been error handled, there can be exceptions in case of wrong data
6.Only one test has been created to give a look and feelof how to mock services and return results.
7.Hope the solution is correct :)




## The Challenge

Your task is to create a foreign exchange rate service as SpringBoot-based microservice. 

The exchange rates can be received from [2]. This is a public service provided by the German central bank.

As we are using user story format to specify our requirements, here are the user stories to implement:

- As a client, I want to get a list of all available currencies
- As a client, I want to get all EUR-FX exchange rates at all available dates as a collection
- As a client, I want to get the EUR-FX exchange rate at particular day
- As a client, I want to get a foreign exchange amount for a given currency converted to EUR on a particular day


 
## Setup
#### Requirements
- Java 11 (will run with OpenSDK 15 as well)
- Maven 3.x

#### Project
The project was generated through the Spring initializer [1] for Java
 11 with dev tools and Spring Web as dependencies. In order to build and 
 run it, you just need to click the green arrow in the Application class in your Intellij 
 CE IDE or run the following command from your project root und Linux or ios. 

````shell script
$ mvn spring-boot:run
````

After running, the project, switch to your browser and hit http://localhost:8080/api/currencies. You should see some 
demo output. 

If time permits, some unit tests and documentation would also be great. 

[1] https://start.spring.io/

[2] https://www.bundesbank.de/dynamic/action/en/statistics/time-series-databases/time-series-databases/759784/759784?listId=www_s331_b01012_3
