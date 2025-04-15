install:
	del target\limit-maces-*.jar
	mvn package
	copy target\limit-maces-*.jar "C:\Users\nicca\Documents\Minecraft\Java\Servers\Testing Server\plugins"