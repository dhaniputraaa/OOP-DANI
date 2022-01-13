import java.sql.SQLException;
import java.sql.Statement;

public class Barang {

    private int kode;
    private String nama = null;
    private String stok = null;
    private String harga = null;

    public Barang(int kode, String nama, String stok, String harga) {
        this.kode = kode;
        this.nama = nama;
        this.stok = stok;
        this.harga = harga;
    }

    public int getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getStok() {
        return stok;
    }

    public String getHarga() {
        return harga;
    }

    public boolean update(String nama, String stok, String harga) {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "update barang SET nama='%s', stok='%s', harga='%s' WHERE kode=%d";
            sql = String.format(sql, nama, stok, harga, kode);
            state.execute(sql);
            db.conn.close();
            return true;
        } catch (SQLException e1) {
            return false;
        }
    }

    public boolean delete() {
        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "delete from tb_barang WHERE kode=%d";
            sql = String.format(sql, kode);
            state.execute(sql);
            db.conn.close();
            return true;
        } catch (SQLException e1) {
            return false;
        }
    }
}