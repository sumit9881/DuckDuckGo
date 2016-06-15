
package com.example.sadhika.duckduckgo.pojos;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Icon {

    @SerializedName("URL")
    @Expose
    private String URL;
    @SerializedName("Height")
    @Expose
    private String Height;
    @SerializedName("Width")
    @Expose
    private String Width;

    /**
     * 
     * @return
     *     The URL
     */
    public String getURL() {
        return URL;
    }

    /**
     * 
     * @param URL
     *     The URL
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * 
     * @return
     *     The Height
     */
    public String getHeight() {
        return Height;
    }

    /**
     * 
     * @param Height
     *     The Height
     */
    public void setHeight(String Height) {
        this.Height = Height;
    }

    /**
     * 
     * @return
     *     The Width
     */
    public String getWidth() {
        return Width;
    }

    /**
     * 
     * @param Width
     *     The Width
     */
    public void setWidth(String Width) {
        this.Width = Width;
    }

}
