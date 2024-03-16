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