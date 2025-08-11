package strategy;

import interfaces.DiscountStrategy;

public class PremiumDiscount implements DiscountStrategy{

    private final double discountRate=0.15;

    @Override
    public double applyDiscount(double baseAmount){
        double discount=baseAmount*discountRate;
        return baseAmount-discount;
    }

}
