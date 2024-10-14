sur mac :
 - installer Xquartz
 - dans réglage -> sécurité : autoriser les connexions de clients réseaux
 - dans un terminal : xhost + 127.0.0.1
 - docker build -t plasmalab .
 - docker run -ti -e DISPLAY=host.docker.internal:0 plasmalab bash
 - ./target/fr.inria.plasmalab-1.4.5-SNAPSHOT-distribution/plasmalab-1.4.5-SNAPSHOT/plasmagui.sh launch