package com.procompras.engine;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class RappiSpider {

    private final SeleniumBrowser browser;

    private record PathMarket(String name, String url) {
    }

    private static final List<PathMarket> PATH_MARKETS = List.of(new PathMarket("Tottus", "https://www.rappi.com.pe/tiendas/95560-tottus-super-nc"), new PathMarket("Metro", "https://www.rappi.com.pe/tiendas/11214-metro"));

    private static final List<String> AVAILABLE_LINKS = List.of("/frutas-y-verduras/verduras", "/carne-y-pollo/pollo-y-aves");

    public RappiSpider(SeleniumBrowser browser) {
        this.browser = browser;
    }


    public List<ProductScrapping> execute() throws InterruptedException {
        List<ProductScrapping> products = new ArrayList<>();

        for (PathMarket pathMarket : PATH_MARKETS) {
            String marketName = pathMarket.name();
            for (String categoryPath : AVAILABLE_LINKS) {
                String url = pathMarket.url() + categoryPath;
                this.browser.open(url);

                String categoryName = this.browser.getDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-qa='title-page']"))).getText();

                this.centerProductView();

                List<WebElement> productsElements = this.getProductsElements();
                for (WebElement currentElement : productsElements) {
                    this.scrollToProduct(currentElement);
                    ProductScrapping productScrap = this.extract(currentElement, categoryName, marketName);
                    System.out.println(productScrap);
                    products.add(productScrap);
                }
            }
        }
        return products;

    }

    public void centerProductView() throws InterruptedException {
        Thread.sleep(5000);
        while (true) {
            this.removeLoginModal();

            try {
                WebElement btnElement = this.browser.getDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(., \"Ver más\")]")));

                this.browser.executeScript("""
                        arguments[0].scrollIntoView({
                            block: 'center'
                        });
                        """, btnElement);

                Thread.sleep(500);

                this.removeLoginModal();

                this.browser.executeScript("arguments[0].click();", btnElement);
                Thread.sleep(Duration.ofSeconds(2));
            } catch (TimeoutException _t) {
                break;
            }
        }
    }


    public ProductScrapping extract(WebElement productElement, String categoryName, String market) {

        BiFunction<WebElement, String, String> _getText = (webElement, cssSelector) -> {
            List<WebElement> elements = webElement.findElements(By.cssSelector(cssSelector));
            if (elements.isEmpty()) {
                return null;
            }
            String text = elements.getFirst().getAttribute("textContent");
            return text != null ? text.trim() : null;
        };

        Function<WebElement, String> _getImage = (webElement) -> {
            List<WebElement> elements = webElement.findElements(By.cssSelector("img[data-testid='image']"));
            for (WebElement element : elements) {
                String src = element.getAttribute("src");
                if (src != null && src.startsWith("https://images.rappi.pe/")) {
                    return src;
                }
            }
            return null;
        };

        String price = _getText.apply(productElement, "[data-qa='product-price']");
        String realPrice = _getText.apply(productElement, "[data-qa='product-real-price']");
        String name = _getText.apply(productElement, "[data-qa='product-name']");
        String pum = _getText.apply(productElement, "[data-qa='product-pum']");
        String description = _getText.apply(productElement, "[data-qa='product-description']");
        String discount = _getText.apply(productElement, "[data-qa='product-discount']");
        String imageUrl = _getImage.apply(productElement);

        Boolean haveDiscount = realPrice != null;
        return new ProductScrapping(name, description, price, realPrice, imageUrl, categoryName, market, haveDiscount, discount, pum);
    }


    public List<WebElement> getProductsElements() throws InterruptedException {
        this.removeLoginModal();

        this.browser.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(Duration.ofMillis(3000));

        WebElement ulProducts = this.browser.getDriverWait().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.gDfWRO")));

        return ulProducts.findElements(By.cssSelector("div[data-qa^='product-item-']"));

    }

    public void scrollToProduct(WebElement productElement) throws InterruptedException {
        this.browser.executeScript("""
                arguments[0].scrollIntoView({
                    block: 'center'
                });
                """, productElement);

        Thread.sleep(Duration.ofMillis(200L));
    }

    public void removeLoginModal() {
        String modalXpath = "//section[@role='dialog'][.//header[contains(text(), 'Confirma tu identidad')]]";
        var driver = this.browser.getDriver();

        List<WebElement> modals = driver.findElements(By.xpath(modalXpath));
        if (modals.isEmpty()) {
            return;
        }

        this.browser.executeScript("""
                const modal = arguments[0];
                const portal = modal.closest('.chakra-portal');
                
                if (portal) {
                    portal.remove();
                } else {
                    modal.remove();
                }
                
                document.body.style.setProperty('overflow', 'auto', 'important');
                document.body.style.setProperty('overscroll-behavior', 'auto', 'important');
                document.body.style.setProperty('position', 'static', 'important');
                document.body.style.setProperty('margin-right', '0px', 'important');
                """, modals.getFirst());
    }
}
