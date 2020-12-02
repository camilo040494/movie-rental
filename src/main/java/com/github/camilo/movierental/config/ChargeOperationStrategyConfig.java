package com.github.camilo.movierental.config;

import java.util.EnumMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.camilo.movierental.model.OperationEnum;
import com.github.camilo.movierental.service.ChargeOperationStrategy;
import com.github.camilo.movierental.service.impl.BuyOperationStrategy;

@Configuration
public class ChargeOperationStrategyConfig {
    
    @Autowired
    private BuyOperationStrategy buyOperationStrategy;
    
    @Bean
    public EnumMap<OperationEnum, ChargeOperationStrategy> chargeOperationStrategy(){
        EnumMap<OperationEnum, ChargeOperationStrategy> map = new EnumMap<OperationEnum, ChargeOperationStrategy>(OperationEnum.class);
        map.put(OperationEnum.BUY, buyOperationStrategy);
        return map;
    }
    
}
