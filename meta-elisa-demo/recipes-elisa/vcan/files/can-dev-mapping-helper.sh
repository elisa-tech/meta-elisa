#!/bin/bash

#
# Script to bring up CAN interfaces configured in /etc/dev-mapping.conf
# as vcan interfaces if no physical interface is present.
#

vcan_up() {
    if [ -n "$1" ]; then
        echo "Bringing up $1 as virtual CAN device"
        ip link add dev $1 type vcan
        ip link set up $1
    fi
}

if [ ! -f /etc/dev-mapping.conf ]; then
    exit 0
fi

hs=$(grep ^hs= /etc/dev-mapping.conf |cut -d= -f2 |tr -d '"')
ls=$(grep ^ls= /etc/dev-mapping.conf |cut -d= -f2 |tr -d '"')

if [ -n "$hs" ]; then
    echo "Checking $hs"
    if ! ifconfig $hs >/dev/null 2>&1; then
        vcan_up $hs
    fi
fi
if [ -n "$ls" -a "$ls" != "$hs" ]; then
    echo "Checking $ls"
    if ! ifconfig $ls >/dev/null 2>&1; then
        vcan_up $ls
    fi
fi

exit 0
