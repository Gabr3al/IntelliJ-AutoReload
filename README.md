# IntelliJ-AutoReload
A solution to automatically update your plugin to a Minecraft-Server on an SFTP-Destination + Restarting the Server after Compiling in IntelliJ

# IMPORTANT: YOU NEED TO READ THIS TO UNDERSTAND THE SETUP

Dependencies: You need to have WinSCP installed on your Computer.

## I will start by explaining on how this works.

Basically we add a script (upload.bat) to our project in IntelliJ that executes after compiling the .jar plugin.
First the Script creates a Text-File with SFTP Arguments. You NEED to fill these with your own data. Download the upload.bat and Change the values.
Secondly the Script will build an SFTP-Connection to your Server with a Private Key. If you just use a Password I recommend you to switch to a private key but you can also just change the Script to use a Password.
After that it will navigate to your set plugins folder and Uploads your set .jar File and Closes Connection.
At last it will Trigger a Server Restart using the StopAPI Plugin i provided in this Repo. 

## Now the Steps to setup properly

1.
