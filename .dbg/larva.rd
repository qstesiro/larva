export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_301
export CLASSPATH=.:${JAVA_HOME}/lib
export PATH=$JAVA_HOME/bin:$PATH

export CLASSPATH=.:/home/qstesiro/github.com/qstesiro/larva/target:/home/qstesiro/github.com/qstesiro/larva/target/lib:/usr/lib/jvm/jdk1.8.0_291/lib

java -cp    /home/qstesiro/github.com/qstesiro/larva/target:/home/qstesiro/github.com/qstesiro/larva/target/lib \
-jar /home/qstesiro/github.com/qstesiro/larva/target/larva-1.5.10.jar

java -classpath ./target/classes com.runbox.Main -address localhost:1025

# maven未安装tools.jar
# https://blog.csdn.net/LovePluto/article/details/79403414
java -Djava.ext.dirs=${JAVA_HOME}/lib::${JAVA_HOME}/lib/ext \
     -jar ~/github.com/qstesiro/larva/target/larva-1.5.10-jar-with-dependencies.jar \
          -address localhost:1025 \
          -script .dbg/java-demo.jdb

# maven已安装tools.jar
java -jar ~/github.com/qstesiro/larva/target/larva-1.5.10-jar-with-dependencies.jar \
          -address localhost:1025 \
          -script .dbg/java-demo.jdb

java -jar larva-1.5.10.jar -address localhost:1025

mvn archetype:generate \
    -DgroupId=io.runbox \
    -DartifactId=java-demo \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackage=io.runbox.demo
