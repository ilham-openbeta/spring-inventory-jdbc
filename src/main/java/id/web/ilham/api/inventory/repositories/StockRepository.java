package id.web.ilham.api.inventory.repositories;

import id.web.ilham.api.inventory.entities.Stock;
import id.web.ilham.api.inventory.entities.StockSummary;
import id.web.ilham.api.inventory.entities.StockWithItem;

import java.util.List;

public interface StockRepository extends CommonRepository<Stock, Integer>{
    List<StockSummary> findAllSummaries();

    StockWithItem findByIdWithItem(Integer id);
}
