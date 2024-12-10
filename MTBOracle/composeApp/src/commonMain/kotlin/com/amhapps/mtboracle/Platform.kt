package com.amhapps.mtboracle

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform