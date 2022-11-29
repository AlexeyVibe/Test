package av.TestQuest1.controllers;

import av.TestQuest1.services.PicturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class C {

    private final PicturesService picturesService;

    @Autowired
    public C(PicturesService picturesService) {
        this.picturesService = picturesService;
    }

    @GetMapping("/static")
    public String show(Model model){
        model.addAttribute("picture",picturesService.findById(19));

        return "hello";
    }

}
