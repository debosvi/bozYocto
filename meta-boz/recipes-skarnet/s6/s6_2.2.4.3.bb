SUMMARY = "s6 is a small suite of programs for UNIX, designed to allow process supervision (a.k.a service supervision), in the line of daemontools and runit, as well as various operations on processes and daemons."
HOMEPAGE = "http://www.skarnet.org/software/s6/"
LICENSE = "ISC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=1500f33d86c4956999052c0e137cd652"

DEPENDS = "execline"

SRC_URI = "http://skarnet.org/software/s6/s6-${PV}.tar.gz;name=tarball"
    
SRC_URI[tarball.md5sum] = "b2c6581cb72a9d6c72f3c7499cbe582a"
SRC_URI[tarball.sha256sum] = "f584ec56d5f2a3a2d81698d5f744b9b64d8fb6c22b56649faa71f3b62da7db3f"
	
PACKAGES =+ "${PN}-sv ${PN}-daemon ${PN}-fifo ${PN}-service ${PN}-sudo ${PN}-log ${PN}-lock ${PN}-fd"

FILES_${PN}-dbg += "${base_prefix}/libexec/.debug"

#FILES_${PN}-dev = "${base_prefix}/"

FILES_${PN}-sv = "${base_bindir}/s6-svscan ${base_bindir}/s6-svscanctl ${base_bindir}/s6-supervise ${base_bindir}/s6-svc"
FILES_${PN}-sv += "${base_bindir}/s6-svok ${base_bindir}/s6-svstat ${base_bindir}/s6-svwait"
FILES_${PN}-sv += "${base_bindir}/s6-svlisten1 ${base_bindir}/s6-svlisten"

FILES_${PN}-daemon = "${base_bindir}/s6-envdir ${base_bindir}/s6-envuidgid ${base_bindir}/s6-fghack"
FILES_${PN}-daemon += "${base_bindir}/s6-setsid ${base_sbindir}/s6-setuidgid ${base_sbindir}/s6-applyuidgid"
FILES_${PN}-daemon += "${base_bindir}/s6-softlimit ${base_bindir}/s6-tai64n ${base_bindir}/s6-tai64nlocal"

FILES_${PN}-fifo = "${base_bindir}/s6-mkfifodir ${base_bindir}/s6-cleanfifodir ${base_bindir}/s6-ftrig-notify"
FILES_${PN}-fifo += "${base_bindir}/s6-ftrig-wait ${base_bindir}/s6-ftrig-listen1 ${base_bindir}/s6-ftrig-listen"
FILES_${PN}-fifo += "${base_bindir}/s6-ftrigrd"

FILES_${PN}-service = "${base_bindir}/s6-ipcclient ${base_bindir}/s6-ipcserver ${base_bindir}/s6-ipcserver-socketbinder"
FILES_${PN}-service += "${base_bindir}/s6-ipcserverd ${base_bindir}/s6-ioconnect ${base_bindir}/s6-ipcserver-access"
FILES_${PN}-service += "${base_bindir}/s6-connlimit ${base_bindir}/s6-accessrules-cdb-from-fs ${base_bindir}/s6-accessrules-fs-from-cdb"

FILES_${PN}-sudo = "${base_bindir}/s6-sudo ${base_bindir}/s6-sudoc ${base_bindir}/s6-sudod"

FILES_${PN}-log = "${base_bindir}/s6-log ${base_bindir}/ucspilogd"

FILES_${PN}-lock = "${base_bindir}/s6-setlock ${base_bindir}/s6lockd ${base_prefix}/libexec/s6lockd-helper"

FILES_${PN}-fd = "${base_bindir}/s6-fdholder*"

# inherit autotools pkgconfig

# EXTRA_OECONF = "--with-sysdeps=${WORKDIR}/sysdeps"

do_configure() {
    ./configure --with-sysdeps=${STAGING_DIR_HOST}/usr/lib/skalibs/sysdeps	\
		--with-include=${STAGING_DIR_HOST}/usr/include	\
		--with-lib=${STAGING_DIR_HOST}/usr/lib/skalibs	\
		--with-lib=${STAGING_DIR_HOST}/usr/lib/execline	\
		--disable-shared
}

do_install() {
    make DESTDIR=${D} -C ${B} install-dynlib install-libexec install-bin install-sbin install-data
}

do_populate_sysroot() {
    make DESTDIR=${STAGING_DIR_HOST} -C ${B} install-lib install-include
}