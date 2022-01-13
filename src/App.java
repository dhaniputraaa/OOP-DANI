import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
// import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
// import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    TableView<Barang> tableView = new TableView<Barang>();
    
    public static void main(String[]args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UAS OOP");
        TableColumn<Barang, Integer> columnKode = new TableColumn<>("KODE");
        columnKode.setCellValueFactory(new PropertyValueFactory<>("kode"));

        TableColumn<Barang, String> columnNama = new TableColumn<>("NAMA");
        columnNama.setCellValueFactory(new PropertyValueFactory<>("nama"));

        TableColumn<Barang, String> columnStok = new TableColumn<>("STOK");
        columnStok.setCellValueFactory(new PropertyValueFactory<>("stok"));

        TableColumn<Barang, String> columnHarga = new TableColumn<>("HARGA");
        columnHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));

        tableView.getColumns().add(columnKode);
        tableView.getColumns().add(columnNama);
        tableView.getColumns().add(columnStok);
        tableView.getColumns().add(columnHarga);

        ToolBar toolBar = new ToolBar();

        Button buttonAdd = new Button("ADD");
        toolBar.getItems().add(buttonAdd);
        buttonAdd.setOnAction(e -> add());

        Button buttonDelete = new Button("DELETE");
        toolBar.getItems().add(buttonDelete);
        buttonDelete.setOnAction(e -> delete());

        Button buttonEdit = new Button("EDIT");
        toolBar.getItems().add(buttonEdit);
        buttonEdit.setOnAction(e -> edit());

        Button buttonRefresh = new Button("REFRESH");
        toolBar.getItems().add(buttonRefresh);
        buttonRefresh.setOnAction(e -> re());

        VBox vbox = new VBox(tableView, toolBar);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
        load();
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from tb_barang");
            tableView.getItems().clear();
            // tampilkan hasil query
            while (rs.next()) {
                tableView.getItems().add(new Barang(rs.getInt("kode"), rs.getString("nama"), rs.getString("stok"), rs.getString("harga")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        Stage addStage = new Stage();
        Button save = new Button("SIMPAN");

        addStage.setTitle("MENAMBAHKAN DATA BARANG");

        TextField namaField = new TextField();
        TextField stokField = new TextField();
        TextField hargaField = new TextField();
        Label labelNama = new Label("NAMA");
        Label labelStok = new Label("STOK");
        Label labelHarga = new Label("HARGA");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelStok, stokField);
        VBox hbox3 = new VBox(5, labelHarga, hargaField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into tb_barang SET nama='%s', stok='%s', harga='%s'";
                sql = String.format(sql, namaField.getText(), stokField.getText(), hargaField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void delete() {
        Stage addStage = new Stage();
        Button save = new Button("DELETE");

        addStage.setTitle("Delete Data");

        TextField kodeField = new TextField();
        Label labelKode = new Label("NAMA YANG AKAN DIHAPUS");

        VBox hbox1 = new VBox(5, labelKode, kodeField);
        VBox vbox = new VBox(20, hbox1, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from tb_barang WHERE nama='%s'";
                sql = String.format(sql, kodeField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
                System.out.println();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void edit() {
        Stage addStage = new Stage();
        Button save = new Button("SIMPAN");

        addStage.setTitle("EDIT NAMA");

        TextField namaField = new TextField();
        TextField stokField = new TextField();
        TextField hargaField = new TextField();
        Label labelNama = new Label("NAMA");
        Label labelStok = new Label("STOK");
        Label labelHarga = new Label("HARGA");

        VBox hbox1 = new VBox(5, labelNama, namaField);
        VBox hbox2 = new VBox(5, labelStok, stokField);
        VBox hbox3 = new VBox(5, labelHarga, hargaField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "UPDATE tb_barang SET stok ='%s', harga ='%s' WHERE nama='%s'";
                sql = String.format(sql, stokField.getText(), hargaField.getText(), namaField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void load() {
        Statement stmt;
        tableView.getItems().clear();
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from tb_barang");
            while (rs.next()) {
                tableView.getItems().addAll(new Barang(rs.getInt("kode"), rs.getString("nama"), rs.getString("stok"), rs.getString("harga")));
            }
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void re() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE tb_barang DROP kode";
            sql = String.format(sql);
            state.execute(sql);
            re2();

        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }

    public void re2() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "ALTER TABLE tb_barang ADD kode INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST";
            sql = String.format(sql);
            state.execute(sql);
            load();
        } catch (SQLException e1) {
            e1.printStackTrace();
            System.out.println();
        }
    }
}