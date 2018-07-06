# TesterMatching

A Spring Boot application used to match Testers based on a User's search Criteria. 
The search results are ranked in order of experience where experience is measured by the number of Bug(s) a Tester filed for the given Device(s).

To run the program:
1. Clone into a directory using the url for the repository.
2. After cloning, use maven to build and start the application using the following command:
      mvn spring-boot:run
      
3. This command should download all the necessary dependencies and being the application.
4. Open a web browser(preferably firefox, safari was giving some weird issues) and navigate to the following URL: http:localhost:8080/
5. Select the criteria and hit the submit button. A pop-up should appear with the results!

