package com.finstats.transaction

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * The endpoint is called every time transaction is made
 */
@RestController
class TransactionController {

    /**
     * Should execute in O(1) time and space
     *
     * @return empty body with status code 201 or 204
     *
     * 201 - success
     * 204 - if transaction is older than 60 seconds
     */
    @PostMapping("/transactions")
    fun transactions(@RequestBody transaction: Transaction) {
        println(transaction)
    }
}