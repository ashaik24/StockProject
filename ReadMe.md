Deployment Process: 
1. Clone the repository from https://github.com/ashaik24/StockProject.git.
2. Install Node.js (If you donot have one on your local machine)
3. Run npm install --force in the StockProject root folder. This will install all the required npm packages.
4. Run MySQL server on your local machine. Create a schema named 'StockProject' on your localhost.
5. Below are the username and password configured in the project for MySQl:
   Username: root
   Password: root
6. Change the credentials if this doesn't match your MySQL server credentials in application.properties file available in src/main/resources folder.
7. Run 'mvn clean package' command to build the package. 
8. From the terminal run 'cd target' command to move into the target folder generated.
9. Run command 'java -jar StockProject-0.0.1-SNAPSHOT.jar' in the terminal. 
10. Once the server starts on localhost:8080 open 'http://localhost:8080/' url in the browser.
11. In case you face issues with Java version use Java SDK 17

Key components:

Tech Stack Used in Stock project:
1. UI framework: Angular
2. UI Design: Angular Material, Bootstrap
3. Backend: Spring Boot
4. Language: Java
5. Object relational mapping tool: Hibernate
6. Database: MySQL
7. Build automation tool: Maven


Architecture:
1. Project is developed using maven structure integrated with Spring boot and Angular.
2. All the angular elements are developed in src/main/resources/static/angularclient folder.
3. Backend java code is available in src/main/java folder which contains controllers, services and entities.
4. MySQL database will be running on local server.
5. HttpClient is used for interaction between UI and backend services.
6. Application flow starts from Angular components and connected to Spring Controllers through http calls, all the business logic is handled in services. Database models are created in the entities folder which connect to MySql through JPA CrudRepository.


Application Functionalities:
1. Register admin and users.
2. Login and authorization for admin and user.
3. User Role:
    1. Dashboard which displays available stocks in the market and option to Buy the Stocks.
    2. Current Portfolio of stocks available in Profile tab.
    3. Portfolio of Cash, Transactions History, Deposit and Withdraw cash features available in Balance tab.
    4. Sell owned stocks available in Profile tab across each stock unit available.
    5. Buying and Selling stocks using limit order available in Dashboard and Profile tabs under Buy Stock and Sell Stock sections.
    6. Scheduled Transaction tab displays all the scheduled events and provides option to cancel the order before the expiry date.
4. Admin Role:
    1. View Dashboard of Stocks added.
    2. Add new Stocks available in Add Stocks tab.
    3. Update Market Hours available in Add Stocks tab.
5. Random Stock Generator functionality is developed in StockPriceGenerator api in the backend. Fluctuations in Stocks during the scheduled time will be displayed on the User Dashboard along with the Opening Price, High and Low prices through out the day.
6.  Scheduled Buy and Sell stocks is developed in ScheduledTransactionExecutor api, which reads the scheduled transactions of all users and buys and sells stocks when the target is reached. 
