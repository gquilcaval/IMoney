package com.example.imoney.domain

import androidx.lifecycle.LiveData
import com.example.imoney.data.entity.Transaccion
import com.example.imoney.data.repository.TransaccionRepository
import javax.inject.Inject

class GetFrTransaccionUseCase @Inject constructor(
    private val repository : TransaccionRepository
) {



   suspend operator fun invoke(): LiveData<List<Transaccion>> = repository.getAllTransaccion()

}