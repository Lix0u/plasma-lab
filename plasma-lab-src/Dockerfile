FROM maven:3.9.9-amazoncorretto-17-debian

# Set environment variable to use host's X11 display
ENV DISPLAY=:0.0

#copy all the folders
COPY repository /repository

COPY install_dependencies.sh /install_dependencies.sh

RUN apt-get update
RUN apt-get upgrade

RUN apt-get install libxrender1 libxtst6 libxi6 -y

#RUN apk add --no-cache msttcorefonts-installer fontconfig
#RUN update-ms-fonts

#COPY fr.inria.plasmalab.root/fr.inria.plasmalab* /fr.inria.plasmalab.root/

#COPY fr.inria.plasmalab.root/compile.sh /fr.inria.plasmalab.root/compile.sh

#COPY fr.inria.plasmalab.matlab_ui /fr.inria.plasmalab.matlab_ui

#COPY fr.inria.plasmalab.root/scripts /fr.inria.plasmalab.root/scripts

#COPY fr.inria.plasmalab.root/scripts /fr.inria.plasmalab.root/scripts

#COPY fr.inria.plasmalab.root/src /fr.inria.plasmalab.root/src

#COPY fr.inria.plasmalab.root/target /fr.inria.plasmalab.root/target

COPY fr.inria.plasmalab.root /fr.inria.plasmalab.root

COPY plasma_lab_examples /plasma_lab_examples

RUN chmod +x install_dependencies.sh

RUN ./install_dependencies.sh

COPY mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh

RUN chmod +x /usr/local/bin/mvn-entrypoint.sh

#RUN chmod +x fr.inria.plasmalab.root/compile.sh

#RUN cd fr.inria.plasmalab.root
WORKDIR fr.inria.plasmalab.root


RUN ./compile.sh

RUN chmod +x target/fr.inria.plasmalab-1.4.5-SNAPSHOT-distribution/plasmalab-1.4.5-SNAPSHOT/plasmagui.sh

RUN apt-get install fontconfig -y

#run ls 

#run ls target

#run ls target/fr.inria.plasmalab-1.4.5-SNAPSHOT-distribution

#run ls target/fr.inria.plasmalab-1.4.5-SNAPSHOT-distribution/plasmalab-1.4.5-SNAPSHOT

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]

CMD ["./target/fr.inria.plasmalab-1.4.5-SNAPSHOT-distribution/plasmalab-1.4.5-SNAPSHOT/plasmagui.sh launch"]
#CMD ["mvn"]