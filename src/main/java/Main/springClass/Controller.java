package Main.springClass;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;


@RestController
public class Controller {

    ImageOptions imageProcessorController = new ImageOptions();

    @RequestMapping(method = RequestMethod.POST, value = "/image")
    public String addImage(HttpServletRequest requestEntity) throws IOException {
        Integer code = this.imageProcessorController.addImage(requestEntity.getInputStream());
        return "Add Image, ID image: " + code.toString();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/image/{id}")
    public String removeImage(@PathVariable Integer id) throws IOException {
        this.imageProcessorController.removeImage(id);
        return "Successfully removed image.";
    }
    @RequestMapping(method = RequestMethod.GET, value = "/image/{id}/size")
    public String sizeOfImage(@PathVariable Integer id) throws CatchExceptions {
        JSONObject json = this.imageProcessorController.sizeOfImage(id);
        return "JSON size file: " + json;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/image/{id}/histogram")
    public String histogramImage(@PathVariable Integer id) throws IOException {
        try {
            JSONObject json = this.imageProcessorController.histogramImage(id);
            return "JSON histogram file: " + json;
        } catch (IOException e) {
            return "IOException thrown.";
        }
    }
    @RequestMapping(value = "/image/{id}/crop/{x}/{y}/{w}/{h}", method = RequestMethod.GET, produces =
            MediaType.IMAGE_PNG_VALUE)
    public byte[] getGrayImage(@PathVariable Integer id, @PathVariable Integer x,@PathVariable Integer y,
                               @PathVariable Integer w, @PathVariable Integer h) throws Exception {
        return imageProcessorController.CropImage(id,x,y,w,h);
    }
}