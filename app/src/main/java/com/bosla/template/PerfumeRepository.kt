package com.bosla.template

object PerfumeRepository {
    private val perfumes = mutableListOf(
        Perfume(
            id = 1,
            name = "عود الملكي الفاخر",
            category = "عود وخشب",
            price = 350.0,
            rating = 4.9,
            description = "مزيج ساحر من دهن العود الكلمنتان الطبيعي مع لمسات ناعمة من الورد الطائفي وخشب الصندل الدافئ."
        ),
        Perfume(
            id = 2,
            name = "مسك ختام العبير",
            category = "مسك وعنبر",
            price = 180.0,
            rating = 4.7,
            description = "عبق المسك الأبيض الصافي الممزوج برائحة البودرة الناعمة وأزهار البنفسج لخلق إحساس النظافة والأناقة."
        ),
        Perfume(
            id = 3,
            name = "زهرة الجوري الشرقية",
            category = "زهور وفاكهة",
            price = 220.0,
            rating = 4.5,
            description = "عبير فواح يعكس أنوثة الطائف وجاذبية الزهور النادرة مع نفحات من الفانيليا اللذيذة."
        ),
        Perfume(
            id = 4,
            name = "عنبر الروح الذهبي",
            category = "مسك وعنبر",
            price = 290.0,
            rating = 4.8,
            description = "عطر دافئ وجذاب يتألق بقطرات العنبر الكهرماني المخلوط بالهيل والتوابل الشرقية الفاخرة."
        ),
        Perfume(
            id = 5,
            name = "صندل الهيمالايا",
            category = "عود وخشب",
            price = 310.0,
            rating = 4.6,
            description = "مستخلص خشب الصندل النقي مع لمسة عصرية من أوراق الباتشولي والبرغموت المنعش."
        ),
        Perfume(
            id = 6,
            name = "ياسمين الليل",
            category = "زهور وفاكهة",
            price = 195.0,
            rating = 4.4,
            description = "باقة من أزهار الياسمين الملكي مع لمسات ناعمة من الفراولة والكمثرى المنعشة."
        ),
        Perfume(
            id = 7,
            name = "ليالي الكمبودي",
            category = "عود وخشب",
            price = 420.0,
            rating = 5.0,
            description = "قطرة مركزة من العود الكمبودي المعتق لسنوات طويلة، لعشاق الفخامة والتميز."
        ),
        Perfume(
            id = 8,
            name = "أوركيد الشفق",
            category = "زهور وفاكهة",
            price = 260.0,
            rating = 4.6,
            description = "نفحات غنية من زهرة الأوركيد السوداء والمسك الرومي مع قاعدة دافئة من خشب الأرز."
        )
    )

    fun getAll(): List<Perfume> = perfumes

    fun getFavorites(): List<Perfume> = perfumes.filter { it.isFavorite }

    fun getCart(): List<Perfume> = perfumes.filter { it.isInCart }

    fun toggleFavorite(id: Int) {
        perfumes.find { it.id == id }?.let {
            it.isFavorite = !it.isFavorite
        }
    }

    fun toggleCart(id: Int) {
        perfumes.find { it.id == id }?.let {
            it.isInCart = !it.isInCart
        }
    }

    fun clearCart() {
        perfumes.forEach { it.isInCart = false }
    }

    fun getCartTotal(): Double {
        return perfumes.filter { it.isInCart }.sumOf { it.price }
    }
}
