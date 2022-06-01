Je použitá H2, ktorá sa ukladá do priečinka *src/main/resources/db*

Po presmerovaní do súboru s aplikáciou pomocou príkazu ```mvn install``` v CMD prebehne inštalácia a vytvorí sa target folder.
V tomto foldery bude jar file s názvom **demo-0.0.1-SNAPSHOT.jar**
Na zapnutie sa môže použiť ľubovolný terminál alebo command line, kde po presmerovaní do súboru */target* sa zapnutie realizuje príkazom ```java -jar demo-0.0.1-SNAPSHOT.jar```

Po zapnutí sa k API dá pristupovať pomocou localhost:#### s endpointom **/swagger-ui.html**, v mojom prípade to bolo **http://localhost:8080/swagger-ui.html**
