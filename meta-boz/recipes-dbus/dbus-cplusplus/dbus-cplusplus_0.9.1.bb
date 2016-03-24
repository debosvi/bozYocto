SUMMARY = "skalibs is a package centralizing the free software / open source C development files used for building all software at skarnet.org."
HOMEPAGE = "https://github.com/sstiller/dbus-cplusplus.git"
LICENSE = "LGPL-2.1"
SECTION = "libs"
LIC_FILES_CHKSUM = "file://COPYING;md5=7266a93b753b03bc5f00522e65722b79"

DEPENDS = "libtool-native expat"

SRCREV = "3e33cb73b5353fc4ba015961be4823e3d820651c"
SRC_URI = "https://github.com/sstiller/dbus-cplusplus.git"

inherit autotools

EXTRA_OECONF = "--disable-ecore --disable-glib --enable-asio --disable-doxygen-docs"

