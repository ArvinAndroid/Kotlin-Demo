package cn.jason.kotlin

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : Activity() {

    private val items = listOf<String>("1", "2", "3", "6", "7", "8", "9", "0")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //通过findViewById获取到RecyclearView，并强转类型
        val recyclerView = findViewById(R.id.recycler) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        //通过类似赋值的方式设置Adapter
        recyclerView.adapter = RecyclerAdapter(items);

    }

    /**
     * 新建内部类Adapter， 继承RecyclerView.Adapter
     */
    class RecyclerAdapter(val items: List<String>) : //定义数据源,通过:代替(extends)
            RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

        //绑定ViewHolder,先定义变量名，再：指定类型
        override fun onBindViewHolder(p0: RecyclerViewHolder, p1: Int) {
            p0.textView.text = items.get(p1);
        }

        //:后表示返回类型，＝后表示返回值
        override fun getItemCount(): Int = items.size

        //生成ViewHolder对象，：后代表返回RecyclerViewHolder,
        //＝后表示创建一个RecyclerViewHolder对象，类似java中new RecyclerViewHolder
        override fun onCreateViewHolder(p0: ViewGroup?, p1: Int):
                RecyclerViewHolder = RecyclerViewHolder(TextView(p0?.context))

        //创建ViewHolder对象，继承RecyclerView.ViewHolder，并绑定一个TextView控件
        class RecyclerViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    }

}
