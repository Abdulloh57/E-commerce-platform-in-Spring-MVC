package library.uz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "product_id")
    private int productId;
    private int quantity;

    public OrderItem(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
