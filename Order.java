package resell;

/*
Dubem Nwizubo
April 15, 2022
Period 7
Master Project
Orders
*/

public class Order {

  private int id;
  private String initials;
  private String address;
  private float weight;
  private float price;
  private String delivered;

  public Order() {
   super();
  }

  public Order(int id, String initials, String address, float weight, float price, String delivered) {
  super();
  this.id = id;
  this.initials = initials;
  this.address = address;
  this.weight = weight;
  this.price = price;
  this.delivered = delivered;
  }

  public int getId() {
  return id;
  }

  public void setId(int id) {
  this.id = id;
  }

  public String getInitials() {
  return initials;
  }

  public void setInitials(String initials) {
  this.initials = initials;
  }

  public String getAddress() {
  return address;
  }

  public void setAddress(String address) {
  this.address = address;
  }

  public float getWeight() {
  return weight;
  }

  public void setWeight(float weight) {
  this.weight = weight;
  }

  public float getPrice() {
  return price;
  }

  public void setPrice(float price) {
  this.price = price;
  }

  public String getDelivered() {
  return delivered;
  }
   
  public void setDelivered(String delivered) {
  this.delivered = delivered;
  }  
}
