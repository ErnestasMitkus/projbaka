package com.mist.algot

class Reporter {

    static instance = new Reporter()

    private final PrintStream filePrintStream

    private Reporter() {
        this.filePrintStream = null
    }

    private Reporter(String fileName) {
        this.filePrintStream = new PrintStream(fileName)
    }

    static void setupReporter(String fileName) {
        instance = new Reporter(fileName)
    }

    static void printLine(String str) {
        println str
        !instance.filePrintStream ?: instance.filePrintStream.println(str)
    }

    static void flush() {
        !instance.filePrintStream ?: instance.flush()
    }
}
