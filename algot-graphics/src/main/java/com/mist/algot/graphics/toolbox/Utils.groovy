package com.mist.algot.graphics.toolbox

import com.google.common.base.Preconditions

class Utils {

    static <T> List<T> expand(List<T> list, int number) {
        Preconditions.checkArgument(number >= 0)
        List<T> result = []
        list.each {
            for (int i = 0; i < number; i++) {
                result << it
            }
        }
        result
    }

}
