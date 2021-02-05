SUMMARY = "IPC by pipe for AGL cluster demo safety workload"
DESCRIPTION = "Jochen layers it on ^^"

#that seems to be necessary, the license here makes no sense, I just fumbled it so it would compile ;)
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/elisa-tech/wg-automotive-safety-app.git;branch=control_pipe;protocol=https;"

SRCREV = "8db75d886c915efc16e481e3fb63a09fd6e10eb6"

#needed to install systemd services
inherit systemd
#add and enable the services
SYSTEMD_SERVICE_${PN} = "signal-source.service safety-app.service"
SYSTEMD_AUTO_ENABLE = "enable"

#make the service files known
SRC_URI += "file://signal-source.service"
SRC_URI += "file://safety-app.service"

DEPENDS += "ncurses"

#Package version
PV = "1.0+git${SRCPV}"

#Location of the source during building
S  = "${WORKDIR}/git"

#build instructions
do_compile(){
	oe_runmake
}

#installation routine, does nothing but copy the text file to the root directiory of the image for testing
do_install() {
    oe_runmake install 'DESTDIR=${D}'
# install the signal-source service and the safety-app service
    install -d -m 755 ${D}${systemd_unitdir}/system
    install -m 644 ${WORKDIR}/signal-source.service ${D}${systemd_unitdir}/system
    install -m 644 ${WORKDIR}/safety-app.service ${D}${systemd_unitdir}/system     
}

# having the clean: target can trip us up, if the files to be removed never 
# were created in the first place for whatever reason, thus this flag
CLEANBROKEN = "1"
