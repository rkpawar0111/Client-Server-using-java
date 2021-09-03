# Client-Server-using-java
Client/Server setup for multiple file transfer.


-> This is Client/Server setup application using low-level API(i.e using socket address) with simple user interface.
-> I have used java for back-end and javaFx for front-end.
-> Requirements:-
  1. Primary pacakage requirements are java.net,java.awt,java.io and java.nio.
  2. java.net provides ServerSocket and Socket class which are used at Server and Client side respectively.
  3. java.awt provides BufferedImage class which provides buffer (or) memory space that can be shared between client/server.
  4. java.io and java.nio for working with text and image files.

-> The application is capable enough to transfer only multiple text and image files across server to client.
-> A additional single line code on client side is added which provides ip address of the client connected to the server, in case if needed to track client.
-> On client side i have used "localhost" instead of server (or) sender ip address. You can provide ipv4 address of sender in case the receiver is on
  different machine/computer. (Note: Both the Server and Client machine should be connected to same network for file transfer because of low-level API)

/*

Note:- // In case of Error occurence
1. While testing on same machine, if "localhost" does not work, use loop-back address 127.0.0.1 which will definately work.
2. Use correct ip address of sender (or) server.
3. Use port numbers which are not in use.
4. In case if client program raises socket-timed-out (or) socket-disconnected, then check above points are followed and in case if using home/school/college wifi
   do remember most of the ports will not be allowed for access, due to some security resaons.(You can even connect both client and server machine to mobile hotspot
   and share the files.)

*/
