__author__ = 'simsund'
import socket
import sys

server = "irc.chalmers.it"       #settings
channel = "#Irkkalon"
botnick = "Merlin"

irc = socket.socket(socket.AF_INET, socket.SOCK_STREAM) #defines the socket

print ("connecting to:" + server)

irc.connect((server, 6667))                                                         #connects to the server
irc.send("USER "+ botnick +" "+ botnick +" "+ botnick +" :This is a avalon bot!\n") #user authentication
irc.send("NICK "+ botnick +"\n")                            #sets nick
irc.send("PRIVMSG nickserv :iNOOPE\r\n")    #auth
irc.send("JOIN "+ channel +"\n")        #join the chan

while 1:    #puts it in a loop
   text=irc.recv(2040)  #receive the text
   print ("")   #print text to console

   if text.find('PING') != -1:                          #check if 'PING' is found
      irc.send('PONG ' + text.split() [1] + '\r\n') #returnes 'PONG' back to the server (prevents pinging out!)