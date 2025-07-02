package com.example.dexmodule

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.example.dexmodule.ObfuscationResolver
import com.example.dexmodule.hook.applyAllHooks
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        // Khởi tạo EzXHelper để sử dụng các hàm tiện ích
        EzXHelperInit.initHandleLoadPackage(lpparam)

        // 1. Chạy DexKit để tìm các phương thức cần hook
        // Quá trình này chỉ cần chạy một lần cho mỗi ứng dụng
        ObfuscationResolver.resolve(lpparam.appInfo)

        // 2. Áp dụng các hook dựa trên kết quả đã tìm được
        applyAllHooks(lpparam.classLoader)
    }
}