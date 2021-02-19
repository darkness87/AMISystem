ps -elf | grep ami | grep -v grep | awk '{print $4}' | while read PID; do echo "killing $PID ..."; kill -9 $PID; done
echo "AMI-Server System is stop!!"