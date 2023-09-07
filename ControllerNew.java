package resell;

/*
Dubem Nwizubo
April 15, 2022
Period 7
Master Project
Controller
*/

import java.net.URL;

public class ControllerNew implements Initializable {

  //FXML SCENEBUILDER ITEMS
  @FXML
  private MenuItem exitGUI;
  @FXML
  private MenuItem completed;
  @FXML
  private MenuItem about;
  @SuppressWarnings("rawtypes")
  @FXML
  private TableView tvOrdersA;
  @FXML
  private TableView<Order> tvOrders;
  @FXML
  private TableColumn<Order, Integer> colId;
  @FXML
  private TableColumn<Order, String> colInitials;
  @FXML
  private TableColumn<Order, String> colAddress;
  @FXML
  private TableColumn<Order, Float> colWeight;
  @FXML
  private TableColumn<Order, Float> colPrice;
  @FXML
  private TableColumn<Order, String> colDelivered;
  @FXML
  private TextField tfId;
  @FXML
  private TextField tfInitials;
  @FXML
  private TextField tfAddress;
  @FXML
  private TextField tfWeight;
  @FXML
  private TextField tfPrice;
  @FXML
  private Button btnNew;
  @FXML
  private Button btnUpdate;
  @FXML
  private Button btnDelete;
  @FXML
  private TextField tfDelivered;
  @FXML
  private Label announce;
  @FXML
  private Label insert;
  @FXML
  private Label update;
  @FXML
  private Label delete;
  @FXML
  private AnchorPane myAnchorPane;
  @FXML
  public int currentRecord;
  
  Connection conn = null;
  
  //ADD, UPDATE, DELETE BUTTONS
  @FXML
  public void btnNewC(ActionEvent event) {
    if (btnNew.getText().equals("NEW ORDER")) {
      tfId.setText("");
      tfInitials.setText("");
      tfAddress.setText("");
      tfWeight.setText("");
      tfPrice.setText("");
      tfDelivered.setText("");
      btnNew.setText("CONFIRM ORDER");
    } else {
      btnNew.setText("NEW ORDER");
      Order s = pullOrder();
      newOrder(s);
      showOrders();
    }
  }
  
  @FXML
  public void btnUpdateC(ActionEvent event) {
    Order s = pullOrder();
    updateOrder(s);
    showOrders();
  }
  
  @FXML
  public void btnDeleteC(ActionEvent event) {
    Order s = pullOrder();
    deleteOrder(s);
    showOrders();
  }
  
  //INITIAZLIZING - DISPLAYING DATA IN TABLEVIEW WHEN PROGRAM RUNS
  public void initialize(URL url, ResourceBundle rb) {
    // TODO
    showOrders();
  }
  
  //CONNECTING TO SQLITE DATABASE
  public Connection connect() {
    try {
      String url = "jdbc:sqlite:C:/sqlite/db/ordersdb.sqlite";
      conn = DriverManager.getConnection(url);
      System.out.println("Connection to SQLite has been established.");
      return conn;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
  
  //CREATING OBSERVABLE LIST - ALLOWS DATA TO BE SHOWN IN TABLEVIEW
  public ObservableList<Order> getOrderList(){
    ObservableList<Order> orderList = FXCollections.observableArrayList();
    Connection conn = connect();
    String query = "SELECT * FROM orders";
    Statement st;
    ResultSet rs;
    //CONNECTS TABLE TO DATABASE
    try {
      st = conn.createStatement();
      rs = st.executeQuery(query);
      Order orders;
      while(rs.next(orders = new Order(rs.getInt("id"), rs.getString("initials"), rs.getString("address"),
      rs.getFloat("weight"),rs.getFloat("price"), rs.getString("delivered"));
      orderList.add(orders);
    } catch(Exception ex) {
    ex.printStackTrace();
    }
    return orderList;
  }
  
  //DISPLAYS DATA IN COLUMNS
  public void showOrders(){
    ObservableList<Order> list = getOrderList();
    colId.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
    colInitials.setCellValueFactory(new PropertyValueFactory<Order, String>("initials"));
    colAddress.setCellValueFactory(new PropertyValueFactory<Order, String>("address"));
    colWeight.setCellValueFactory(new PropertyValueFactory<Order, Float>("weight"));
    colPrice.setCellValueFactory(new PropertyValueFactory<Order, Float>("price"));
    colDelivered.setCellValueFactory(new PropertyValueFactory<Order, String>("delivered"));
    tvOrders.setItems(list);
  }
  
  //METHOD THAT ALLOWS USER TO INSERT NEW ORDER
  private void newOrder(Order s){
    String qry = "INSERT INTO orders (id,initials,address,weight,price,delivered) VALUES (?,?,?,?,?,?)";
    try {
      PreparedStatement pstmt = conn.prepareStatement(qry);
      pstmt.setInt(1, s.getId());
      pstmt.setString(2, s.getInitials());
      pstmt.setString(3, s.getAddress());
      pstmt.setFloat(4, s.getWeight());
      pstmt.setFloat(5, s.getPrice());
      pstmt.setString(6, s.getDelivered());
      pstmt.executeUpdate();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    showOrders();
  }
  
  //METHOD THAT ALLOWS USER TO UPDATE ORDER
  private void updateOrder(Order s){
    String qry = "UPDATE orders SET initials =?, address =?, weight =?, price =?, delivered =? WHERE id =?";
    try {
      PreparedStatement pstmt = conn.prepareStatement(qry);
      pstmt.setInt(1, s.getId());
      pstmt.setString(2, s.getInitials());
      pstmt.setString(3, s.getAddress());
      pstmt.setFloat(4, s.getWeight());
      pstmt.setFloat(5, s.getPrice());
      pstmt.setString(6, s.getDelivered());
      pstmt.executeUpdate();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    showOrders();
  }
  
  //METHOD THAT ALLOWS USER TO DELETE RECORD
  private void deleteOrder(Order s){
    try {
      String qry = "DELETE FROM orders WHERE id =? ";
      PreparedStatement pstmt = conn.prepareStatement(qry);
      pstmt.setInt(1, s.getId()); pstmt.executeUpdate();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    showOrders();
  }
  
  //PULLS ORDER FROM TEXT FIELDS AS DATA TO BE ENTERED INTO DATABASE
  public Order pullOrder() {
    Order s = new Order();
    s.setId(Integer.parseInt(tfId.getText()));
    s.setInitials(tfInitials.getText());
    s.setAddress(tfAddress.getText());
    s.setWeight(Float.parseFloat(tfWeight.getText()));
    s.setPrice(Float.parseFloat(tfPrice.getText()));
    s.setDelivered(tfDelivered.getText());
    return s;
  }
  
  //ALLOWS USER TO EXIT GUI
  @FXML
  public void exitGUIC(ActionEvent event) {
    Platform.exit();
    System.exit(0);
  }
  
  //DISPLAYS ABOUT MESSAGE
  @FXML
  public void aboutC(ActionEvent event) {
    new Alert(Alert.AlertType.INFORMATION, "Order Tracking Program - Code by Dubem Nwizubo").showAndWait();
  }
  
  //ALLOWS TABLE VIEW ROWS TO BE SELECTED
  @FXML
  private void handleMouseAction(MouseEvent event) {
    Order order = tvOrders.getSelectionModel().getSelectedItem();
    tfId.setText("" +order.getId());
    tfInitials.setText(order.getInitials());
    tfAddress.setText(order.getAddress());
    tfWeight.setText("" +order.getWeight());
    tfPrice.setText("" +order.getPrice());
    tfDelivered.setText(order.getDelivered());
  }
}
