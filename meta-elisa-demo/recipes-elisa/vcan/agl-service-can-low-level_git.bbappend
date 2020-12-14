FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://can-dev-mapping-helper.conf"

do_install_append() {
    install -D -m 0644 ${WORKDIR}/can-dev-mapping-helper.conf \
        ${D}${sysconfdir}/systemd/system/afm-service-agl-service-can-low-level-.service.d/can-dev-mapping-helper.conf
}

RDEPENDS_${PN} += "can-dev-mapping-helper"
