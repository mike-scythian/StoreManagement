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

    private final int PAGE = 10;

    private Pageable pageable;


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
            Pageable pageable = PageRequest.of(page, PAGE);
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
    public List<SummaryDto> getByStore(Long storeId, Integer page) {

        if (page != null)
            pageable = PageRequest.of(page, PAGE);

        return summaryRepository.findByStore(storeId, pageable)
                .stream()
                .map(SummaryMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<SummaryDto> getByProduct(Long productId, Integer page) {

        if (page != null)
            pageable = PageRequest.of(page, PAGE);

        return summaryRepository.findByProduct(productId, pageable)
                .stream()
                .map(SummaryMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<SummaryDto> getByStoreForPeriod(Long storeId,
                                                LocalDateTime startDate,
                                                LocalDateTime finishDate,
                                                Integer page) {
        if (page != null)
            pageable = PageRequest.of(page, PAGE);

        return summaryRepository.findByStoreAndTimeOperationBetween(storeId, startDate, finishDate, pageable)
                .stream()
                .map(SummaryMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public List<SummaryDto> getByProductForPeriod(Long productId,
                                                  LocalDateTime startDate,
                                                  LocalDateTime finishDate,
                                                  Integer page) {
        if (page != null)
            pageable = PageRequest.of(page, PAGE);

        return summaryRepository.findByProductAndTimeOperationBetween(productId, startDate, finishDate, pageable)
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
                .distinct()
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
