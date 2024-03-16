# IntelliJ-AutoReload
Streamlining Plugin Deployment: Automating Plugin Updates to a Minecraft Server via SFTP, with Automatic Server Restart upon Compilation in IntelliJ.

# Crucial Information: Please Read to Understand the Setup

Dependencies: WinSCP installation on your computer is required.
Your Server will stop. If you want an actual Auto Restart you can either run it in a Cloud or use an [Auto Restart Script](https://www.spigotmc.org/threads/auto-restart-script.451479/)

## Video Demonstration

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/kuQIU9RK9JU/0.jpg)](https://www.youtube.com/watch?v=kuQIU9RK9JU)

## I will begin by explaining how this works.

In our IntelliJ project, we include a script (upload.bat) that runs after compiling the .jar plugin. Initially, the script generates a text file with SFTP arguments. It's crucial to customize these arguments with your own data. Download the upload.bat script and modify the values accordingly.

Next, the script establishes an SFTP connection to your server using a private key. If you're currently using a password, I strongly recommend switching to a private key for enhanced security. However, if you prefer using a password, you can adjust the script accordingly.

Following the connection setup, the script navigates to your specified plugins folder and uploads the designated .jar file. Once the upload is complete, the script closes the connection.

Finally, the script triggers a server restart using the StopAPI Plugin provided in this repository.

Please ensure the accuracy of the provided information and customize it as per your specific requirements before implementation.

If you encounter any issues, feel free to contact me on Discord. You can find my username in my bio.


## Now, here are the steps to set up properly.

### Setup for Maven

1. Create your IntelliJ Minecraft Project (Plugin). A good way to do this is by using the [Minecraft Development Extension](https://plugins.jetbrains.com/plugin/8327-minecraft-development) 
   
2. Next, go into the **pom.xml** and add the following entries to the **'build'** tag:
```xml
<directory>target</directory>
<finalName>YOURPLUGINNAME</finalName>
```
3. Add the StopAPI Plugin to your server's plugins folder. Restart your server to generate the configuration file. It's important to change your AuthKey. Additionally, you can modify your Port. Once you're done, restart the server. The configuration file should look like this:
```yml
port: 3000
authKey: CHANGETHIS
enabled: true
```
4. Download the **upload.bat** file and place it in your **root project folder** (the same folder where the pom.xml is located). Now you MUST fill in your Data.
   - USERNAME: Your SSH Username
   - HOSTNAME: The IP of your Server
   - C:\PATH\TO\PRIVATEKEY.ppk: The Path to your Private Key
   - C:\PATH\TO\YOUR\PROJECT\target\YOURPLUGINNAME.jar : The Path to your Plugin. IMPORTANT: Keep the **'target'** folder and set a Plugin Name!
   - STOPAPI-CONFIG-PORT: The port number configured in your StopAPI configuration
   - AUTHKEY: The authentication key specified in your StopAPI configuration
     
This step is essential for configuring the upload script to function correctly. Ensure accurate input to enable seamless automation of plugin deployment and server restarts. The batch file should resemble the following format:
```bat
@echo off

del uploadscript.txt
echo open sftp://USERNAME@HOSTNAME -privatekey="C:\PATH\TO\PRIVATEKEY.ppk" >> uploadscript.txt
echo cd /HOME/PATH/TO/SERVER/plugins >> uploadscript.txt
echo put C:\PATH\TO\YOUR\PROJECT\target\YOURPLUGINNAME.jar >> uploadscript.txt
echo close >> uploadscript.txt
echo exit >> uploadscript.txt

"C:\Program Files (x86)\WinSCP\WinSCP.com" "/script=uploadscript.txt"
del uploadscript.txt

curl "http://HOSTNAME:STOPAPI-CONFIG-PORT/?auth=AUTHKEY"
```
5. Finally, we want to execute the batch script automatically after compiling in IntelliJ. To do so, go into the **pom.xml** again and add the following block to the **'plugins'** tag located under the **'build'** tag:
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.2.0</version>
    <executions>
        <execution>
            <id>execute-batch-script</id>
            <phase>package</phase>
            <goals>
                <goal>exec</goal>
            </goals>
            <configuration>
                <executable>cmd</executable>
                <arguments>
                    <argument>/c</argument>
                    <argument>upload.bat</argument>
                </arguments>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### Setup for Gradle

1. Create your IntelliJ Minecraft Project (Plugin). A good way to do this is by using the [Minecraft Development Extension](https://plugins.jetbrains.com/plugin/8327-minecraft-development).

2. Next, go into **build.gradle** and add the following:
```groovy
task executeBatch(type: Exec) {
    commandLine 'cmd', '/c', 'upload.bat'
}

compileJava.finalizedBy(executeBatch)
```
We'll have to modify the upload.bat because it was made for Maven originally. Don't worry, we'll cover this in step 4

3. Add the StopAPI Plugin to your server's plugins folder. Restart your server to generate the configuration file. It's important to change your AuthKey. Additionally, you can modify your Port. Once you're done, restart the server. The configuration file should look like this:
```yml
port: 3000
authKey: CHANGETHIS
enabled: true
```

4. Create the file **upload.bat** in your **Project Root Directory** and paste the Snippet below (the same folder where the build.gradle is located). Now you MUST fill in your Data.
   - USERNAME: Your SSH Username
   - HOSTNAME: The IP of your Server
   - C:\PATH\TO\PRIVATEKEY.ppk: The Path to your Private Key
   - C:\PATH\TO\YOUR\PROJECT\build\libs\YOURPLUGINNAME.jar : The Path to your Plugin.
   
     **IMPORTANT: USE THE BATCH EXAMPLE BELOW FOR GRADLE. Compile your Plugin and copy the name.**
     
   - STOPAPI-CONFIG-PORT: The port number configured in your StopAPI configuration
   - AUTHKEY: The authentication key specified in your StopAPI configuration
     
This step is essential for configuring the upload script to function correctly. Ensure accurate input to enable seamless automation of plugin deployment and server restarts. The batch file should resemble the following format:
```bat
@echo off

del uploadscript.txt
echo open sftp://USERNAME@HOSTNAME -privatekey="C:\PATH\TO\PRIVATEKEY.ppk" >> uploadscript.txt
echo cd /HOME/PATH/TO/SERVER/plugins >> uploadscript.txt
echo put C:\PATH\TO\YOUR\PROJECT\build\libs\YOURPLUGINNAME.jar >> uploadscript.txt
echo close >> uploadscript.txt
echo exit >> uploadscript.txt

"C:\Program Files (x86)\WinSCP\WinSCP.com" "/script=uploadscript.txt"
del uploadscript.txt

curl "http://HOSTNAME:STOPAPI-CONFIG-PORT/?auth=AUTHKEY"
```



###### If you followed every step accordingly, you should now be able to click on the Run button in IntelliJ to compile, upload, and restart automatically!
This setup might seem complicated. If you have any questions or need assistance, please check my Discord username in the bio and add me.
