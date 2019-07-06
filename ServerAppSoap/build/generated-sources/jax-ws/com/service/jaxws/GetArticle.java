
package com.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getArticle", namespace = "http://service.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getArticle", namespace = "http://service.com/")
public class GetArticle {

    @XmlElement(name = "nArticle", namespace = "")
    private String nArticle;

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

}
