# Т-Лояльность
Documentation:
Frontend — Android приложение на Kotlin. Используется Jetpack Compose, Koin, Retrofit, Kotlinx.Serialization, Zxing (для отображения QR-кодов), Guava, Camera, BarcodeScanning (для сканирования QR кодов), Accompanist Permissions.

В приложении реализованы:
- Экраны регистрации и авторизации для партнёра и клиента
- Экран со списком всех партнёров для клиента
- Экран с списком всех акций партнёра для клиента
- Экран с подробной информацией об акции, прогрессом выполнения акции и QR-кодом для использования акции
- Экран с списком своих акций для партнёра
- Экран с подробной информацией об акции для партнёра
- Экран создания акции для партнёра
- Экран сканирования QR-кода для партнёра (после сканирования он становится экраном подробной информации об акции с кнопкой применения акции)