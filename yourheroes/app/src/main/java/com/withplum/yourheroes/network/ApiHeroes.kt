package com.withplum.yourheroes.network

class ApiCharacterDataWrapper(
    val code: Int?,
    val data: ApiCharacterDataContainer?
)

class ApiCharacterDataContainer(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<ApiCharacter>?
)

class ApiCharacter(
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnail: ApiCharacterImage?
)

class ApiCharacterImage(
    val path: String?,
    val extension: String?
)