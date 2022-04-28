HowTo: First Setup
------------------

1. Start docker-compose
- Run "docker-compose up" in the /basyx.nodered/ folder
or
- Run /basyx.nodered/start.sh (start.bat)

2. Open in Browser 
- http://localhost:1880


HINTS
-----
- if you get a message that the nodes MQTT are disconnected:
   a. Go to folder \examples\basyx.nodered\mosquitto\config
   b. Open the mosquitto.conf, locate and uncomment the following lines:
	allow_anonymous true
	port 1883

- if you get a conflict when starting the containers, 
  make sure any other monitoring examples and their containers are shut down
  (run the stop.bat/stop.sh command in the respective example folder)
