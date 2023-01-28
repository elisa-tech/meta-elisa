FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " file://0001-Add-new-icon.patch \
           "

CLUSTER_DEMO_VISS_HOSTNAME = "localhost"

RDEPENDS:${PN}:append = " kuksa-val-agl \
                          kuksa-dbc-feeder \
                          kuksa-val-agl-demo-cluster \
                        "
