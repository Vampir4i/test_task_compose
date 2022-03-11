package com.example.testtaskcompose.retrofit

import com.google.gson.annotations.SerializedName

class CommonProfile(
    var id: Int? = null,
    var login: String? = null,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null
)

class GitProfile(
    var login: String? = null,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
    var url: String? = null,
    @SerializedName("public_repos")
    var publicRepos: Int? = null,
    @SerializedName("public_gists")
    var publicGists: Int? = null,
    var followers: Int? = null
)

class CommonRepo(
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var language: String? = null,
)

class CommonGist(
    var id: String? = null,
    var description: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    var files: List<HashMap<String, GistsFile>>? = null
) {
    class GistsFile(
        var language: String? = null
    )
}

