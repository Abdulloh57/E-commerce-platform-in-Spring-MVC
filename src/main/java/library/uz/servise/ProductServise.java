package library.uz.servise;

import library.uz.controller.dto.ProductCreateDto;
import library.uz.dao.ProductDatabase;
import library.uz.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductServise {
    private final ProductDatabase repository;
    public void addProduct(ProductCreateDto request, String imagePath) {
        repository.addProduct(request,imagePath);
    }

    public List<Product> getAll() {
       return repository.getAll();
    }
}
