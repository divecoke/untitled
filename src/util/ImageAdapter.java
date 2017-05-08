package util;

import javafx.scene.image.Image;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Minde on 5/8/2017.
 */
public class ImageAdapter extends XmlAdapter<String, Image> {
    @Override
    public Image unmarshal(String v) throws Exception {
        return null;
    }

    @Override
    public String marshal(Image v) throws Exception {
        return null;
    }
}
