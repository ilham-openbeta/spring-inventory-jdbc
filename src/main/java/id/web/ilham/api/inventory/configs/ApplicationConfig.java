package id.web.ilham.api.inventory.configs;

import id.web.ilham.api.inventory.entities.StockSummary;
import id.web.ilham.api.inventory.models.stock.StockSummaryResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy((MatchingStrategies.STRICT));
        modelMapper.typeMap(StockSummary.class, StockSummaryResponse.class).addMappings(mapper -> {
                    mapper.map(src -> src.getItem().getId(), StockSummaryResponse::setItemId);
                    mapper.map(src -> src.getItem().getName(), StockSummaryResponse::setItemName);
                    mapper.map(src -> src.getItem().getPrice(), StockSummaryResponse::setItemPrice);
                    mapper.map(src -> src.getItem().getImageUrl(), StockSummaryResponse::setImageUrl);
                }
        );
        return modelMapper;
    }
}
