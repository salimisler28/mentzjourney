package com.mentz.mentzjourney.data.dto

data class LocationDto(
    val id: String,
    val isGlobalId: Boolean?,
    val name: String,
    val disassembledName: String?,
    val coord: List<Long>,
    val streetName: String?,
    val type: String,
    val matchQuality: Int,
    val isBest: Boolean,
    val productClasses: List<Int>?,
    val parent: ParentDto,
    val properties: PropertiesDto
)
