package cn.jason.kotlin

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import cn.jason.kotlin.bean.LatesNews
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 通过Anko的函数来简化获取RecyclearView的代码
        val recyclerViwe: RecyclerView = find(R.id.recycler)

        recyclerViwe.layoutManager = LinearLayoutManager(this)
        //创建一个自县城执行网络请求
        async() {
            val result = Request("http://news-at.zhihu.com/api/4/news/latest").run()
            //通过uiThread返回到主线程，Refresh UI
            uiThread {
                recyclerViwe.adapter = RecyclerAdapter(result)
            }

        }
    }

    /**
     * 新建内部类Adapter， 继承RecyclerView.Adapter
     */
    class RecyclerAdapter(val latesNews: LatesNews) : //定义数据源,通过:代替(extends)
            RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

        val stories = latesNews.stories;

        //绑定ViewHolder,先定义变量名，再：指定类型
        override fun onBindViewHolder(p0: RecyclerViewHolder, p1: Int) {
            p0.textView.text = stories.get(p1).title;
        }

        //:后表示返回类型，＝后表示返回值
        override fun getItemCount(): Int = stories.size

        //生成ViewHolder对象，：后代表返回RecyclerViewHolder,
        //＝后表示创建一个RecyclerViewHolder对象，类似java中new RecyclerViewHolder
        override fun onCreateViewHolder(p0: ViewGroup?, p1: Int):
                RecyclerViewHolder = RecyclerViewHolder(TextView(p0?.context))

        //创建ViewHolder对象，继承RecyclerView.ViewHolder，并绑定一个TextView控件
        class RecyclerViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    }

}
