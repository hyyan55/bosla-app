# Bosla App — بوصلة التطبيقات

تطبيق أندرويد من أكاديمية بوصلة يحوّل وصفاً مكتوباً (برومبت) إلى تطبيق أندرويد أصلي وملف APK.

## هذا المستودع
هذا هو **قالب التطبيق الثابت** الذي تُبنى عليه كل التطبيقات المولّدة:
- Gradle 8.14.3 + AGP 8.13.0 + Kotlin 2.0.21 + Java 17
- `minSdk 24` ، `compileSdk/targetSdk 35` ، الحزمة `com.bosla.template`
- مكتبات فقط: core-ktx ، appcompat ، material ، constraintlayout

## كيف يعمل (برومبت ← APK)
1. المستخدم يكتب وصف التطبيق في تطبيق **بوصلة التطبيقات**.
2. يُرسل الوصف + `GENERATION_PROMPT.md` إلى Gemini فيولّد كود التطبيق.
3. تُستبدل الملفات المولّدة (`MainActivity.kt` ، `activity_main.xml` ، `strings.xml`) في هذا القالب.
4. يُشغَّل الـ workflow `Build APK` من تبويب Actions فيبني `app-debug.apk` ويرفعه كـ Artifact.
5. عند فشل البناء → اتبع `FIX_LOOP.md` (سجل الخطأ إلى Gemini، استبدل، أعد البناء — 3 محاولات كحد أقصى).

## الملفات
- `GENERATION_PROMPT.md` — برومبت التوليد لـ Gemini
- `FIX_LOOP.md` — حلقة الإصلاح عند فشل البناء
- `TEST_PROMPT_CALCULATOR.md` — برومبت الاختبار: آلة حاسبة بسيطة
- `.github/workflows/build-apk.yml` — بناء APK على GitHub Actions

## البناء يدوياً
من تبويب **Actions** ← اختر **Build APK** ← **Run workflow**.
