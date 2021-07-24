package nita.krishna.dailykiosk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var adapter: NewsAdapter
    private lateinit var newsList: RecyclerView
    lateinit var progressBar : ProgressBar
    private var articles = mutableListOf<Article>()
    private var pageNum = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsList = findViewById(R.id.newsList)
        progressBar = findViewById(R.id.progressBar)

        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
        newsList.layoutManager = LinearLayoutManager(this@MainActivity)

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
                    progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Krishna", "Error in Fetching News")
            }

        })
    }
}
