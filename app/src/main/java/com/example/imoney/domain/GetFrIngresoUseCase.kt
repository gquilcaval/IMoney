package com.example.imoney.domain

import com.example.imoney.data.entity.Transaccion
import com.example.imoney.data.repository.TransaccionRepository
import javax.inject.Inject

class GetFrIngresoUseCase @Inject constructor(
    private val repository : TransaccionRepository
) {



   suspend operator fun invoke(transaccion: Transaccion) = repository.insertTransaccion(transaccion)

}