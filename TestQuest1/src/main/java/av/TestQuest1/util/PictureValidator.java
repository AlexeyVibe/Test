package av.TestQuest1.util;

import av.TestQuest1.models.Picture;
import av.TestQuest1.services.PicturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PictureValidator implements Validator {

    private PicturesService picturesService;

    @Autowired
    public PictureValidator(PicturesService picturesService) {
        this.picturesService = picturesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Picture.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Picture picture = (Picture) target;

        if (picturesService.findByUrl(picture.getUrl()) != null)
            errors.rejectValue("url", "", "This picture is uploaded already");
    }
}
