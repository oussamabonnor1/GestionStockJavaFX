/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service;

import com.Models.Article;
import com.ToolBox.DbConnection;
import javafx.collections.ObservableList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author oussama
 */
@WebService(serviceName = "connectionService")
public class connectionService {

    int i = 0;

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    //region Articles
    @WebMethod(operationName = "getTableArticle")
    public ObservableList<Article> getTableArticle() {

        DbConnection.createConnection();
        return DbConnection.getTableArticle();
    }

    @WebMethod(operationName = "addArticle")
    public String addArticle(@WebParam(name = "nArticle") String narticle,
            @WebParam(name = "label") String label,
            @WebParam(name = "price") String price,
            @WebParam(name = "minStrock") String minStrock) {
        i++;
        DbConnection.createConnection();
        DbConnection.addArticle(narticle, label, price, minStrock);
        return "article added";
    }

    @WebMethod(operationName = "deleteArticle")
    public String deleteArticle(@WebParam(name = "nArticle") String nArticle) {
        DbConnection.createConnection();
        DbConnection.deleteArticle(nArticle);
        return "article deleted";
    }

    @WebMethod(operationName = "updateArticle")
    public String updateArticle(@WebParam(name = "nArticle") String nArticle,
            @WebParam(name = "columName") String columnName,
            @WebParam(name = "newValue") String newValue) {
        DbConnection.createConnection();
        DbConnection.updateArticle(nArticle, columnName, newValue);
        return "article updated";
    }

    @WebMethod(operationName = "searchArticle")
    public ObservableList<Article> searchArticles(@WebParam(name = "bon") String bon,
            @WebParam(name = "date") String date) {
        DbConnection.createConnection();
        return DbConnection.searchArticles(bon, date);
    }

    @WebMethod(operationName = "getArticle")
    public Article getArticle(@WebParam(name = "nArticle") String nArticle) {
        DbConnection.createConnection();
        return DbConnection.getArticle(nArticle);
    }
    /*
     public static int getIdOfArticle(String label) {
     rs = null;
     try {
     rs = statement.executeQuery("SELECT * FROM " + articleDbName + " WHERE Label = '" + label + "';");
     rs.next();
     return rs.getInt(1);
     } catch (SQLException e) {
     System.out.println("SELECT * FROM " + articleDbName + " WHERE Label = '" + label + "';");
     e.printStackTrace();
     }
     return -1;
     }
     */
    //endregion
}
