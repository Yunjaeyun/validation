package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Savepoint;

@Data
public class Item {

//    @NotNull(groups = {UpdateCheck.class})  // 수정요구사항 추가
    private Long id;

//    @NotBlank(message = "공백 X", groups = {UpdateCheck.class, SaveCheck.class})
    private String itemName;

//    @NotNull(groups = {UpdateCheck.class, SaveCheck.class})
//    @Range(min = 1000, max = 1000000, groups = {UpdateCheck.class, SaveCheck.class})
    private Integer price;

//    @NotNull(groups = {UpdateCheck.class, SaveCheck.class})
//    @Max(value = 9999, groups = {Savepoint.class})
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
