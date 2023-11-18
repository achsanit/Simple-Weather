package com.achsanit.simpleweather.foundation.utils

/**
 * Contains a set of error codes
 */
object CodeError {
    /**
     * Set when get data from local storage and throw exception
     */
    const val SOMETHING_WENT_WRONG = -1
    /**
     * Set NO INTERNET CONNECTION
     */
    const val NO_INTERNET_CONNECTION = 1
    const val REQUEST_TIME_OUT = 2
    /**
     * Set when get data from internet and get
     */
    const val EMPTY = 0
}