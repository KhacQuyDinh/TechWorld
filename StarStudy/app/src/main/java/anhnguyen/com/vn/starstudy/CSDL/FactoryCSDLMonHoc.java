package anhnguyen.com.vn.starstudy.CSDL;

import android.content.Context;
import android.util.Log;

import anhnguyen.com.vn.starstudy.MaMonHoc;

/**
 * Created by MyPC on 26/02/2017.
 */
public class FactoryCSDLMonHoc {
    private CSDLMonHoc csdlMonHoc;

    public FactoryCSDLMonHoc(Context context) {
        CSDLCache.napCache(context);
    }

    public String[] layDeThi(int requirement, int number_Question) {
        switch (requirement) {
            case MaMonHoc.HOAHOC:
//                System.out.println("Run layCSDLDeThi Mon HoaHoc............................");
                csdlMonHoc = CSDLCache.layCSDL(MaMonHoc.HOAHOC); break;
            case MaMonHoc.TOANHOC:
//                System.out.println("Run layCSDLDeThi Mon ToanHoc............................");
                csdlMonHoc = CSDLCache.layCSDL(MaMonHoc.TOANHOC); break;
            case MaMonHoc.VATLY:
//                System.out.println("Run layCSDLDeThi Mon VatLy............................");
                csdlMonHoc = CSDLCache.layCSDL(MaMonHoc.VATLY); break;
            default:
                Log.e("Factory_CSDL_MonHoc" ,"FactoryCSDLMonHoc: ERROR"); break;
        }

        return csdlMonHoc.layDeThi(number_Question);
    }

    public String[] layDapAn(int requirement, int number_Question) {
        switch (requirement) {
            case MaMonHoc.HOAHOC:
//                System.out.println("Run layCSDLDeThi Mon HoaHoc............................");
                csdlMonHoc = CSDLCache.layCSDL(MaMonHoc.HOAHOC); break;
            case MaMonHoc.TOANHOC:
//                System.out.println("Run layCSDLDeThi Mon ToanHoc............................");
                csdlMonHoc = CSDLCache.layCSDL(MaMonHoc.TOANHOC); break;
            case MaMonHoc.VATLY:
//                System.out.println("Run layCSDLDeThi Mon VatLy............................");
                csdlMonHoc = CSDLCache.layCSDL(MaMonHoc.VATLY); break;
            default:
                Log.e("Factory_CSDL_MonHoc" ,"FactoryCSDLMonHoc: ERROR"); break;
        }
        return csdlMonHoc.layDapAn(number_Question);
    }
}
