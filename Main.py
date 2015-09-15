__author__ = 'simsund'
import socket

server = "irc.chalmers.it" # Server
channel = "#Irkkalon" # Channel
botnick = "Merlin" # Your bots nick

def ping(): # This is our first function! It will respond to server Pings.
  ircsock.send("PONG :Pong\n")

def sendmsg(chan , msg): # This is the send message function, it simply sends messages to the channel.
  ircsock.send("PRIVMSG "+ chan +" :"+ msg +"\n")

def joinchan(chan): # This function is used to join channels.
  ircsock.send("JOIN "+ chan +"\n")

def hello(newnick): # This function responds to a user that inputs "Hello Mybot"
  ircsock.send("PRIVMSG "+ channel +" :Hello!\n")


ircsock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

ircsock.connect((server, 22)) # Here we connect to the server using port 22

ircsock.send("USER "+ botnick +" "+ botnick +" "+ botnick + "\n") # user authentication

ircsock.send("NICK "+ botnick +"\n") # here we actually assign the nick to the bot

joinchan(channel) # Join the channel using the functions we previously defined

while 1: # Be careful with these! It might send you to an infinite loop
  ircmsg = ircsock.recv(2048) # receive data from the server
  ircmsg = ircmsg.strip('\n\r') # removing any unnecessary linebreaks.
  print(ircmsg) # Here we print what's coming from the server

  if ircmsg.find(":Hello "+ botnick) != -1: # If we can find "Hello Mybot" it will call the function hello()
    hello()

  if ircmsg.find("PING :") != -1: # if the server pings us then we've got to respond!
    ping()


