package com.mobiquity.weatherapp.model


data class ForeCast(
    var temp: Double,
    var humdity: Int,
    var clouds: String,
    var CloudsDescr: String,
    var icon: String,
    var speed: Double,
    var date: String


) {
    override fun hashCode(): Int {
        return 1
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null) return false
        if (this.javaClass != o.javaClass) return false
        val user: ForeCast = o as ForeCast
        return (
                (date.equals(user.date)
                        ))
    }
}
