package anhnguyen.com.vn.starstudy.CSDL;

import android.content.Context;

import java.util.Hashtable;

import anhnguyen.com.vn.starstudy.MaMonHoc;

/**
 * Created by MyPC on 01/03/2017.
 */
public class CSDLCache {
    private static Hashtable<Integer, CSDLMonHoc> mapCSDL = new Hashtable<>();

    public static CSDLMonHoc layCSDL(int maMonHoc) {
//        System.out.println("Run layCSDL!");
        return mapCSDL.get(maMonHoc);
    }

    public static void napCache(Context context) {
//        System.out.println("Run NapCache!");
        CSDLToanHoc.executeCSDLToanHoc csdlToanHoc = new CSDLToanHoc.executeCSDLToanHoc(context);
        csdlToanHoc.setId(MaMonHoc.TOANHOC);
        mapCSDL.put(csdlToanHoc.getId(), csdlToanHoc);

        CSDLHoaHoc.executeCSDLHoaHoc csdlHoaHoc = new CSDLHoaHoc.executeCSDLHoaHoc(context);
        csdlHoaHoc.setId(MaMonHoc.HOAHOC);
        mapCSDL.put(csdlHoaHoc.getId(), csdlHoaHoc);

        CSDLVatLy.executeCSDLVatLy csdlVatLy = new CSDLVatLy.executeCSDLVatLy(context);
        csdlVatLy.setId(MaMonHoc.VATLY);
        mapCSDL.put(csdlVatLy.getId(), csdlVatLy);
    }
}
