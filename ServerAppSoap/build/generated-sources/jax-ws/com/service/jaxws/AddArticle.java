
package com.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "addArticle", namespace = "http://service.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addArticle", namespace = "http://service.com/", propOrder = {
    "nArticle",
    "label",
    "price",
    "minStrock"
})
public class AddArticle {

    @XmlElement(name = "nArticle", namespace = "")
    private String nArticle;
    @XmlElement(name = "label", namespace = "")
    private String label;
    @XmlElement(name = "price", namespace = "")
    private String price;
    @XmlElement(name = "minStrock", namespace = "")
    private String minStrock;

    /**
     * 
     * @return
     *     returns String
     */
    public String getNArticle() {
        return this.nArticle;
    }

    /**
     * 
     * @param nArticle
     *     the value for the nArticle property
     */
    public void setNArticle(String nArticle) {
        this.nArticle = nArticle;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * 
     * @param label
     *     the value for the label property
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getPrice() {
        return this.price;
    }

    /**
     * 
     * @param price
     *     the value for the price property
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getMinStrock() {
        return this.minStrock;
    }

    /**
     * 
     * @param minStrock
     *     the value for the minStrock property
     */
    public void setMinStrock(String minStrock) {
        this.minStrock = minStrock;
    }

}
