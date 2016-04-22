
### Networking

Client-server model

Server will be the master of all game state and will be the master of stats and bullets

The server and each client will all have a 'reactivegamedata' instance which contains all info about the gamestate like the points, the units, their positions and stats, etc.   This state can only be modified by using 'CustomGameActions' and these are passed through the 'NetworkMessage' class.  




Clients will simulate their own physics collisions and will be the master of their own character position and rotation
(Server will kick players who seem to be hacking)

Server will rebroadcast player pos and rotation to all others and this will occur using UDP streaming (other stuff is TCP)

when clients shoot, animations and bullets will be simulated.  The damage will be calculated all by the server 
   #### Bullets
   When a bullet is fired, the players position and rotation at that time is saved and passed to the server encapsulated in a 'fire action'
   
   Need to store last 20 snapshots of unit positions along with their tickIds so then you can 'rewind time' to calc a bullet hit 
   
   //https://developer.valvesoftware.com/wiki/Latency_Compensating_Methods_in_Client/Server_In-game_Protocol_Design_and_Optimization
   
   
   #### Movement
   100 ms of extrapolation for 'leading' opponent models
   also do interpolation..