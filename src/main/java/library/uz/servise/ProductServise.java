package library.uz.servise;

import library.uz.controller.dto.ProductCreateDto;
import library.uz.model.Product;
import library.uz.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductServise {
    private final ProductRepository repository;
    public void addProduct(ProductCreateDto request, String imagePath) {
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setImagePath(imagePath);
        repository.save(product);
    }

    public List<Product> getAll() {
       return repository.findAll();
    }
}
