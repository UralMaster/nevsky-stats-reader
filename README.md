# Nevsky 26/54 football (soccer) command's statistics application

Nevsky 26/54 is amateur football (soccer) command from Saint Petersburg, Russia. This project is result of last 
attempt for personal players' statistics gathering and aggregation automation. The project is on very early steps of 
its implementation, so it will be improved and rework in many parts.

This particular application is reader application which provides ability of players and games view only.

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to import Vaadin projects to different 
IDEs](https://vaadin.com/docs/latest/flow/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/nevsky-stats-1.0-SNAPSHOT.jar`

