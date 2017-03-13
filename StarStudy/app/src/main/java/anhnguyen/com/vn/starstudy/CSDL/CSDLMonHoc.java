package anhnguyen.com.vn.starstudy.CSDL;

/**
 * Created by MyPC on 26/02/2017.
 */
public abstract class CSDLMonHoc {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract String[] layDeThi(int number_Question);
    public abstract String[] layDapAn(int number_Question);
}
