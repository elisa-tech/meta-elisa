LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=97335dd06052dc8d1c0c034d2d7dd4f1"

SRCREV = "f483c0907b5a2c86a8929854319eb2e74a361993"
SRC_URI = "git://github.com/sudipm-mukherjee/hello-world.git;branch=master;protocol=https;"

S  = "${WORKDIR}/git"

do_compile () {
        oe_runmake
}

do_install () {
        oe_runmake install 'DESTDIR=${D}'
}
