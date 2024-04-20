package ra.project_module5_restapi_240130.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import ra.project_module5_restapi_240130.model.EOrder;
import ra.project_module5_restapi_240130.model.OrderDetail;
import ra.project_module5_restapi_240130.model.User;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDTOResponse {
    private long id;
    private Date order_at;
    private double totalPrice;
    private String status;
    private String note;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private Date created_at;
    private Date received_at;
    @JsonIgnoreProperties({"order"})
    private List<OrderDetail> orderDetailList;
}
