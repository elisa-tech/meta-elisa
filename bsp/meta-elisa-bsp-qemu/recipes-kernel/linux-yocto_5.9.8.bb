
inherit kernel kernel-yocto
require recipes-kernel/linux/linux-yocto.inc

SRC_URI_remove = " file://net-sch_generic-Use-pfifo_fast-as-fallback-scheduler.patch"

SRCREV_machine = "951cbbc386ff01b50da4f46387e994e81d9ab431"
SRCREV_meta = "d5ca337b7e9b5834c83b629b5456bb4744efa644"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;name=machine;branch=linux-5.9.y; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.8;destsuffix=${KMETA} "

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION ?= "5.9.8"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"

KMACHINE_qemux86-64 = "qemux86-64"
KMACHINE_genericx86-64 = "common-pc-64"

COMPATIBLE_MACHINE_qemux86-64 = "${MACHINE}"
KCONFIG_MODE = "--alldefconfig"

# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc"
KERNEL_FEATURES_append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES_append_qemuall=" cfg/virtio.scc"
KERNEL_FEATURES_append_qemux86=" cfg/sound.scc cfg/paravirt_kvm.scc"
KERNEL_FEATURES_append_qemux86-64=" cfg/sound.scc cfg/paravirt_kvm.scc"
KERNEL_FEATURES_append = " ${@bb.utils.contains("TUNE_FEATURES", "mx32", " cfg/x32.scc", "" ,d)}"

IMAGE_INSTALL_append = " kernel-modules"

# hack for now, Module.symvers should only be created while building the modules
# but do_shared_workdir() executes before modules are built and so file is not found
# create empty Module.symvers which will be replaced with the actual one later.
copy_module_symvers () {
	# The module bbclass tries to copy the
	# Module.symvers from ${B}, so make
	# sure it actually is there
	touch ${B}/Module.symvers
}

do_shared_workdir[prefuncs] += "copy_module_symvers"
