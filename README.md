# DexModule Example
Mẫu Xposed module sử dụng [EzXHelper](https://github.com/KyuubiRan/EzXHelper)

## Sử dụng mẫu này để tạo dự án của bạn
Trước khi sử dụng:
- Thay đổi `applicationId` và `namespace` trong `build.gradle.kts`
- Cập nhật tên gói và đường dẫn `xposed_init`
- Chỉnh `rootProject.name` trong `settings.gradle.kts`
- Đồng bộ Gradle
- Điều chỉnh giá trị trong `MainHook.kt` và `arrays.xml`
