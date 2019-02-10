package Models;

public class Article {
    int nArticle, minStock;
    String label;
    float price;

    public Article(int nArticle, String label, float price, int minStock) {
        this.nArticle = nArticle;
        this.minStock = minStock;
        this.label = label;
        this.price = price;
    }

    public int getnArticle() {
        return nArticle;
    }

    public void setnArticle(int nArticle) {
        this.nArticle = nArticle;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
