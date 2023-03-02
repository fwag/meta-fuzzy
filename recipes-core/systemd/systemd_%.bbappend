FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://resolved.conf \
"

FILES_${PN} += " \
    ${sysconfdir}/systemd/resolved.conf \
"

do_install_append() {
    # Adding default dns for systemd-resolved
    install -d ${D}${sysconfdir}/systemd/
    install -m 0644 ${WORKDIR}/resolved.conf ${D}${sysconfdir}/systemd/
}

RDEPENDS_${PN} += "bash"
