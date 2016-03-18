SUMMARY = "skalibs is a package centralizing the free software / open source C development files used for building all software at skarnet.org"
HOMEPAGE = "http://skarnet.org/software/skalibs/"
LICENSE = "ISC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=1500f33d86c4956999052c0e137cd652"

# DEPENDS = "virtual/libusb0 pcsc-lite"
# RDEPENDS_${PN} = "pcsc-lite"

SRC_URI = " \
    http://skarnet.org/software/skalibs/skalibs-${PV}.tar.gz;name=tarball \
    file://sysdeps \
    "

SRC_URI[tarball.md5sum] = "8cc1dfad59a588ba3956d78c81b5ea0a"
SRC_URI[tarball.sha256sum] = "6229fb4fb415699bbff3b446ff44aa5b7fb9c512b83bd68ae4005603afab60cb"

# inherit autotools pkgconfig

# FILES_${PN} += "${libdir}/pcsc/"
# FILES_${PN}-dbg += "${libdir}/pcsc/drivers/*/*/*/.debug"

# EXTRA_OECONF = "--with-sysdeps=${WORKDIR}/sysdeps"

do_configure() {
    echo ${TARGET_SYS} > ${WORKDIR}/sysdeps/target
    ./configure --with-sysdeps=${WORKDIR}/sysdeps  --disable-shared
}

do_install() {
    make DESTDIR=${D} -C ${B} install-data install-dynlib
}

do_populate_sysroot() {
    make DESTDIR=${STAGING_DIR} -C ${B} install-sysdeps install-lib install-include
}