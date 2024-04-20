package ra.project_module5_restapi_240130.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Order {
    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "serial_number", columnDefinition = "varchar(100)")
    private String serialNumber;

    @JsonFormat(pattern = "dd/mm/yyy")
    @CreationTimestamp
    private Date order_at;

    @Column(name = "total_price")
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private EOrder status;

    @Column(columnDefinition = "varchar(100)")
    private String note;

    @Column(name = "receive_name", columnDefinition = "varchar(100)")
    private String receiveName;

    @Column(name = "receive_address")
    private String receiveAddress;

    @Column(name = "receive_phone",columnDefinition = "varchar(15)")
    private String receivePhone;

    @CreationTimestamp
    @JsonFormat(pattern = "dd/mm/yyy")
    private Date created_at;

    @JsonFormat(pattern = "dd/mm/yyy")
    @CreationTimestamp
    private Date received_at;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail> orderDetail= new ArrayList<>();
}
