package com.mist.algot.graphics.toolbox

import spock.lang.Specification

import static com.mist.algot.graphics.toolbox.Utils.expand

class UtilsTest extends Specification {

    def "should correctly expand list"() {
        expect:
        expand([1, 2, 3], 3) == [1, 1, 1, 2, 2, 2, 3, 3, 3]
        expand([0], 5) == [0, 0, 0, 0, 0]
        expand([1, 2, 3], 0) == []
        expand([], 10) == []
    }
}
