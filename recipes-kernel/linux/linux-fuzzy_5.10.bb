require recipes-kernel/linux/linux-yocto.inc

# These are variables that should be added to any
# kernel compatible with meta-intel

KERNEL_FEATURES_INTEL_COMMON ?= ""

COMPATIBLE_MACHINE_intel-x86-common = "${MACHINE}"
#KERNEL_FEATURES_intel-x86-common = "${KERNEL_FEATURES_INTEL_COMMON}"

KMACHINE_corei7-64-intel-common = "intel-corei7-64"
KMACHINE_core2-32-intel-common = "intel-core2-32"
KMACHINE_skylake-64-intel-common = "intel-corei7-64"

INTEL_COMMON_AUTOLOAD ?= " uio iwlwifi i915"
#KERNEL_MODULE_AUTOLOAD_intel-x86-common = " ${INTEL_COMMON_AUTOLOAD}"

FILESEXTRAPATHS_prepend := "${THISDIR}/linux-intel:"

LINUX_VERSION_EXTENSION ??= "-intel-pk-${LINUX_KERNEL_TYPE}"

PV = "${LINUX_VERSION}+git${SRCPV}"

KCONF_BSP_AUDIT_LEVEL = "2"

COMPATIBLE_MACHINE ?= "(intel-corei7-64|intel-core2-32)"

KBRANCH = "5.10/yocto"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

#SRC_URI_append = " file://0001-menuconfig-mconf-cfg-Allow-specification-of-ncurses-.patch \
#                   "

DEPENDS += "elfutils-native openssl-native util-linux-native"

LINUX_VERSION ?= "5.10.35"
#SRCREV_machine ?= "0fd9e7f81ac665d77c40d5c4a3815e46d4d07e90"
#SRCREV ?= "905402f3e91a6ff116c6da1bcad7c56ac1e486ff"
SRCREV ?= "f63887d8a29ceb39f0575d6a2e74c805e71dea83"

KERNEL_CONFIG_URI="file://defconfig"

SRC_URI = " \
           git://github.com/intel/linux-intel-lts.git;protocol=https;name=machine;branch=${KBRANCH}; \
           ${KERNEL_CONFIG_URI} \
           file://kernel_custom_config.cfg \
           file://cisco_config.cfg \	
	  "

# For Crystalforest and Romley
#KERNEL_MODULE_AUTOLOAD_append_core2-32-intel-common = " uio"
#KERNEL_MODULE_AUTOLOAD_append_corei7-64-intel-common = " uio"

# Functionality flags
#KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc features/security/security.scc"

# Following commit is backported from mainline 5.12-rc to linux-intel 5.10 kernel
# Commit: https://github.com/torvalds/linux/commit/26499e0518a77de29e7db2c53fb0d0e9e15be8fb
# In which 'CONFIG_DRM_GMA3600' config option is dropped.
# This causes warning during config audit. So suppress the harmless warning for now.
KCONF_BSP_AUDIT_LEVEL = "0"

# Disabling CONFIG_SND_SOC_INTEL_SKYLAKE for 32-bit, does not allow to set CONFIG_SND_SOC_INTEL_SST too, which
# causes config warning too.
KCONF_AUDIT_LEVEL_core2-32-intel-common = "0"

KERNEL_VERSION_SANITY_SKIP="1"

require ${@bb.utils.contains('DISTRO_FEATURES', 'efi-secure-boot', 'mender-kernel-linux-yocto.inc', '', d)}

SRC_URI += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'tpm2', \
                         'file://tpm2.scc file://tpm2.cfg', '', d)} \
"
