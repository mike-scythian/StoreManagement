package nix.project.store.management.services.impl;

import lombok.AllArgsConstructor;
import nix.project.store.management.dto.ProductDto;
import nix.project.store.management.dto.SausageDto;
import nix.project.store.management.dto.mapper.SausageMapper;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.exceptions.ValueExistsAlreadyException;
import nix.project.store.management.models.Product;
import nix.project.store.management.models.Sausage;
import nix.project.store.management.repositories.SausageRepository;
import nix.project.store.management.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SausageServiceImpl implements ProductService {

    private final SausageRepository sausageRepo;

    @Override
    public long addProduct(ProductDto productDto) {

        Sausage sausage = new Sausage(productDto.getName(), productDto.getPrice(), productDto.getParam());

        if (isUnique(sausage))
            return sausageRepo.save(sausage).getId();

        else
            throw new ValueExistsAlreadyException();
    }

    @Override
    public ProductDto update(Long productId, ProductDto productDto) {

        Sausage sausage = sausageRepo.findById(productId)
                .orElseThrow(DataNotFoundException::new);

        int count = 0;

        if (productDto.getName() != null) {
            sausage.setName(productDto.getName());
            ++count;
        }
        if (productDto.getPrice() != null) {
            sausage.setPrice(productDto.getPrice());
            ++count;
        }
        if (productDto.getParam() != null) {
            sausage.setType(productDto.getParam());
            ++count;
        }
        if (count > 0)
            sausageRepo.save(sausage);

        return SausageMapper.MAPPER.toMap(sausage);
    }

    @Override
    public void delete(Long productId) {
        if (sausageRepo.existsById(productId))
            sausageRepo.deleteById(productId);
        else
            throw new DataNotFoundException();
    }

    @Override
    public List<SausageDto> getProducts() {
        return sausageRepo.findAll().stream()
                .map(SausageMapper.MAPPER::toMap)
                .toList();
    }

    @Override
    public ProductDto getProduct(Long productId) {

        Sausage sausage = sausageRepo.findById(productId)
                .orElseThrow(DataNotFoundException::new);

        return SausageMapper.MAPPER.toMap(sausage);
    }

    @Override
    public <T extends Product> boolean isUnique(T product) {

        Sausage sausage;

        if (product.getClass().equals(Sausage.class))
            sausage = (Sausage) product;
        else
            return false;

        long length = sausageRepo.findAll().stream()
                .filter(s -> !s.equals(sausage))
                .count();

        return length == sausageRepo.count();
    }

}
