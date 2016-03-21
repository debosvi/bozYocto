SUMMARY = "s6 is a small suite of programs for UNIX, designed to allow process supervision (a.k.a service supervision), in the line of daemontools and runit, as well as various operations on processes and daemons."
HOMEPAGE = "http://www.skarnet.org/software/s6/"
LICENSE = "ISC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=1500f33d86c4956999052c0e137cd652"

DEPENDS = "execline"

SRC_URI = " \
    http://skarnet.org/software/s6/s6-${PV}.tar.gz;name=tarball \
    "

FILES_${PN} +=  "${base_prefix}/libexec"
FILES_${PN}-dbg += "${base_prefix}/libexec/.debug"
    
SRC_URI[tarball.md5sum] = "b2c6581cb72a9d6c72f3c7499cbe582a"
SRC_URI[tarball.sha256sum] = "f584ec56d5f2a3a2d81698d5f744b9b64d8fb6c22b56649faa71f3b62da7db3f"

# inherit autotools pkgconfig

# EXTRA_OECONF = "--with-sysdeps=${WORKDIR}/sysdeps"

do_configure() {
    ./configure --with-sysdeps=${STAGING_DIR_HOST}/usr/lib/skalibs/sysdeps  --with-include=${STAGING_DIR_HOST}/usr/include --with-lib=${STAGING_DIR_HOST}/usr/lib/skalibs --with-lib=${STAGING_DIR_HOST}/usr/lib/execline --disable-shared
}

do_install() {
    make DESTDIR=${D} -C ${B} install-dynlib install-libexec install-bin install-sbin install-data
}

do_populate_sysroot() {
    make DESTDIR=${STAGING_DIR_HOST} -C ${B} install-lib install-include
}