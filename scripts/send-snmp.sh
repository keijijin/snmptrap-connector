#!/bin/bash

HostName=$1
SpecificTrap=$2
AlarmId=$3
EventMsg=$4

GenericTrap=0
DEST=127.0.0.1:162

#snmptrap command
snmptrap -v 1 -c test $DEST \
.1.3.6.1.4.1.8072.9999.0.2.35 $HostName $GenericTrap $SpecificTrap "" \
.1.3.6.1.4.1.8072.9999.0.2.35.$GenericTrap.$SpecificTrap.1 s "$AlarmId" \
.1.3.6.1.4.1.8072.9999.0.2.35.$GenericTrap.$SpecificTrap.2 s "$EventMsg"
