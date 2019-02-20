package Main.springClass;

import org.json.JSONException;
import org.json.JSONObject;
import javax.imageio.ImageIO;
import javax.servlet.ServletInputStream;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageOptions {
    private Map<Integer,BufferedImage> imageMap = new HashMap<>();
    private int CounterImage = -1;

    int addImage(ServletInputStream addImage)throws IOException{
        CounterImage++;

        InputStream StreamImage = new BufferedInputStream(addImage);
        BufferedImage image = ImageIO.read(StreamImage);

        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        bufferedImage.createGraphics().drawImage(image, 0, 0, null);

        imageMap.put(CounterImage, bufferedImage);

        return CounterImage;
    }
    void removeImage(int id) throws CatchExceptions{
        if(!imageMap.containsKey(id)|| imageMap.get(id)==null)
            throw new CatchExceptions("Wrong ID");

        imageMap.remove(id);
    }

    JSONObject sizeOfImage(int id)throws CatchExceptions{
        if(!imageMap.containsKey(id)|| imageMap.get(id)==null)
            throw new CatchExceptions("Wrong ID");

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("Height", imageMap.get(id).getHeight());
            jsonObject.put("Width", imageMap.get(id).getWidth());
        }catch(JSONException e){}
        return jsonObject;
    }

    JSONObject histogramImage(int id)throws IOException{
        if(!imageMap.containsKey(id)|| imageMap.get(id)==null)
            throw new CatchExceptions("Wrong ID");

        JSONObject jsonObject = new JSONObject();


        int black = 0, white = 0;
        try{
            for(int i = 0; i< imageMap.get(id).getWidth(); i++){
                for(int j = 0; j< imageMap.get(id).getHeight(); j++){

                    if(imageMap.get(id).getRGB(i,j)==-1){
                        white++;
                    } else black++;
                }
            }
            jsonObject.put("Black Color", black);
            jsonObject.put("White Color", white);

        }catch(JSONException e){ }
        return jsonObject;
    }

    byte[] CropImage(int id, int x, int y, int w, int h)throws IOException{

        if(!imageMap.containsKey(id)|| imageMap.get(id)==null)
            throw new CatchExceptions("Wrong ID");

        if(x+w> imageMap.get(id).getWidth()||y+h> imageMap.get(id).getHeight())
            throw new IOException("Too much area");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write( imageMap.get(id).getSubimage(x,y,w,h), "jpg", byteArrayOutputStream );
        byteArrayOutputStream.flush();

        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return imageInByte;
    }
}