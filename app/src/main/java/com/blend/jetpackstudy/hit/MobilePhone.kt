package com.blend.jetpackstudy.hit

import javax.inject.Inject


class MobilePhone @Inject constructor(val simCard: SimCard) {
    fun dialNumber() {
        simCard.dialNumber()
    }
}