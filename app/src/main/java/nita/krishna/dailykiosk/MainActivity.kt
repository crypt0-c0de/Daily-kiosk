package nita.krishna.dailykiosk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    lateinit var newsList: RecyclerView
    lateinit var container : ConstraintLayout
    private var articles = mutableListOf<Article>()
    var pageNum = 1
    var totalResults = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsList = findViewById(R.id.newsList)
        container = findViewById(R.id.container)

        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
        newsList.layoutManager = LinearLayoutManager(this@MainActivity)



//        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
//        layoutManager.setPagerMode(true)
//        layoutManager.setPagerFlingVelocity(3000)
//        layoutManager.setItemChangedListener(object: StackLayoutManager.ItemChangedListener {
//            override fun onItemChanged(position: Int) {
//                container.setBackgroundColor(Color.parseColor("#FF3322"))
//            }
//
//        })
//        newsList.layoutManager = layoutManager

        getNews()
    }

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in",pageNum)
        news.enqueue(object: Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news: News? = response.body()
                if(news != null) {
                    Log.d("Krishna", news.toString())
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Krishna", "Error in Fetching News")
            }

        })
    }
}
