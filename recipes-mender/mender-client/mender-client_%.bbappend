FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " \
		file://server.crt \
		file://rpm-update-module \
        file://fds-update-module \
        file://mender.conf \
"

FILES_${PN} += "/usr/share/mender/modules/v3/rpm /usr/share/mender/modules/v3/fds /etc/sevrer/server.crt"

do_install_append() {
    install -d ${D}/usr/share/mender/modules/v3
    install -m 0755 ${WORKDIR}/rpm-update-module ${D}/usr/share/mender/modules/v3/rpm
    install -m 0755 ${WORKDIR}/fds-update-module ${D}/usr/share/mender/modules/v3/fds

    install -m 0744 ${WORKDIR}/server.crt ${D}/etc/mender/server.crt
}

# enable check for artifact signature
SRC_URI_append = " file://artifact-verify-key.pem"