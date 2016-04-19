SUMMARY = "SQLCipher is an SQLite extension that provides transparent 256-bit AES encryption of database files."
HOMEPAGE = "https://www.zetetic.net/sqlcipher/"
LICENSE = "LLC"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7edde5c030f9654613438a18b9b56308"

DEPENDS = "tcl-native"
# RDEPENDS = "libcrypto"

SRC_URI = "https://github.com/sqlcipher/sqlcipher/archive/v${PV}.tar.gz;name=tarball"

SRC_URI[tarball.md5sum] = "2225da8fc163f78b5fffd0bcf8b28695"
SRC_URI[tarball.sha256sum] = "99b702ecf796de02bf7b7b35de4ceef145f0d62b4467a86707c2d59beea243d0"

inherit autotools

EXTRA_OECONF = "--disable-tcl --enable-tempstore=yes CFLAGS=-DSQLITE_HAS_CODEC"
EXTRA_OEMAKE = '"LIBTOOL=${STAGING_BINDIR_CROSS}/${HOST_SYS}-libtool"'



