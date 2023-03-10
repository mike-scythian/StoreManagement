package nix.project.store.management.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nix.project.store.management.entities.Summary;
import nix.project.store.management.dto.SummaryDto;
import nix.project.store.management.dto.mapper.SummaryMapper;
import nix.project.store.management.dto.ProductBySaleDto;
import nix.project.store.management.repositories.SummaryRepository;
import nix.project.store.management.services.ProductService;
import nix.project.store.management.services.SummaryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final SummaryRepository summaryRepository;

    private final ProductService productService;


    @Override
    @Transactional
    public void createReport(Long productId, Double saleSum, Long storeId) {

        Summary report = Summary.builder()
                .product(productId)
                .payment(saleSum)
                .timeOperation(LocalDateTime.now())
                .store(storeId)
                .build();

        summaryRepository.save(report);
    }

    @Override
    public List<SummaryDto> getReports(Integer page) {
        if (page != null) {
            Pageable pageable = PageRequest.of(page, 10);
            return summaryRepository.findAll(pageable)
                    .stream()
                    .map(SummaryMapper.MAPPER::toMap)
                    .toList();
        } else
            return summaryRepository.findAll()
                    .stream()
                    .map(SummaryMapper.MAPPER::toMap)
                    .toList();
    }

    @Override
    public List<SummaryDto> getByStore(Long storeId) {

            return summaryRepository.findByStore(storeId)
                .stream()
                .map(SummaryMapper.MAPPER::toMap)
                .toList();


    }

    @Override
    public List<SummaryDto> getByProduct(Long productId) {
        return summaryRepository.findByProduct(productId)
                .stream()
                .map(SummaryMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<SummaryDto> getByStoreForPeriod(Long storeId, LocalDateTime startDate, LocalDateTime finishDate) {
        return summaryRepository.findByStoreAndTimeOperationBetween(storeId, startDate, finishDate)
                .stream()
                .map(SummaryMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<SummaryDto> getByProductForPeriod(Long productId, LocalDateTime startDate, LocalDateTime finishDate) {
        return summaryRepository.findByProductAndTimeOperationBetween(productId, startDate, finishDate)
                .stream()
                .map(SummaryMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<ProductBySaleDto> getTopTenProducts() {

        return summaryRepository.findAll()
                .stream()
                .map(prod -> new ProductBySaleDto(
                        prod.getProduct(),
                        productService.getProduct(prod.getProduct()).getName(),
                        sumByProduct(prod.getProduct())))
                .sorted((v1, v2) -> -1 * (v1.summaryIncome().compareTo(v2.summaryIncome())))
                .limit(10)
                .toList();
    }

    private Double sumByProduct(Long productId) {

        return summaryRepository.findAll()
                .stream()
                .filter(summary -> Objects.equals(summary.getProduct(), productId))
                .mapToDouble(Summary::getPayment)
                .sum();
    }


}
