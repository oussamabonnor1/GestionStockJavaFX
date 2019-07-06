
package com.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "searchArticle", namespace = "http://service.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "searchArticle", namespace = "http://service.com/", propOrder = {
    "bon",
    "date"
})
public class SearchArticles {

    @XmlElement(name = "bon", namespace = "")
    private String bon;
    @XmlElement(name = "date", namespace = "")
    private String date;

    /**
     * 
     * @return
     *     returns String
     */
    public String getBon() {
        return this.bon;
    }

    /**
     * 
     * @param bon
     *     the value for the bon property
     */
    public void setBon(String bon) {
        this.bon = bon;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getDate() {
        return this.date;
    }

    /**
     * 
     * @param date
     *     the value for the date property
     */
    public void setDate(String date) {
        this.date = date;
    }

}
