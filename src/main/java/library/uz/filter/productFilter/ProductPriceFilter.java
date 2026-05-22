package library.uz.filter.productFilter;

import library.uz.controller.dto.ProductFilterdto;
import library.uz.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductPriceFilter implements ProductFilter{
    @Override
    public boolean support(ProductFilterdto filterdto) {
        return filterdto.getPrice() !=0.0;
    }

    @Override
    public List<Product> filter(List<Product> products, ProductFilterdto filterdto) {
        return products.stream()
                .filter(product -> product.getPrice() == filterdto.getPrice())
                .collect(Collectors.toList());
    }
}
