title "@project.artifactId@"
chcp 65001
java ^
-Xms6G -Xmx6G -Xmn3G ^
-XX:SurvivorRatio=8 -XX:InitialSurvivorRatio=8 ^
-XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=512M ^
-jar @project.artifactId@-exec.jar
pause