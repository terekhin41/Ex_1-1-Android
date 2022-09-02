package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy
import ru.netology.nmedia.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: PostEntity)

    @Query("UPDATE posts SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)

    @Query("""
        UPDATE posts SET
        likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likeById(id: Long)

    @Query("DELETE FROM posts WHERE id = :id")
    fun removeById(id: Long)

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getPostById(id: Long) : Post?

    @Query("UPDATE posts SET share = share + 1 WHERE id = :id")
    fun share(id: Long)
}