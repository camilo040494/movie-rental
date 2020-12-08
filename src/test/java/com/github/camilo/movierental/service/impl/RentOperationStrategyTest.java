package com.github.camilo.movierental.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.github.camilo.movierental.builder.RentBuilder;
import com.github.camilo.movierental.model.Rent;
import com.github.camilo.movierental.repository.ChargeRepository;

@SpringBootTest(classes = RentOperationStrategy.class)
class RentOperationStrategyTest {
    
    private static final BigDecimal COST = new BigDecimal(1000);
    
    @Autowired
    RentOperationStrategy rentOperationStrategy;
    
    @MockBean
    ChargeRepository<Rent> rentRepository;
    
    @Test
    void testMustPayMoviePenalty() {
        OffsetDateTime now = OffsetDateTime.now().minusDays(5);
        Rent rent = new RentBuilder().buildEmptyEntity().withFromDate(now).withCost(COST).build();
        BigDecimal calculateTotalCost = rentOperationStrategy.calculateCost(rent);
        assertEquals(new BigDecimal("1050.00"), calculateTotalCost);
    }
    
    @Test
    void testMustNotPayMoviePenalty() {
        OffsetDateTime now = OffsetDateTime.now().minusDays(2);
        Rent rent = new RentBuilder().buildEmptyEntity().withFromDate(now).withCost(COST).build();
        BigDecimal calculateTotalCost = rentOperationStrategy.calculateCost(rent);
        assertEquals(new BigDecimal("1000"), calculateTotalCost);
    }
    
    @Test
    void testMustPay() {
        OffsetDateTime now = OffsetDateTime.now().minusDays(5);
        Optional<BigDecimal> calculatePenalty = rentOperationStrategy.exceededRentingDays(now, COST);
        assertTrue(calculatePenalty.isPresent());
        assertEquals(new BigDecimal("50.00"), calculatePenalty.get());
    }
    
    @Test
    void testMustNotPay() {
        OffsetDateTime now = OffsetDateTime.now().minusDays(2);
        Optional<BigDecimal> calculatePenalty = rentOperationStrategy.exceededRentingDays(now, COST);
        assertFalse(calculatePenalty.isPresent());
    }
    
    @Test
    void testPernaltyOneWeek() {
        OffsetDateTime now = OffsetDateTime.now();
        BigDecimal calculatePenalty = rentOperationStrategy.calculatePenalty(now,
                now.plusDays(4), COST);
        assertEquals(new BigDecimal("50.00"), calculatePenalty);
    }
    
    @Test
    void testPenaltyTwoWeeks() {
        OffsetDateTime now = OffsetDateTime.now();
        BigDecimal calculatePenalty = rentOperationStrategy.calculatePenalty(now,
                now.plusDays(10), COST);
        assertEquals(new BigDecimal("100.00"), calculatePenalty);
    }
    
    @Test
    void testNoPenalty() {
        OffsetDateTime now = OffsetDateTime.now();
        BigDecimal calculatePenalty = rentOperationStrategy.calculatePenalty(now,
                now.plusDays(3), COST);
        assertEquals(new BigDecimal("50.00"), calculatePenalty);
    }
    
    @Test
    void testNoPenalty2() {
        OffsetDateTime now = OffsetDateTime.now();
        BigDecimal calculatePenalty = rentOperationStrategy.calculatePenalty(now,
                now.plusDays(2), COST);
        assertEquals(new BigDecimal("50.00"), calculatePenalty);
    }

}
