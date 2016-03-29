package cn.jason.kotlin.bean

/**
 * Created by jun on 3/10/16.
 */
data class Story(
        var title: String,
        var ga_prefix: String,
        var images: List<String>,
        var type: Int,
        var id: Int
)