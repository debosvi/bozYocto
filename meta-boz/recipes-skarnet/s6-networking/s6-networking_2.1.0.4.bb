SUMMARY = "s6-networking is a suite of small networking utilities for Unix systems."
HOMEPAGE = "http://www.skarnet.org/software/s6-networking/"
LICENSE = "ISC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=1500f33d86c4956999052c0e137cd652"

DEPENDS = "skalibs"

SRC_URI = " \
    http://skarnet.org/software/s6-networking/s6-networking-${PV}.tar.gz;name=tarball \
    "

SRC_URI[tarball.md5sum] = "07a19e69db4978ef5c03cfd7daf3a827"
SRC_URI[tarball.sha256sum] = "0fb4bd02371077c6bed2878d22c9acbfc01834da6752277fbb92d8312076640a"

# inherit autotools pkgconfig

# EXTRA_OECONF = "--with-sysdeps=${WORKDIR}/sysdeps"

do_configure() {
    ./configure --with-sysdeps=${STAGING_DIR_HOST}/usr/lib/skalibs/sysdeps  \
        --with-include=${STAGING_DIR_HOST}/usr/include  \
        --with-lib=${STAGING_DIR_HOST}/usr/lib/skalibs  \
        --with-lib=${STAGING_DIR_HOST}/usr/lib/execline \
        --with-lib=${STAGING_DIR_HOST}/usr/lib/s6   \
        --with-lib=${STAGING_DIR_HOST}/usr/lib/s6-dns   \
        --disable-shared
}

do_install() {
    make DESTDIR=${D} -C ${B} install-dynlib install-libexec install-bin install-sbin install-data
}

do_populate_sysroot() {
    make DESTDIR=${STAGING_DIR_HOST} -C ${B} install-lib install-include
}