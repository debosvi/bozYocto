SUMMARY = "s6-rc is a service manager for s6-based systems, i.e. a suite of programs that can start and stop services, both long-running daemons and one-time initialization scripts, in the proper order according to a dependency tree."
HOMEPAGE = "http://www.skarnet.org/software/s6/"
LICENSE = "ISC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=a687612ed70a29618cd6987e9c0fd55a"

DEPENDS = "s6"

SRC_URI = " \
    http://skarnet.org/software/s6-rc/s6-rc-${PV}.tar.gz;name=tarball \
    "

FILES_${PN} +=  "${base_prefix}/libexec"
FILES_${PN}-dbg += "${base_prefix}/libexec/.debug"
   
SRC_URI[tarball.md5sum] = "23c15cf3413f34692dc1d6775e5cc54a"
SRC_URI[tarball.sha256sum] = "93f30557e2e2e5c507b5becef2bd26f377be6b6c542fb4627c3122e1c2d83ef9"

# inherit autotools pkgconfig

# EXTRA_OECONF = "--with-sysdeps=${WORKDIR}/sysdeps"

do_configure() {
    ./configure --with-sysdeps=${STAGING_DIR_HOST}/usr/lib/skalibs/sysdeps  --with-include=${STAGING_DIR_HOST}/usr/include --with-lib=${STAGING_DIR_HOST}/usr/lib/skalibs --with-lib=${STAGING_DIR_HOST}/usr/lib/execline --with-lib=${STAGING_DIR_HOST}/usr/lib/s6 --disable-shared
}

do_install() {
    make DESTDIR=${D} -C ${B} install-dynlib install-libexec install-bin install-sbin install-data
}

do_populate_sysroot() {
    make DESTDIR=${STAGING_DIR_HOST} -C ${B} install-lib install-include
}