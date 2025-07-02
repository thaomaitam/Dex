package com.example.dexmodule.hook
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.example.dexmodule.ObfuscationResolver

fun applyAllHooks(classLoader: ClassLoader) {
    // Lấy kết quả từ bộ phân giải
    val resolved = ObfuscationResolver.resolvedMethods

    // Hook getImei
    val getImeiInfo = resolved["getImei"]
    if (getImeiInfo != null) {
        findMethod(getImeiInfo.className, classLoader) {
            name == getImeiInfo.methodName
        }.hookBefore {
            // Đặt giá trị IMEI giả mạo của bạn ở đây
            it.result = "123456789012345" 
        }
    }
    
    // Hook SystemProperties.get
    val getSystemPropInfo = resolved["SystemProperties_get"]
    if (getSystemPropInfo != null) {
        findMethod(getSystemPropInfo.className, classLoader) {
            name == getSystemPropInfo.methodName
        }.hookBefore { param ->
            val key = param.args[0] as String
            // Logic làm giả dựa trên key
            when (key) {
                "ro.build.fingerprint" -> param.result = "my/fake/fingerprint"
                "ro.product.model" -> param.result = "Faker Phone 9000"
                // ...
            }
        }
    }
}