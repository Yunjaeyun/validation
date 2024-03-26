package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v3/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v3/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v3/addForm";
    }

//    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //특정 필드 예외가 아닌 전체 예외
        if(item.getPrice()!=null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값=" + resultPrice);
            }
        }

        //검증에 실패하면 다시 입력폼으로
        if(bindingResult.hasErrors()){
            log.info("errors={},",bindingResult);
            return "validation/v3/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItem2(@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        //특정 필드 예외가 아닌 전체 예외
        if(item.getPrice()!=null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값=" + resultPrice);
            }
        }

        //검증에 실패하면 다시 입력폼으로
        if(bindingResult.hasErrors()){
            log.info("errors={},",bindingResult);
            return "validation/v3/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v3/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/editForm";
    }

//    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@Validated @ModelAttribute Item item,BindingResult bindingResult) {

        //특정 필드 예외가 아닌 전체 예외
        if(item.getPrice()!=null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값=" + resultPrice);
            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }


    @PostMapping("/{itemId}/edit")
    public String edit2(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute Item item, BindingResult bindingResult) {

        //특정 필드 예외가 아닌 전체 예외
        if(item.getPrice()!=null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.reject("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값=" + resultPrice);
            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "validation/v3/editForm";
        }

        itemRepository.update(itemId, item);
        return "redirect:/validation/v3/items/{itemId}";
    }

}

