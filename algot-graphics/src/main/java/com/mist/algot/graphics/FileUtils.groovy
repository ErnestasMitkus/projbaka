package com.mist.algot.graphics

class FileUtils {

    public static InputStream loadFile(String filePath) {
        filePath.startsWith("/") ?
            FileUtils.class.getResourceAsStream(filePath) :
            new FileInputStream(filePath)
    }

}
