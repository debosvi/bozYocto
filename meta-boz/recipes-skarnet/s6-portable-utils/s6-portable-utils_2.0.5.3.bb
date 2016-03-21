SUMMARY = "s6-portable-utils is a set of tiny general Unix utilities, often performing well-known tasks such as cut and grep, but optimized for simplicity and small size."
HOMEPAGE = "http://www.skarnet.org/software/s6-portable-utils/"
LICENSE = "ISC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=1500f33d86c4956999052c0e137cd652"

DEPENDS = "skalibs"

SRC_URI = " \
    http://skarnet.org/software/s6-portable-utils/s6-portable-utils-${PV}.tar.gz;name=tarball \
    "

SRC_URI[tarball.md5sum] = "09612618faf23e9981597ce634742194"
SRC_URI[tarball.sha256sum] = "27be01b3d66df617ea7dbc21b55d253d5b9142463099f6944f90ef33587a2e09"

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