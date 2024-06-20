package com.example.newsapp.usecases

import com.example.newsapp.core.domain.repository.NewsRepository
import com.example.newsapp.core.domain.usecases.SearchArticlesUseCase
import com.example.remote.model.Article
import com.example.remote.model.Fields
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class SearchArticlesUseCaseTest : BehaviorSpec({
    val mockRepo = mockk<NewsRepository>()
    val page = 1
    val query = "ARMENIA"
    val useCase = SearchArticlesUseCase(mockRepo)

    Given("We have page number and query get corresponding articles") {
        val fakeArticle = Article(
            id = "asf",
            webPublicationDate = "2023-09-24T14:37:33Z",
            webTitle = "Very interesting title",
            fields = Fields(
                headline = "headline",
                thumbnail = "https://media.guim.co.uk/5956e4a8a83e4fc531927e7bf65d3c9b1099acab/61_0_3378_2027/500.jpg",
                body = "",
            )
        )

        val expectedResult = listOf(fakeArticle.copy(
                webPublicationDate = "24/09/2023"
                )
        )

        coEvery { mockRepo.searchArticles(page, query) } returns listOf(fakeArticle)

        When("Run use case") {
            val result = useCase(page, query)
            Then("We got correct result") {
                result shouldBe expectedResult
            }
        }
    }

    Given("We have page number and query test fail case") {
        val expectedError = Exception("Test")
        coEvery { mockRepo.searchArticles(page, query) } throws expectedError

        When("Run use case") {
            val result = shouldThrow<Exception> { useCase(page, query) }
            Then("We got fail") {
                result shouldBe expectedError
            }
        }
    }
})
