package com.mukesh.reliv.model

data class MesiboUserTokenResponse(
    val app: App,
    val uts: String,
    val disabled: Long,
    val ipaddr: String,
    val user: User,
    val op: String,
    val result: Boolean
)

data class App(
    val aid: String,
    val uid: String,
    val name: String,
    val secret: String,
    val u_users: String,
    val u_groups: String,
    val d_storage: String,
    val url: String,
    val server: String,
    val notify: String,
    val nrate: String,
    val ninterval: String,
    val flag: String,
    val f_beta: String,
    val ts: String,
    val uts: String,
    val fcm_id: String,
    val fcm_key: String,
    val apn_info: String,
    val pushflags: String,
    val token: String
)

data class User(
    val uid: String,
    val token: String
)
