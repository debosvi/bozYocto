
#/bin/bash 

## shows debug information
#set -x

GIT_VERSION=`command -v git`
WGET_VERSION=`command -v wget`

ECHO_CMD="echo -e"

CACHE_DLDIR=${CACHE_DLDIR:-/opt/poky/downloads}


POKY_FLAVOR=krogoth
POKY_MAJOR=2.1.1
POKY_VER=15.0.1

POKY_ARCHIVE=poky-${POKY_FLAVOR}-${POKY_VER}.tar.bz2
POKY_URL_ARCHIVE="http://downloads.yoctoproject.org/releases/yocto/yocto-${POKY_MAJOR}/${POKY_ARCHIVE}"
POKY_URL_CHECKSUM="http://downloads.yoctoproject.org/releases/yocto/yocto-${POKY_MAJOR}/${POKY_ARCHIVE}.md5sum"

TMPDIR=${PWD}/tmp
DLDIR=${PWD}/dl
BLDDIR=${PWD}/build

function get_poky {
    if [ ! -f ${DLDIR}/${POKY_ARCHIVE} ]; then
        ${ECHO_CMD} "\e[0;33mDownload poky Git repository\e[0m"
        wget -c ${POKY_URL_ARCHIVE} -O ${DLDIR}/${POKY_ARCHIVE}
    else
        ${ECHO_CMD} "\e[0;32mPoky Git archive located yet\e[0m"    
    fi
}

function populate_poky {
    get_poky
    if [ ! -f ${TMPDIR}/poky/LICENSE ]; then
        ${ECHO_CMD} "\e[0;33mExtract Poky\e[0m"    
        mkdir -pv ${TMPDIR}/poky
        tar -xf ${DLDIR}/${POKY_ARCHIVE} --strip 1  -C ${TMPDIR}/poky
    fi
    if [ ! -L ${TMPDIR}/poky/meta-boz ]; then
        ${ECHO_CMD} "\e[0;33mDo symlink for meta boz directory\e[0m"    
        ln -sfn ${PWD}/meta-boz ${TMPDIR}/poky/meta-boz
    fi
    ${ECHO_CMD} "\e[0;32mPoky ready\e[0m"  
}

function prepare_poky {
    mkdir -pv ${BLDDIR}/conf
    
    sed -e s:REPLACE:${PWD}:g ${PWD}/conf/bblayers.conf > ${BLDDIR}/conf/bblayers.conf
    
    cp -av ${PWD}/conf/local.conf ${BLDDIR}/conf
    cat << EOF >> ${BLDDIR}/conf/local.conf
PREMIRRORS_prepend = "  \
     git://.*/.* file://${CACHE_DLDIR}/ \n \
     ftp://.*/.* file://${CACHE_DLDIR}/ \n \
     http://.*/.* file://${CACHE_DLDIR}/ \n \
     https://.*/.* file://${CACHE_DLDIR}/ \n"
EOF

}

function check_tools {
    ${ECHO_CMD} "\e[0;33mCheck all tools required for this script\e[0m"
    FAILURE=0
    
    # if [ "${GIT_VERSION}" == "" ]; then 
        # ${ECHO_CMD} "\e[0;31mYou must install GIT tool before using this script\e[0m"
        # FAILURE=1
    # fi

    if [ "${WGET_VERSION}" == "" ]; then 
        ${ECHO_CMD} "\e[0;31mYou must install WGET tool before using this script\e[0m"
        FAILURE=1
    fi
    
    if [ ${FAILURE} -eq 1 ]; then
        exit 0;
    fi
}

if [ "$1" == "clean" ]; then
    ${ECHO_CMD} "\e[0;32mClean build\e[0m"
    rm -rf ${BLDDIR}
    exit 1;
fi

if [ "$1" == "distclean" ]; then
    ${ECHO_CMD} "\e[0;32mClean project\e[0m"
    rm -rf ${BLDDIR} ${TMPDIR}
    exit 1;
fi

mkdir -p ${TMPDIR} ${DLDIR} ${BLDDIR}

check_tools
populate_poky
prepare_poky
