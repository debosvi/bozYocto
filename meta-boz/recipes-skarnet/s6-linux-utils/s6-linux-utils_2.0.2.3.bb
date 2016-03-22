SUMMARY = "s6-linux-utils is a set of minimalistic Linux-specific system utilities."
HOMEPAGE = "http://www.skarnet.org/software/s6-linux-utils/"
LICENSE = "ISC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=1500f33d86c4956999052c0e137cd652"

DEPENDS = "skalibs"

SRC_URI = " \
    http://skarnet.org/software/s6-linux-utils/s6-linux-utils-${PV}.tar.gz;name=tarball \
    "

SRC_URI[tarball.md5sum] = "e4ae28e1ca13d9cd7a226a2729c71bbf"
SRC_URI[tarball.sha256sum] = "f959ffb9bb79865018becc6664d29faef22cb747a43db252879e11886b1b8cc3"

# inherit autotools pkgconfig

# EXTRA_OECONF = "--with-sysdeps=${WORKDIR}/sysdeps"

do_configure() {
    ./configure --with-sysdeps=${STAGING_DIR_HOST}/usr/lib/skalibs/sysdeps  \
        --with-include=${STAGING_DIR_HOST}/usr/include  \
        --with-lib=${STAGING_DIR_HOST}/usr/lib/skalibs  \
        --disable-shared
}

do_install() {
    make DESTDIR=${D} -C ${B} install-dynlib install-libexec install-bin install-sbin install-data
}

do_populate_sysroot() {
    make DESTDIR=${STAGING_DIR_HOST} -C ${B} install-lib install-include
}