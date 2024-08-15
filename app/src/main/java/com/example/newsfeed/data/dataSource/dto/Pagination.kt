package com.example.newsfeed.data.dataSource.dto

data class Pagination(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val total: Int
)