export CLASSPATH=.:/home/qstesiro/github.com/qstesiro/larva/target:/home/qstesiro/github.com/qstesiro/larva/target/lib:/usr/lib/jvm/jdk1.8.0_291/lib

java -cp    /home/qstesiro/github.com/qstesiro/larva/target:/home/qstesiro/github.com/qstesiro/larva/target/lib \
-jar /home/qstesiro/github.com/qstesiro/larva/target/larva-1.5.10.jar

java -classpath ./target/classes com.runbox.Main -address localhost:1025

java -jar ./target/larva-1.5.10-jar-with-dependencies.jar -address localhost:1025

java -jar larva-1.5.10.jar -address localhost:1025
