package umn.ac.id.project;

public class HistoryModel {
    private String name;
    private int price;
    private String tanggal;

    public HistoryModel(){}

    public HistoryModel(String name, int price, String date){
        this.name = name;
        this.price = price;
        this.tanggal = tanggal;
    }

     public String getName(){
        return name;
     }
     public void setName(){
        this.name = name;
     }
     public int getPrice(){
        return price;
     }
     public void setPrice(){
        this.price = price;
     }

     public String getTanggal(){
        return tanggal;
     }
     public void setTanggal(){
        this.tanggal = tanggal;
     }

}
