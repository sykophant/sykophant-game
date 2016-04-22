
### Networking

Client-server model

Server will be the master of all game state and will be the master of stats and bullets

Clients will simulate their own physics collisions and will be the master of their own character position and rotation
(Server will kick players who seem to be hacking)

Server will rebroadcast player pos and rotation to all others and this will occur using UDP streaming (other stuff is TCP)

when clients shoot, animations and bullets will be simulated.  The damage will be calculated all by the server 
   #### Bullets
   When a bullet is fired, the players position and rotation at that time is saved and passed to the server encapsulated in a 'fire action'