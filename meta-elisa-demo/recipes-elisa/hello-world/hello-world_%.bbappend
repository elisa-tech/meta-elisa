FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://helloworld.service"

inherit systemd

SYSTEMD_SERVICE_${PN} = "helloworld.service"
SYSTEMD_AUTO_ENABLE = "enable"

do_install:append(){
        install -d -m 755 ${D}${systemd_unitdir}/system
        install -m 644 ${WORKDIR}/helloworld.service ${D}${systemd_unitdir}/system
}
