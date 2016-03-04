cd internetMessageProxy && mvn clean install -DskipTests=true && cd - &&
cd bcCommunicator && mvn clean install -DskipTests=true  && cd - &&
cd commonTestUtilities && mvn clean install -DskipTests=true && cd - &&
cd Communicator.Client && mvn clean install -DskipTests=true && cd - &&
cd Communicator.Server && mvn clean install -DskipTests=true && cd - &&

cp commonTestUtilities/target/commonTestUtilities-0.0.1-SNAPSHOT.jar output/
cp internetMessageProxy/target/internetMessageSender-0.0.1-SNAPSHOT.jar output/
cp bcCommunicator/target/bcCommunicator-0.0.1-SNAPSHOT.jar output/
cp Communicator.Client/target/Communicator.Client-0.0.1-SNAPSHOT.jar output/
cp Communicator.Server/target/Communicator.Server-0.0.1-SNAPSHOT.jar output/
