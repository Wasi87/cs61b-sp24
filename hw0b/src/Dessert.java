public class Dessert {
    public int flavor;
    public int price;

    public Dessert(int flavor, int price){
        this.flavor = flavor;
        this.price = price;
        numDesserts++;
    }
    public static int numDesserts;

    public void printDessert(){
        System.out.printf("%d %d %d", this.flavor, this.price, numDesserts);
    }

    public static void main(String[] args) {
        System.out.println("I love dessert!");
    }
}
