<<<<<<< HEAD
=======
<<<<<<< HEAD

=======
>>>>>>> upstream/main
>>>>>>> 50cd092 (Made changes)
package com.example.mc_project.domain.repository

import com.example.mc_project.domain.model.Article

interface ArticleRepository {
    suspend fun getRandomArticle(): Article
    suspend fun getArticleByTitle(title: String): Article
<<<<<<< HEAD
}
=======
<<<<<<< HEAD
    suspend fun getMultipleRandomArticles(count: Int): List<Article>
}
=======
}
>>>>>>> upstream/main
>>>>>>> 50cd092 (Made changes)
