package com.tech.twe_tutorside.model;




/*"id": "5",
        "user_id": "74",
        "seller_id": "71",
        "product_id": "80",
        "status": "Accepted",
        "date_time": "2020-10-09 04:26:23"*/

public class CurrentRequestModal {




    private String reciever_id;
    private String seller_id;

    private String product_id;

    private String status;

    private String date_time;


    public CurrentRequestModal(String reciever_id, String seller_id, String product_id, String status, String date_time) {
        this.reciever_id = reciever_id;
        this.seller_id = seller_id;
        this.product_id = product_id;
        this.status = status;
        this.date_time = date_time;
    }


    public String getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(String reciever_id) {
        this.reciever_id = reciever_id;
    }




    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
