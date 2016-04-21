DESCRIPTION = "Apache thrift"
LICENSE = "LGPLv2.1+"
SECTION = "libs"
DEPENDS = "boost openssl zlib libevent"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e4ed21f679b2aafef26eac82ab0c2cbf"

           
SRC_URI = "https://dist.apache.org/repos/dist/release/thrift/${PV}/thrift-${PV}.tar.gz \
		   file://automake-foreign.patch"

SRC_URI[md5sum] = "88d667a8ae870d5adeca8cb7d6795442"
SRC_URI[sha256sum] = "b0740a070ac09adde04d43e852ce4c320564a292f26521c46b78e0641564969e"

S = "${WORKDIR}/thrift-${PV}"

inherit cmake

BOOST_PATH = "${TMPDIR}/sysroots/${MACHINE}/usr/"
BOOST_PATH_class-native = "${TMPDIR}/sysroots/${BUILD_SYS}/usr/"

EXTRA_OECMAKE += "  \
    -DWITH_PYTHON=OFF   \
    -DWITH_C_GLIB=OFF   \
    -DWITH_QT4=OFF  \
    -DWITH_QT5=OFF  \
    -DWITH_BOOSTTHREADS=ON"
    
EXTRA_OECMAKE += "  \
    -DBUILD_TESTING=OFF     \
    -DBUILD_EXAMPLES=OFF    \
    -DBUILD_TUTORIALS=OFF"
    
EXTRA_OECMAKE += "  \
    -DTHRIFT_COMPILER_C_GLIB=OFF  \
    -DTHRIFT_COMPILER_CPP=ON  \
    -DTHRIFT_COMPILER_JAVA=OFF  \
    -DTHRIFT_COMPILER_AS3=OFF  \
    -DTHRIFT_COMPILER_HAXE=OFF  \
    -DTHRIFT_COMPILER_CSHARP=OFF  \
    -DTHRIFT_COMPILER_PY=OFF  \
    -DTHRIFT_COMPILER_RB=OFF  \
    -DTHRIFT_COMPILER_PERL=OFF  \
    -DTHRIFT_COMPILER_PHP=OFF  \
    -DTHRIFT_COMPILER_ERL=OFF  \
    -DTHRIFT_COMPILER_COCOA_GLIB=OFF  \
    -DTHRIFT_COMPILER_ST=OFF  \
    -DTHRIFT_COMPILER_OCAML=OFF  \
    -DTHRIFT_COMPILER_XSD=OFF  \
    -DTHRIFT_COMPILER_HTML=OFF  \
    -DTHRIFT_COMPILER_JS=OFF  \
    -DTHRIFT_COMPILER_JSON=OFF  \
    -DTHRIFT_COMPILER_JAVAME=OFF  \
    -DTHRIFT_COMPILER_DELPHI=OFF  \
    -DTHRIFT_COMPILER_GO=OFF  \
    -DTHRIFT_COMPILER_D=OFF  \
    -DTHRIFT_COMPILER_LUA=OFF"

#EXTRA_OECONF += " ac_cv_lib_lex=-L${TMPDIR}/sysroots/${MACHINE}/usr/lib libfl.a"

PACKAGE_BEFORE_PN = "${PN}-tools"
FILES_${PN}-tools = "${bindir}/thrift"
FILES_${PN} = "${libdir}/libthrift.so.${PV} ${libdir}/libthriftz.so.${PV} ${libdir}/libthriftnb.so.${PV}"
FILES_SOLIBSDEV = "${libdir}/libthrift.so ${libdir}/libthriftz.so ${libdir}/libthriftnb.so"

BBCLASSEXTEND = "native"
