FILESEXTRAPATHS_prepend := "${THISDIR}/linux-yocto:"

SRC_URI_append = " file://i6300-wdog.cfg file://cleanup.cfg"
