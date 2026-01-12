package ru.bank.app

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Page
import com.microsoft.playwright.Playwright
import org.testng.Assert.assertEquals
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

class PlaywrightUiTest {
    private lateinit var playwright: Playwright
    private lateinit var browser: Browser
    private lateinit var page: Page

    @BeforeClass
    fun setUp() {
        playwright = Playwright.create()
        browser = playwright.chromium().launch(BrowserType.LaunchOptions().setHeadless(true))
        page = browser.newPage()
    }

    @AfterClass
    fun tearDown() {
        page.close()
        browser.close()
        playwright.close()
    }

    /**
     * Тест-кейс: проверка формы входа и позитивного сценария.
     * Вводим номер телефона и ПИН, ожидаем приветственный текст.
     */
    @Test
    fun shouldShowWelcomeAfterLogin() {
        val resource = requireNotNull(javaClass.classLoader.getResource("ui/index.html"))
        page.navigate(resource.toURI().toString())

        page.locator("[data-testid='login-phone']").fill("+7 999 123-45-67")
        page.locator("[data-testid='login-pin']").fill("1234")
        page.locator("[data-testid='login-submit']").click()

        val hint = page.locator("[data-testid='login-hint']").textContent()
        assertEquals(hint, "Добро пожаловать, +7 999 123-45-67!")
    }
}
