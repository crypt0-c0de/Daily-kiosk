package nita.krishna.dailykiosk

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// https://newsapi.org/v2/top-headlines?country=in&apiKey=4852e0653ba546c3b0df900c95726b48
// https://newsapi.org/v2/everything?q=apple&from=2021-07-22&to=2021-07-22&sortBy=popularity&apiKey=4852e0653ba546c3b0df900c95726b48

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "4852e0653ba546c3b0df900c95726b48"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadlines(@Query("country")country: String , @Query("page")page: Int) : Call<News>

    // https://newsapi.org/v2/top-headlines?apiKey=4852e0653ba546c3b0df900c95726b48&country=in&page=1
}

object NewsService{

    val newsInstance: NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsInstance = retrofit.create(NewsInterface::class.java)
    }

}

