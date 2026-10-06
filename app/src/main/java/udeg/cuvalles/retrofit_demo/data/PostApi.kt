package udeg.cuvalles.retrofit_demo.data

import retrofit2.http.GET

interface PostApi {

    @GET("posts")
    suspend fun getPosts(): List<Post>
}