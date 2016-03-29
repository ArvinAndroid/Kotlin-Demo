package cn.jason.kotlin

import android.util.Log
import cn.jason.kotlin.bean.LatesNews
import com.google.gson.Gson
import java.net.URL

/**
 * Created by jun on 3/9/16.
 */
class Request(var url: String) {
    public fun run(): LatesNews {
        var result = URL(url).readText()
        return Gson().fromJson(result, LatesNews::class.java)
    }
}
