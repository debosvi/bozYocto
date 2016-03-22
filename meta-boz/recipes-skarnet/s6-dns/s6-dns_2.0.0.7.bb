SUMMARY = "s6-dns is a suite of DNS client programs and libraries for Unix systems, as an alternative to the BIND, djbdns or other DNS clients."
HOMEPAGE = "http://www.skarnet.org/software/s6-dns/"
LICENSE = "ISC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=1500f33d86c4956999052c0e137cd652"

DEPENDS = "skalibs"

SRC_URI = " \
    http://skarnet.org/software/s6-dns/s6-dns-${PV}.tar.gz;name=tarball \
    "

SRC_URI[tarball.md5sum] = "55ea7f361af4d1142d5b553afdbb5c36"
SRC_URI[tarball.sha256sum] = "d37f05810d35ee4f98afe35467c856de10a5878e8d2ad19ccbc6599a58c6442a"

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