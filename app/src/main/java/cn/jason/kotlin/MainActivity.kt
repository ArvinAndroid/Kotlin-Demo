package cn.jason.kotlin

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.jason.kotlin.bean.LatesNews
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_item.view.*
import org.jetbrains.anko.async
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import android.provider.MediaStore.Audio.Artists
import android.util.Log
import android.widget.Toast
import cn.jason.kotlin.bean.Story

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 通过Anko的函数来简化获取RecyclearView的代码
        val recyclerViwe: RecyclerView = find(R.id.recycler)
        recyclerViwe.itemAnimator = DefaultItemAnimator()
        recyclerViwe.layoutManager = LinearLayoutManager(this)
        recyclerViwe.setHasFixedSize(true)
        //创建一个子线程执行网络请求
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
        var context: Context? = null
        val stories = latesNews.stories

        //绑定ViewHolder,先定义变量名，再指定类型
        override fun onBindViewHolder(viewHolder: RecyclerViewHolder, position: Int) {
            var story: Story = stories.get(position)
            viewHolder.itemView.tv_item.text = story.title;
            var imgUrl = story.images.get(0)
            viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    Toast.makeText(context, "Click " + " " + story.title, Toast.LENGTH_LONG).show();
                }
            })
            Glide.with(context).load(imgUrl).into(viewHolder.itemView.img_item)
        }

        //:后表示返回类型，＝后表示返回值
        override fun getItemCount(): Int = stories.size

        //生成ViewHolder对象，：后代表返回RecyclerViewHolder,
        //＝后表示创建一个RecyclerViewHolder对象，类似java中new RecyclerViewHolder
        override fun onCreateViewHolder(viewGroup: ViewGroup?, p1: Int):
                RecyclerViewHolder {
            context = viewGroup!!.context
            return RecyclerViewHolder(LayoutInflater.from(viewGroup!!.context).inflate(R.layout.view_item, null))
        }

        //创建ViewHolder对象，继承RecyclerView.ViewHolder，并绑定一个TextView控件
        class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var textView: TextView? = null
            private var imgView: ImageView? = null

            init {
                textView = itemView.find(R.id.tv_item)
                imgView = itemView.find(R.id.img_item)
            }
        }

    }


}
