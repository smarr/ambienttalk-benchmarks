#!/bin/sh

export PATH=$PATH:$JAVA_HOME/bin





echo java -cp "bin/:lib/*" $*
java -cp "bin/:lib/*"  $*
# -Cinstrument.allocation.options.allocationAgentJar=$ALLOCATION_JAR
