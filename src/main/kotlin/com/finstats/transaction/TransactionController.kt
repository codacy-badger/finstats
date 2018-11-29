package com.finstats.transaction

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TransactionController {

    @PostMapping("/transaction")
    fun transaction(@RequestBody transaction: Transaction) {
        println(transaction)
    }
}