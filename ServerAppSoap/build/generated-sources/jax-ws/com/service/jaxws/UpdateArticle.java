
package com.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "updateArticle", namespace = "http://service.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateArticle", namespace = "http://service.com/", propOrder = {
    "nArticle",
    "columName",
    "newValue"
})
public class UpdateArticle {

    @XmlElement(name = "nArticle", namespace = "")
    private String nArticle;
    @XmlElement(name = "columName", namespace = "")
    private String columName;
    @XmlElement(name = "newValue", namespace = "")
    private String newValue;

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
    public String getColumName() {
        return this.columName;
    }

    /**
     * 
     * @param columName
     *     the value for the columName property
     */
    public void setColumName(String columName) {
        this.columName = columName;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getNewValue() {
        return this.newValue;
    }

    /**
     * 
     * @param newValue
     *     the value for the newValue property
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

}
