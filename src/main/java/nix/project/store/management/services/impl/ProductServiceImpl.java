package nix.project.store.management.services.impl;

import lombok.AllArgsConstructor;
import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.dto.mapper.ProductMapper;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.exceptions.ValueExistsAlreadyException;
import nix.project.store.management.entities.Product;
import nix.project.store.management.repositories.ProductRepository;
import nix.project.store.management.repositories.StoreRepository;
import nix.project.store.management.repositories.StoreStockRepository;
import nix.project.store.management.services.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreStockRepository storeStockRepository;
    private static final ProductMapper mapper = ProductMapper.MAPPER;


    @Override
    public ProductDto createProduct(ProductDto productDto) {

        Product product = mapper.toEntityMap(productDto);

        if (productRepository.existsByNameAndType(product.getName(), product.getType()))

            throw new ValueExistsAlreadyException();
        else {
            Product savedProduct = productRepository.save(product);
            return mapper.toMap(savedProduct);
        }
    }

    @Override
    public ProductDto update(Long productId, ProductDto productDto) {

        Product product = productRepository.findById(productId)
                .orElseThrow(DataNotFoundException::new);

        int count = 0;

        if (productDto.getName() != null) {
            product.setName(productDto.getName());
            ++count;
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
            ++count;
        }
        if (productDto.getUnits() != null) {
            product.setUnits(productDto.getUnits());
            ++count;
        }
        if (productDto.getType() != null) {
            product.setType(productDto.getType());
            ++count;
        }
        if (count > 0)
            productRepository.save(product);

        return mapper.toMap(product);
    }

    @Override
    public void delete(Long productId) {

        if (productRepository.existsById(productId) && !checkStocks(productId))
            productRepository.deleteById(productId);
        else
            throw new DataNotFoundException();
    }

    @Override
    public List<ProductDto> getProducts(Integer page) {

        if(page != null) {
            Pageable pageable = PageRequest.of(page, 10);
            return productRepository.findAll(pageable)
                    .stream()
                    .map(mapper::toMap)
                    .toList();
        }
        else
            return productRepository.findAll()
                    .stream()
                    .map(mapper::toMap)
                    .toList();
    }

    @Override
    public ProductDto getProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(DataNotFoundException::new);

        return mapper.toMap(product);
    }

    private boolean checkStocks(Long id){
        return storeStockRepository.existsByIdProductId(id);
    }

}
