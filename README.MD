# DEDServer DNS
This is only for testing and proving purposes. Do not use it as a normal project, as it is only for the DEDServer Community.

#How to use it?
Compile the whole project and 
``` java -jar dns_server.jar ```

Do not forget the `dns.config` file, it must contain the "DNS table"
###Example : 
```
game.clashofclans.com. = 134.16.21.56
gamea.clashofclans.com. = 134.16.21.56
```
Do not forget the dot (.) at the end of each domain names as it is really important.

#How to test the code
Firstly, you need to start the server. When done, there are two possible ways:
##Using the CMD
Open the CMD on Windows and type `nslookup - localhost`. This command is used to test a DNS server. When the `>` appears, type a domain name (such as google.fr, or cocdevforum.ml, whatever). It should return the IP of the domain name (associated). If you type a domain name that is contained in the `dns.config`, the IP that will be returned will be the one you specified on the config file. 
####Example
If you used the example from the previous section :
```
C:\Users\GitHubTest>nslooup - localhost
Server by default: localhost

> google.com
Server: UnKnown
IP : 173.194.40.152

> game.clashofclans.com
Server: UnKnown
IP : 134.16.21.56

> gameeeee.clashofclans.random.com
Non-existant domain

> gamea.clahsofclans.com
Server: UnKnown
IP : 134.16.21.56
```
##Using a phone/tablet/computer
Go to your Wi-Fi settings, set the DNS to the IP address the DNS server is on and test Clash of Clans.
####Example
##### Let's say you have a private server on 134.16.21.56
If CoC shows your private server it means it works, if not it either means:
- Failed launching the server
- Your settings are not correct
- Or your private server isn't on

#THE END