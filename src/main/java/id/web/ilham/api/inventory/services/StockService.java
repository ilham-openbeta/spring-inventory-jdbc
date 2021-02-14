package id.web.ilham.api.inventory.services;

import id.web.ilham.api.inventory.entities.Stock;
import id.web.ilham.api.inventory.entities.StockSummary;
import id.web.ilham.api.inventory.entities.StockWithItem;

import java.util.List;

public interface StockService extends CommonService<Stock, Integer> {

    List<StockSummary> findAllSummaries();

    StockWithItem findByIdWithItem(Integer id);
}
