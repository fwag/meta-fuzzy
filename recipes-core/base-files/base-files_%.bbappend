FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://fstab"

do_install_append() {
    install -d ${D}/data
    install -d ${D}/data/overlay
    install -d ${D}/data/overlay/etc
    install -d ${D}/data/overlay/etc/upper
    install -d ${D}/data/overlay/etc/work
}