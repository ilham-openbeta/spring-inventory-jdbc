package id.web.ilham.api.inventory.services.impl;

import id.web.ilham.api.inventory.entities.Stock;
import id.web.ilham.api.inventory.entities.StockSummary;
import id.web.ilham.api.inventory.entities.StockWithItem;
import id.web.ilham.api.inventory.repositories.StockRepository;
import id.web.ilham.api.inventory.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl extends CommonServiceImpl<Stock, Integer> implements StockService {

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        super(stockRepository);
    }

    @Override
    public List<StockSummary> findAllSummaries() {
        return ((StockRepository) repository).findAllSummaries();
    }

    @Override
    public StockWithItem findByIdWithItem(Integer id) {
        return ((StockRepository) repository).findByIdWithItem(id);
    }
}
