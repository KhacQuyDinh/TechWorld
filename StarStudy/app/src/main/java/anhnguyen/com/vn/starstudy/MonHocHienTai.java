package anhnguyen.com.vn.starstudy;

/**
 * Created by MyPC on 02/03/2017.
 */
public class MonHocHienTai {
    public static final String MONTOAN = "Toan Hoc";
    public static final String MONLY = "Vat Ly";
    public static final String MONHOA = "Hoa Hoc";
    public static final String TUKHOA = "Mon Hoc Hien Tai";
    private String tenMonHoc;

    public MonHocHienTai() {
    }

    public MonHocHienTai(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }
}
