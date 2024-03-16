@echo off

del uploadscript.txt
echo open sftp://USERNAME@HOSTNAME -privatekey="C:PATH\TO\PRIVATEKEY" >> uploadscript.txt
echo cd /PATH/TO/PLUGIN/FOLDER >> uploadscript.txt
echo put C:\PATH\TO\COMPILED\FILE.jar >> uploadscript.txt
echo close >> uploadscript.txt
echo exit >> uploadscript.txt

"C:\Program Files (x86)\WinSCP\WinSCP.com" "/script=uploadscript.txt"
del uploadscript.txt

curl "http://HOSTNAME:CONFIG-PORT/?auth=YOURAUTHKEY"