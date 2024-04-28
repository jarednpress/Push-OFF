Run as just an app: download PUSH_OFF.jar and run in linux terminal using:
```
java -jar PUSH_OFF.jar
```
On macos:
```
java -XstartOnFirstThread -jar PUSH_OFF.jar
```
Untested on Windows (we both use linux)

note: java.jar may not be representitive of final version. is updated as of 'jar file test' commit.

__________________________________

how to deploy as new .jar file if updated:
```
./gradlew desktop:dist
```
the new build of the app will be located in the desktop/build/libs folder. You must have a JVM installed for this to work.

__________________________________
Player 1 controls:

![wasd](https://github.com/jarednpress/Push-OFF/assets/112017486/10abb39d-f694-46c3-ba94-30d1b37933d3)


Player 2 Controls are same but on here: 

![IJKL](https://github.com/jarednpress/Push-OFF/assets/112017486/08e6b6e7-8bca-42b5-bfe1-458c90ed8358)



__________________________________

here is how to run from IDE (double click on run in right-side gradle menu):


![image](https://github.com/jarednpress/Push-OFF/assets/112017486/33f9c5ff-538c-45cd-9ee2-5bbbfb2233c5)


