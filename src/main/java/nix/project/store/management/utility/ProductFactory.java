package nix.project.store.management.utility;

import lombok.Getter;
import lombok.Setter;
import nix.project.store.management.exceptions.DataNotFoundException;
import nix.project.store.management.models.Product;
import nix.project.store.management.repositories.SausageRepository;
import nix.project.store.management.repositories.VegetableRepository;
import nix.project.store.management.services.ProductService;
import nix.project.store.management.services.impl.SausageServiceImpl;
import nix.project.store.management.services.impl.VegetableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    @Autowired
    private SausageRepository sausageRepository;

    @Autowired
    private VegetableRepository vegetableRepository;

    @Getter
    private Product product;

    @Getter
    @Setter
    private ProductService service;

    public void defineProduct(Long productId){

        if(sausageRepository.existsById(productId))
            product = sausageRepository.findById(productId)
                    .orElseThrow(DataNotFoundException::new);

        if(vegetableRepository.existsById(productId))
            product = vegetableRepository.findById(productId)
                    .orElseThrow(DataNotFoundException::new);
    }

    public void defineService(Long productId){

        if(sausageRepository.existsById(productId))
            service = new SausageServiceImpl(sausageRepository);
        if(vegetableRepository.existsById(productId))
            service = new VegetableServiceImpl(vegetableRepository);

    }
}
