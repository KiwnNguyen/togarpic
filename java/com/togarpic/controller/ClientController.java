package com.togarpic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.togarpic.model.Recipe;
import com.togarpic.repository.RecipeRepository;

@Controller
@RequestMapping("/")
public class ClientController implements WebMvcConfigurer {
	@Autowired
	private RecipeRepository rec1;
	//------------ error 404 -----------
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error404").setViewName("error404");
    }
	//------------ error 404 -----------
	
	@RequestMapping("/home")
	public String Home() {
		return"client/home";
		
	}
	@RequestMapping("/shopgrid")
	public String Shopgrid() {
		return"client/shopgrid";
		
	}
	@RequestMapping("/shopdetails")
	public String shopdetails() {
		return"client/shopdetails";
		
	}
	@RequestMapping("/shoppingcart")
	public String shopingcart() {
		return"client/shoppingcart";
		
	}
	@RequestMapping("/checkout")
	public String checkout() {
		return"client/checkout";
		
	}
	@RequestMapping("/blogdetails")
	public String blogdetails() {
		return"client/blogdetails";
		
	}
	@RequestMapping(value = "/recipe", method = RequestMethod.GET)
	public String showRecipeList(Model model) {	  
		Iterable<Recipe> rec = rec1.findAll();
		model.addAttribute("listRecipe", rec);
		return "client/recipe";
	}
	@GetMapping(value = "/recipe_details/{rec_id}")
	 public String showRecipeDetails(@PathVariable("rec_id") Long recId, Model model) {
        // Xác định đường dẫn trả về dựa trên recId
        String redirectPath;
        if (recId == 1) {
            redirectPath = "redirect:/recipe_details/pizza";
        } else if (recId == 2) {
            redirectPath = "redirect:/recipe_details/beefsteak";
        }else if (recId == 3) {
            redirectPath = "redirect:/recipe_details/banhchung";
        }else if (recId == 4) {
            redirectPath = "redirect:/recipe_details/donut";
        }else if (recId == 5) {
            redirectPath = "redirect:/recipe_details/curryrice";
        }else if (recId == 6) {
            redirectPath = "redirect:/recipe_details/ramen";
        }else if (recId == 9) {
            redirectPath = "redirect:/recipe_details/tofu";
        }else if (recId == 10) {
            redirectPath = "redirect:/recipe_details/bokho";
        } else {
            // Nếu recId không phù hợp, chuyển hướng về trang chủ hoặc hiển thị thông báo lỗi
            return "redirect:/recipe";
        }

        // Trả về đường dẫn chuyển hướng
        return redirectPath;
    }
	@RequestMapping("/recipe_details/pizza")
	public String pizza() {
		return"client//recipe_details/pizza";
		
	}
	@RequestMapping("/recipe_details/beefsteak")
	public String beefsteak() {
		return"client//recipe_details/beefsteak";
		
	}
	@RequestMapping("/recipe_details/banhchung")
	public String banhchung() {
		return"client//recipe_details/banhchung";
		
	}
	@RequestMapping("/recipe_details/donut")
	public String donut() {
		return"client//recipe_details/donut";
		
	}
	@RequestMapping("/recipe_details/curryrice")
	public String curryrice() {
		return"client//recipe_details/curryrice";
		
	}
	@RequestMapping("/recipe_details/ramen")
	public String ramen() {
		return"client//recipe_details/ramen";
		
	}
	@RequestMapping("/recipe_details/tofu")
	public String tofu() {
		return"client//recipe_details/tofu";
		
	}
	@RequestMapping("/recipe_details/bokho")
	public String bokho() {
		return"client//recipe_details/bokho";
		
	}

//    @GetMapping("/recipe_details/pizza")
//    public String showPizzaRecipe(Model model) {
//        // Lấy thông tin công thức pizza từ repository
//        Recipe pizzaRecipe = RecipeRepository.findByName("Pizza");
//
//        // Gửi công thức pizza cho view
//        model.addAttribute("recipe", pizzaRecipe);
//
//        // Trả về view để hiển thị nội dung chi tiết công thức pizza
//        return "recipe_details";
//    }
//
//    @GetMapping("/recipe_details/beefsteak")
//    public String showBeefSteakRecipe(Model model) {
//        // Lấy thông tin công thức beefsteak từ repository
//        Recipe beefSteakRecipe = RecipeRepository.findRecipeByName("Beefsteak");
//
//        // Gửi công thức beefsteak cho view
//        model.addAttribute("recipe", beefSteakRecipe);
//
//        // Trả về view để hiển thị nội dung chi tiết công thức beefsteak
//        return "recipe_details";
//    }
//}
	@RequestMapping("/contact")
	public String contact() {
		return"client/contact";
		
	}
	
}