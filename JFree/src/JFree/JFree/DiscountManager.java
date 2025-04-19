package JFree;

public class DiscountManager {
    boolean isDiscountsSeason;
    JFree.IDiscountCalculator discountCalculator;

    public DiscountManager(boolean isDiscountsSeason, JFree.IDiscountCalculator discountCalculator) {
        this.isDiscountsSeason = isDiscountsSeason;
        this.discountCalculator = discountCalculator;
    }

    public double calculatePriceAfterDiscount(double price) {
        if (! isDiscountsSeason) {
            return price;
        }

        if (discountCalculator.isTheSpecialWeek())
            return price * .8;

        return price * discountCalculator.getDiscountPercentage();
    }
}
