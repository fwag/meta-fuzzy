DESCRIPTION = "Image for UCS Series"
LICENSE = "CLOSED"

require recipes-core/images/core-image-minimal.bb

require ${@bb.utils.contains('DISTRO_FEATURES', 'efi-secure-boot', 'conf/include/mender-kernel-image.inc', '', d)}

inherit features_check extrausers

IMAGE_FEATURES += "read-only-rootfs"

CORE_IMAGE_EXTRA_INSTALL += " kernel-modules smack"

IMAGE_INSTALL_append = " \
    attr \
    bridge-utils \
    iptables \
    logrotate \
    ntp \
    ntp-utils \
    openssh \
    openssh-scp \
    openssh-ssh \
    os-release \
    rpm \
    sudo \
    ota-state-scripts \
    "
