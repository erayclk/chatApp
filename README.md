# 💬 ChatApp

ChatApp, modern ve kullanıcı dostu arayüze sahip, gerçek zamanlı mesajlaşma özellikleri sunan bir Android sohbet uygulamasıdır. Kotlin diliyle geliştirilmiş ve Firebase altyapısını kullanmaktadır.

## 🚀 Özellikler

- 🔒 Kullanıcı kimlik doğrulama (Firebase Authentication)
- 💬 Gerçek zamanlı mesajlaşma (Firestore)
- 👥 Grup sohbetleri
- 🧑‍💻 Modern ve sezgisel kullanıcı arayüzü
- ⚙️ MVVM mimarisi & Jetpack bileşenleri

---

## 🖼️ Demo

<div align="center">
  <img src="screenshots/Screen_recording_20250524_182453.gif" alt="ChatApp Demo" width="300"/>
</div>

---

## 🛠️ Kullanılan Teknolojiler

| Teknoloji       | Açıklama                        |
|----------------|---------------------------------|
| **Kotlin**      | Android uygulaması için ana dil |
| **Jetpack**     | ViewModel, LiveData, Navigation, DataStore |
| **Firebase**    | Authentication, Firestore, Storage |
| **Material Design 3** | UI/UX için modern tasarım      |
| **Coroutines**  | Asenkron işlemler için          |
| **MVVM**        | Uygulama mimarisi               |

---

## ⚙️ Kurulum

1. Bu repoyu klonlayın:
    ```bash
    git clone https://github.com/erayclk/chatApp
    ```

2. Android Studio ile projeyi açın.

3. Gerekli bağımlılıkların yüklenmesini bekleyin.

4. Firebase yapılandırmasını aşağıdaki gibi tamamlayın:

   - Firebase Console'dan yeni bir proje oluşturun.
   - `google-services.json` dosyasını `app/` klasörüne yerleştirin.
   - Firebase Authentication, Firestore ve Storage özelliklerini etkinleştirin.

5. Uygulamayı bir emulator veya cihazda çalıştırın.

---

## 📋 Gereksinimler

- Android Studio **Arctic Fox** veya üzeri
- Android SDK **21+**
- Kotlin **1.6.0** veya üzeri
- Gradle **7.0+**

---

## 🤝 Katkıda Bulunmak

Katkıda bulunmak istersen memnun olurum!

1. Bu repoyu fork'la  
2. Yeni bir dal oluştur:  
   ```bash
   git checkout -b feature/yeni-ozellik
