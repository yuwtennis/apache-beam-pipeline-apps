
utest:
	mvn test

clean:
	mvn clean

package:
	mvn package

run: clean package
	java -cp target/pipeline_apps-1.0-SNAPSHOT-jar-with-dependencies.jar net.yuwtennis.app.AppClient
