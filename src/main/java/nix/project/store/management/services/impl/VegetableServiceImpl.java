package nix.project.store.management.services.impl;

import lombok.AllArgsConstructor;
import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.dto.VegetableDto;
import nix.project.store.management.dto.mapper.VegetableMapper;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.exceptions.ValueExistsAlreadyException;
import nix.project.store.management.models.Product;
import nix.project.store.management.models.Vegetable;
import nix.project.store.management.repositories.VegetableRepository;
import nix.project.store.management.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VegetableServiceImpl implements ProductService {

    private final VegetableRepository vegetableRepository;

    @Override
    public long addProduct(ProductDto productDto) {

        Vegetable vegetable = new Vegetable(productDto.getName(), productDto.getPrice(), productDto.getParam());

        if (isUnique(vegetable))
            return vegetableRepository.save(vegetable).getId();

        else
            throw new ValueExistsAlreadyException();
    }

    @Override
    public ProductDto update(Long productId, ProductDto productDto) {

        Vegetable vegetable = vegetableRepository.findById(productId)
                .orElseThrow(DataNotFoundException::new);

        int count = 0;

        if (productDto.getName() != null) {
            vegetable.setName(productDto.getName());
            ++count;
        }
        if (productDto.getPrice() != null) {
            vegetable.setPrice(productDto.getPrice());
            ++count;
        }
        if (productDto.getParam() != null) {
            vegetable.setVariety(productDto.getParam());
            ++count;
        }
        if (count > 0)
            vegetableRepository.save(vegetable);

        return VegetableMapper.MAPPER.toMap(vegetable);
    }

    @Override
    public void delete(Long productId) {
        if (vegetableRepository.existsById(productId))
            vegetableRepository.deleteById(productId);
        else
            throw new DataNotFoundException();
    }

    @Override
    public List<VegetableDto> getProducts() {
        return vegetableRepository.findAll().stream()
                .map(VegetableMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public ProductDto getProduct(Long productId) {

        Vegetable vegetable = vegetableRepository.findById(productId)
                .orElseThrow(DataNotFoundException::new);

        return VegetableMapper.MAPPER.toMap(vegetable);
    }

    @Override
    public <T extends Product> boolean isUnique(T product) {

        Vegetable vegetable;

        if (product.getClass().equals(Vegetable.class))
            vegetable = (Vegetable) product;
        else
            return false;

        long length = vegetableRepository.findAll().stream()
                .filter(s -> !s.equals(vegetable))
                .count();

        return length == vegetableRepository.count();
    }

}
