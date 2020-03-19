# Weather-API
A Spring boot weather application, sending request to Darksky and showing reply at html.



### Prerequisites

Browser is needed to use this software.

Must to be connected to internet in order to send request to Darksky Api. 


### Installing

No need to install. 

Maven install

Run spring boot app server

Darksky key is mentioned in application.properties file


Go to localhost:8080;

http://localhost:8080/

Type a city name to "Enter city" textbox. For example Campbell, or Omaha.

Web page will be redirected to http://localhost:8080/searchCity/Campbell and datas will be shown as a table.

In this web page it is possible to export datas to excel or pdf, and by show database button, it is possible to see search and data history at mongodb embedded database.

By "Weathers From Database" button all search and data history could be seen.


## Deployment



## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* Darksky api
* Thymeleaf
* Jstl
* JSON
* ItextPdf
* flapdoodle.embed.mongo