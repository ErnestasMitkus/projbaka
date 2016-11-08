package com.mist.algot.util

import org.apache.commons.lang3.SystemUtils

class OsHelpers {

    public static String determineNativesPath() {
        String natives = System.getProperty("user.dir") + "/natives"
        switch (true) {
            case SystemUtils.IS_OS_LINUX:
                return "$natives/linux"
            case SystemUtils.IS_OS_WINDOWS:
                return "$natives/windows"
            case SystemUtils.IS_OS_MAC_OSX:
                return "$natives/macosx"
            case SystemUtils.IS_OS_SOLARIS:
                return "$natives/solaris"
            default:
                throw new RuntimeException("Failed to determine natives to use for OS: " +
                        "$SystemUtils.OS_NAME, $SystemUtils.OS_VERSION, $SystemUtils.OS_ARCH")

        }
    }

}
