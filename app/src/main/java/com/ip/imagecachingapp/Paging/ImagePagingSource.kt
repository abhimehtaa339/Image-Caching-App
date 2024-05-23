import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.ip.imagecachingapp.Models.ImageResponesItem
import com.ip.imagecachingapp.Retrofit.ApiService
import com.ip.imagecachingapp.cache.DiskCache
import com.ip.imagecachingapp.cache.MemoryCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

class ImagePagingSource(
    private val apiService: ApiService,
    private val context: Context
) : PagingSource<Int, ImageResponesItem>() {

    override fun getRefreshKey(state: PagingState<Int, ImageResponesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageResponesItem> {
        val position = params.key ?: 1

        val cachedData = MemoryCache.getImages(position)
        if (cachedData != null) {
            return LoadResult.Page(
                data = cachedData,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == 30) null else position + 1
            )
        }

        val diskCacheData = loadFromDiskCache(position)
        if (diskCacheData != null) {

            MemoryCache.cacheImages(position, diskCacheData)
            return LoadResult.Page(
                data = diskCacheData,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == 30) null else position + 1
            )
        }


        return try {
            val response = apiService.getData(position)
            cacheImages(position, response)
            LoadResult.Page(
                data = response,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == 30) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun loadFromDiskCache(position: Int): List<ImageResponesItem>? {
        return withContext(Dispatchers.IO) {
            val file = File(context.cacheDir, "image_cache_$position")
            if (file.exists()) {
                try {
                    FileInputStream(file).use { fileInputStream ->

                        val byteArray = fileInputStream.readBytes()

                        val jsonString = String(byteArray)

                        return@use Gson().fromJson(jsonString, Array<ImageResponesItem>::class.java).toList()
                    }
                } catch (e: JsonSyntaxException) {

                    e.printStackTrace()
                    return@withContext null
                } catch (e: Exception) {

                    e.printStackTrace()
                    return@withContext null
                }
            }
            return@withContext null
        }
    }

    private fun cacheImages(position: Int, data: List<ImageResponesItem>) {

        MemoryCache.cacheImages(position, data)
        DiskCache.saveImages(context, position, data)
    }
}
