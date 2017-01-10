package com.mist.algot.graphics.utils

import com.mist.algot.graphics.hidden.Exchange
import com.mist.algot.graphics.hidden.RemovalAlgorithm

class AlgorithmHelpers {

    static void pipe(Exchange exchange, RemovalAlgorithm... algs) {
        algs.each { it.apply(exchange) }
    }
}
