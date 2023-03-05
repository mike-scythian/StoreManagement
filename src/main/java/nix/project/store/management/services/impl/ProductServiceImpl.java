package nix.project.store.management.services.impl;

import lombok.AllArgsConstructor;
import nix.project.store.management.dto.ProductCreateDto;
import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.dto.mapper.ProductCreateMapper;
import nix.project.store.management.dto.mapper.ProductMapper;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.exceptions.ValueExistsAlreadyException;
import nix.project.store.management.models.Product;
import nix.project.store.management.repositories.ProductRepository;
import nix.project.store.management.services.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final ProductMapper mapper = ProductMapper.MAPPER;


    @Override
    public ProductCreateDto createProduct(ProductCreateDto productCreateDto) {

        Product product = ProductCreateMapper.MAPPER.toEntityMap(productCreateDto);

        if (productRepository.existsByNameAndType(product.getName(), product.getType()))

            throw new ValueExistsAlreadyException();
        else {
            Product savedProduct = productRepository.save(product);
            return ProductCreateMapper.MAPPER.toMap(savedProduct);
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

        if (productRepository.existsById(productId))
            productRepository.deleteById(productId);
        else
            throw new DataNotFoundException();
    }

    @Override
    public List<ProductDto> getProducts(Pageable pageable) {

        if(pageable != null)
            return productRepository.findAll(pageable)
                .stream()
                .map(mapper::toMap)
                .toList();
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

}
