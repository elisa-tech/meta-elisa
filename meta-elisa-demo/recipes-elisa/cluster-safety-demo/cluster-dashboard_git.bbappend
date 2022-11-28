FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " file://0001-Add-new-icon.patch \
             file://cluster-dashboard.conf \
           "

RDEPENDS:${PN}:append = " kuksa-val-agl \
                          kuksa-dbc-feeder \
                          kuksa-val-agl-demo-cluster \
                        "
