package ra.project_module5_restapi_240130.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WishlistDTORequest {
    private long productId;
    private long userId;
}
