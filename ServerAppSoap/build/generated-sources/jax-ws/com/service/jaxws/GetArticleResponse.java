
package com.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.Models.Article;

@XmlRootElement(name = "getArticleResponse", namespace = "http://service.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getArticleResponse", namespace = "http://service.com/")
public class GetArticleResponse {

    @XmlElement(name = "return", namespace = "")
    private Article _return;

    /**
     * 
     * @return
     *     returns Article
     */
    public Article getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(Article _return) {
        this._return = _return;
    }

}
