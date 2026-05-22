package library.uz.filter.productFilter;

import library.uz.controller.dto.ProductFilterdto;
import library.uz.model.Product;

import java.util.List;

public interface ProductFilter {
    boolean support(ProductFilterdto filterdto);

    public List<Product> filter(List<Product> products, ProductFilterdto filterdto);
}
