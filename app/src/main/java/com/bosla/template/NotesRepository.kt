package com.bosla.template

import java.util.UUID

object NotesRepository {
    private val items = mutableListOf<NoteItem>()

    init {
        // إضافة 8 عناصر افتراضية واقعية
        items.add(
            NoteItem(
                id = UUID.randomUUID().toString(),
                title = "إعداد التقرير الأسبوعي للمشروع",
                description = "مراجعة كافة الإنجازات والمهام المكتملة وتجهيز العرض التقديمي للاجتماع القادم.",
                category = "العمل",
                priority = true,
                date = "اليوم، 10:00 ص",
                isTask = true,
                isCompleted = false
            )
        )
        items.add(
            NoteItem(
                id = UUID.randomUUID().toString(),
                title = "تسليم واجب برمجة أندرويد",
                description = "إنهاء تصميم واجهات المستخدم والمميزات المطلوبة في تطبيق الملاحظات.",
                category = "دراسة",
                priority = true,
                date = "غداً، 11:59 م",
                isTask = true,
                isCompleted = false
            )
        )
        items.add(
            NoteItem(
                id = UUID.randomUUID().toString(),
                title = "أفكار لتصميم واجهة التطبيق الجديد",
                description = "استخدام ألوان هادئة، زوايا دائرية، وبطاقات عريضة مع تدرجات لونية مميزة للـ Hero.",
                category = "العمل",
                priority = false,
                date = "أمس",
                isTask = false,
                isCompleted = false
            )
        )
        items.add(
            NoteItem(
                id = UUID.randomUUID().toString(),
                title = "قراءة فصل من كتاب التطوير الذاتي",
                description = "تركيز على تقنيات إدارة الوقت والتخلص من المماطلة اليومية.",
                category = "شخصي",
                priority = false,
                date = "اليوم، 08:00 م",
                isTask = true,
                isCompleted = true
            )
        )
        items.add(
            NoteItem(
                id = UUID.randomUUID().toString(),
                title = "قائمة المشتريات الأسبوعية للبيت",
                description = "فواكه طازجة، خضروات، مستلزمات المطبخ، وقهوة عربية.",
                category = "شخصي",
                priority = false,
                date = "أمس",
                isTask = false,
                isCompleted = false
            )
        )
        items.add(
            NoteItem(
                id = UUID.randomUUID().toString(),
                title = "مراجعة ميزانية المصاريف الشهرية",
                description = "تحليل المصاريف وتحديد نقاط التوفير للشهور القادمة.",
                category = "شخصي",
                priority = true,
                date = "اليوم، 04:00 م",
                isTask = true,
                isCompleted = false
            )
        )
        items.add(
            NoteItem(
                id = UUID.randomUUID().toString(),
                title = "تلخيص المحاضرة الثالثة في الذكاء الاصطناعي",
                description = "كتابة أهم المفاهيم حول شبكات الأعصاب الاصطناعية وتطبيقاتها.",
                category = "دراسة",
                priority = false,
                date = "منذ يومين",
                isTask = false,
                isCompleted = false
            )
        )
        items.add(
            NoteItem(
                id = UUID.randomUUID().toString(),
                title = "التخطيط لرحلة نهاية الأسبوع",
                description = "حجز الأماكن وتجهيز الأغراض المطلوبة وتحديد جدول الأنشطة.",
                category = "شخصي",
                priority = false,
                date = "اليوم، 06:00 م",
                isTask = false,
                isCompleted = false
            )
        )
    }

    fun getAllItems(): List<NoteItem> = items

    fun getTasks(): List<NoteItem> = items.filter { it.isTask }

    fun getNotes(): List<NoteItem> = items.filter { !it.isTask }

    fun getUrgentItems(): List<NoteItem> = items.filter { it.priority }

    fun getItemById(id: String): NoteItem? = items.find { it.id == id }

    fun addItem(item: NoteItem) {
        items.add(0, item)
    }

    fun deleteItem(id: String) {
        items.removeAll { it.id == id }
    }

    fun toggleComplete(id: String) {
        items.find { it.id == id }?.let {
            it.isCompleted = !it.isCompleted
        }
    }
}
