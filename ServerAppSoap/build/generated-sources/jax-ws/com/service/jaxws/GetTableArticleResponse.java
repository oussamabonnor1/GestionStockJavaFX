
package com.service.jaxws;

import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.Models.Article;

@XmlRootElement(name = "getTableArticleResponse", namespace = "http://service.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTableArticleResponse", namespace = "http://service.com/")
public class GetTableArticleResponse {

    @XmlElement(name = "return", namespace = "")
    private ObservableList<Article> _return;

    /**
     * 
     * @return
     *     returns ObservableList<Article>
     */
    public ObservableList<Article> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(ObservableList<Article> _return) {
        this._return = _return;
    }

}
