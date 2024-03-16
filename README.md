# IntelliJ-AutoReload
A solution to automatically update your plugin to a Minecraft-Server on an SFTP-Destination + Restarting the Server after Compiling in IntelliJ

# IMPORTANT: YOU NEED TO READ THIS TO UNDERSTAND THE SETUP

Dependencies: You need to have WinSCP installed on your Computer.

## I will start by explaining on how this works.

In our IntelliJ project, we include a script (upload.bat) that runs after compiling the .jar plugin. Initially, the script generates a text file with SFTP arguments. It's crucial to customize these arguments with your own data. Download the upload.bat script and modify the values accordingly.

Next, the script establishes an SFTP connection to your server using a private key. If you're currently using a password, I strongly recommend switching to a private key for enhanced security. However, if you prefer using a password, you can adjust the script accordingly.

Following the connection setup, the script navigates to your specified plugins folder and uploads the designated .jar file. Once the upload is complete, the script closes the connection.

Finally, the script triggers a server restart using the StopAPI Plugin provided in this repository.

Please ensure the accuracy of the provided information and customize it as per your specific requirements before implementation.

### If you encounter any issues, feel free to contact me on Discord. You can find my username in my bio.

## Now the Steps to setup properly

1.
