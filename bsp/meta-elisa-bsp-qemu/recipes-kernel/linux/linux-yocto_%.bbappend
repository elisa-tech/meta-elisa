FILESEXTRAPATHS:prepend := "${THISDIR}/linux-yocto:"

SRC_URI:append = " file://i6300-wdog.cfg file://cleanup.cfg file://cleanup_2.cfg"

