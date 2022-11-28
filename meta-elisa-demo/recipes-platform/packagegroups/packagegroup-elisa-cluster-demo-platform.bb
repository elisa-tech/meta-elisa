SUMMARY = "Elisa extension for AGL cluster profile"
DESCRIPTION = "A set of packages belong to Elisa extension for AGL Cluster Demo Platform"

LICENSE = "MIT"

inherit packagegroup

PACKAGES = "\
    packagegroup-elisa-cluster-demo-platform \
    "

ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} += "\
    "


ELISA_APPS = " \
    hello-world \
    "

ELISA_APIS = " \
    "

RDEPENDS_${PN}:append = " \
    ${ELISA_APPS} \
    ${ELISA_APIS} \
"
