SUMMARY     = "Systemd unit for agl-service-can-low-level CAN device helper"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd allarch

SRC_URI = "file://can-dev-mapping-helper.service \
           file://can-dev-mapping-helper.sh \
"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -D -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_system_unitdir}/${BPN}.service
    install -D -m 0755 ${WORKDIR}/${BPN}.sh ${D}${sbindir}/${BPN}.sh
}

FILES_${PN} += "${systemd_system_unitdir}"

RDEPENDS_${PN} += "bash"

