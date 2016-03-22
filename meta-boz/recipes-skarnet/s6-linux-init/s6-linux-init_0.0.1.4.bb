SUMMARY = "s6-linux-init is a set of minimalistic tools to create a s6-based init system, including a /sbin/init binary, on a Linux kernel."
HOMEPAGE = "http://www.skarnet.org/software/s6-linux-init/"
LICENSE = "ISC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=a687612ed70a29618cd6987e9c0fd55a"

DEPENDS = "skalibs"

SRC_URI = " \
    http://skarnet.org/software/s6-linux-init/s6-linux-init-${PV}.tar.gz;name=tarball \
    "

SRC_URI[tarball.md5sum] = "93fb01d74e453e68ddf7af02c96bbc2f"
SRC_URI[tarball.sha256sum] = "80eeb8e3bf3095d69b86aaf8e6f5c28aab53a0eb6473e301a673870b56566b6a"

# inherit autotools pkgconfig

# EXTRA_OECONF = "--with-sysdeps=${WORKDIR}/sysdeps"

do_configure() {
    ./configure --with-sysdeps=${STAGING_DIR_HOST}/usr/lib/skalibs/sysdeps  \
        --with-include=${STAGING_DIR_HOST}/usr/include  \
        --with-lib=${STAGING_DIR_HOST}/usr/lib/skalibs  \
        --with-lib=${STAGING_DIR_HOST}/usr/lib/execline \
        --with-lib=${STAGING_DIR_HOST}/usr/lib/s6-portable-utils    \
        --with-lib=${STAGING_DIR_HOST}/usr/lib/s6-linux-utils   \
        --with-lib=${STAGING_DIR_HOST}/usr/lib/s6   \
        --disable-shared
}

do_install() {
    make DESTDIR=${D} -C ${B} install-dynlib install-libexec install-bin install-sbin install-data
}

do_populate_sysroot() {
    make DESTDIR=${STAGING_DIR_HOST} -C ${B} install-lib install-include
}