SUMMARY = "execline is a (non-interactive) scripting language, like sh ; but its syntax is quite different from a traditional shell syntax"
HOMEPAGE = "http://www.skarnet.org/software/execline/"
LICENSE = "ISC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=1500f33d86c4956999052c0e137cd652"

DEPENDS = "skalibs"

SRC_URI = " \
    http://skarnet.org/software/execline/execline-${PV}.tar.gz;name=tarball \
    "

SRC_URI[tarball.md5sum] = "94eae34d6ebb1141864a2d9f7babd342"
SRC_URI[tarball.sha256sum] = "ab4451d4992a5d7fb2ae34cffe4d0806e4902bf9aa27ee3b75617aa35379356f"

# inherit autotools pkgconfig

# EXTRA_OECONF = "--with-sysdeps=${WORKDIR}/sysdeps"

do_configure() {
    ./configure --with-sysdeps=${STAGING_DIR_HOST}/usr/lib/skalibs/sysdeps  --with-include=${STAGING_DIR_HOST}/usr/include --with-lib=${STAGING_DIR_HOST}/usr/lib/skalibs --disable-shared
}

do_install() {
    make DESTDIR=${D} -C ${B} install-dynlib install-libexec install-bin install-sbin install-data
}

do_populate_sysroot() {
    make DESTDIR=${STAGING_DIR_HOST} -C ${B} install-lib install-include
}