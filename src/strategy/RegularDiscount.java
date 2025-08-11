package strategy;

import interfaces.DiscountStrategy;

public class RegularDiscount implements DiscountStrategy{

    private final double discountRate=0.05;

    @Override
    public double applyDiscount(double baseAmount){
        double discount=baseAmount*discountRate;
        return baseAmount-discount;
    }

}
