package nix.project.store.management.controllers;

import nix.project.store.management.dto.SummaryDto;
import nix.project.store.management.dto.ProductBySaleDto;
import nix.project.store.management.services.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequestMapping("/summary")
public class SummaryController {

    @Autowired
    SummaryService summaryService;

    @GetMapping
    public ResponseEntity<List<SummaryDto>> statistic(@RequestParam(required = false)Integer page){

        return new ResponseEntity<>(summaryService.getReports(page), HttpStatus.CREATED);
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<List<SummaryDto>> getReportsByStore(@PathVariable long id){

        return new ResponseEntity<>(summaryService.getByStore(id), HttpStatus.CREATED);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<List<SummaryDto>> getReportsByProduct(@PathVariable long id){

        return new ResponseEntity<>(summaryService.getByProduct(id), HttpStatus.CREATED);
    }

    @GetMapping("/stores/by-period/{id}")
    public ResponseEntity<List<SummaryDto>> getReportsFromStoreByPeriod(@PathVariable long id,
                                                                        @RequestParam LocalDate start,
                                                                        @RequestParam LocalDate end){

        return new ResponseEntity<>(summaryService.getByStoreForPeriod(
                id,
                start.atTime(LocalTime.parse("00:00:00")),
                end.atTime(LocalTime.parse("23:59:59"))), HttpStatus.CREATED);
    }

    @GetMapping("/products/by-period/{id}")
    public ResponseEntity<List<SummaryDto>> getReportsByProductByPeriod(@PathVariable long id,
                                                                        @RequestParam LocalDate start,
                                                                        @RequestParam LocalDate end){

        return new ResponseEntity<>(summaryService.getByProductForPeriod(
                id,
                start.atTime(LocalTime.parse("00:00:00")),
                end.atTime(LocalTime.parse("23:59:59"))), HttpStatus.CREATED);
    }

    @GetMapping("/products/top")
    public ResponseEntity<List<ProductBySaleDto>> getTopTenProducts(){

        return new ResponseEntity<>(summaryService.getTopTenProducts(), HttpStatus.CREATED);
    }

}
