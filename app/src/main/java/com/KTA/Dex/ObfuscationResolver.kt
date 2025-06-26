package com.KTA.Dex
import android.content.pm.ApplicationInfo
import org.luckypray.dexkit.DexKitBridge

// Data class đơn giản để lưu kết quả
data class ObfuscatedMethod(val className: String, val methodName: String)

object ObfuscationResolver {

    // Map để lưu kết quả, key là tên logic, value là kết quả từ DexKit
    val resolvedMethods = mutableMapOf<String, ObfuscatedMethod>()

    // Biến cờ để đảm bảo chỉ chạy một lần
    private var resolved = false

    fun resolve(appInfo: ApplicationInfo) {
        if (resolved) return
        
        // Load thư viện native của DexKit
        System.loadLibrary("dexkit")

        // Sử dụng DexKitBridge để phân tích APK
        DexKitBridge.create(appInfo.sourceDir).use { bridge ->
            // --- Tìm hàm getImei ---
            // Chiến lược: Tìm phương thức trong TelephonyManager trả về String và không có tham số.
            // Đây là một ví dụ đơn giản, thực tế có thể cần chiến lược phức tạp hơn.
            val getImeiResult = bridge.findMethod {
                searchPackages("android.telephony")
                matcher {
                    className = "android.telephony.TelephonyManager"
                    name = "getImei" // Giả sử chúng ta biết tên không bị làm rối, hoặc dùng các đặc điểm khác
                    returnType = "java.lang.String"
                    paramCount = 0
                }
            }.firstOrNull()
            
            if (getImeiResult != null) {
                resolvedMethods["getImei"] = ObfuscatedMethod(getImeiResult.className, getImeiResult.methodName)
            }
            
            // --- Tìm hàm SystemProperties.get ---
            val getSystemPropResult = bridge.findMethod {
                matcher {
                    className = "android.os.SystemProperties"
                    name = "get"
                    returnType = "java.lang.String"
                    paramTypes("java.lang.String") // Tìm phiên bản 1 tham số
                }
            }.firstOrNull()

            if (getSystemPropResult != null) {
                resolvedMethods["SystemProperties_get"] = ObfuscatedMethod(getSystemPropResult.className, getSystemPropResult.methodName)
            }

            // ... Thêm các chiến lược tìm kiếm khác cho các API bạn muốn hook ...
        }

        resolved = true
    }
}