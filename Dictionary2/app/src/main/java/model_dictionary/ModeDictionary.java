package model_dictionary;

import android.content.Context;

/**
 * Created by Dell on 11/11/2016.
 * Su dung mot dang khoi tao duy nhat chua hai  loai tu dien EV va VE va chua tu dien dang thuc thi
 * Tu dien dang thuc thi la su dung da hinh (co the dung toan tu instanceof de xac dinh chinh xac loai tu dien).
 */
public class ModeDictionary {
    //Su dung Singleton de chi khoi tao mot lan duy nhat.
    private static EVDictionary EVDic;
    private static VEDictionary VEDic;
    //Mac dinh ban dau la tu dien EV.
    private static GeneralDictionary execDic;

    //Cac phuong thuc khong la static de buoc phai khoi tao.
    public ModeDictionary(Context context) {
        if (EVDic == null) {
            EVDic = new EVDictionary(context);
        }
        if (VEDic == null) {
            VEDic = new VEDictionary(context);
        }
        //Khoi tao ban dau la EVDic.
        setExecDic(EVDic);
    }

    public static GeneralDictionary getExecDic() {
        return execDic;
    }

    public static EVDictionary getEVDic() {
        return EVDic;
    }

    public static VEDictionary getVEDic() {
        return VEDic;
    }

    public static void turnOnEVDictionary() {
        execDic = EVDic;
    }

    public static void turnOnVEDictionary() {
        execDic = VEDic;
    }

    private static void setExecDic(GeneralDictionary execDic) {
        ModeDictionary.execDic = execDic;
    }
}
