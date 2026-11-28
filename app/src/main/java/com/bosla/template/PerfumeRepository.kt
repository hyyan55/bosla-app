package com.bosla.template

data class Perfume(
    val id: Int,
    val name: String,
    val brand: String,
    val price: Double,
    val category: String,
    val description: String,
    val notes: String,
    var isFavorite: Boolean = false,
    var inCart: Boolean = false,
    var cartQuantity: Int = 0
)

object PerfumeRepository {
    val perfumesList = mutableListOf(
        Perfume(
            id = 1,
            name = "عود الملكي الخاص",
            brand = "الماجد للعود",
            price = 450.0,
            category = "شرقي فاخر",
            description = "عطر شرقي ساحر بعبق العود والورد الطائفي يمنحك حضوراً ملوكياً لا يُنسى في المناسبات السعيدة.",
            notes = "الدهن العتيق، العود الهندي، الورد الطائفي، العنبر الذهبي"
        ),
        Perfume(
            id = 2,
            name = "روح اللافندر النقية",
            brand = "أطياب المرشود",
            price = 280.0,
            category = "زهري ناعم",
            description = "مزيج منعش ومهدئ من زهور اللافندر الفرنسي مع لمسات رقيقة من الفانيلا البيضاء.",
            notes = "خزامى بروفانس، فانيليا بيضاء، زهر البرتقال، مسك ناعم"
        ),
        Perfume(
            id = 3,
            name = "مسك الختام والحرير",
            brand = "عبد الصمد القرشي",
            price = 320.0,
            category = "شرقي فاخر",
            description = "عبير المسك الأبيض الصافي الممزوج بلمسات حريرية تمنحك إحساساً دائماً بالنظافة والأنوقة.",
            notes = "مسك غزال نقي، زنبق الوادي، أخشاب الأرز، بودرة ناعمة"
        ),
        Perfume(
            id = 4,
            name = "سحر الحمضيات والانتعاش",
            brand = "باريس لافيرن",
            price = 210.0,
            category = "انتعاش ونظافة",
            description = "جرعة انطلاق حيوية من الجريب فروت والليمون الصقلي لتجديد طاقتك في صباح كل يوم.",
            notes = "جريب فروت وردي، برغموت صقلي، نعناع بستاني، نجيل الهند"
        ),
        Perfume(
            id = 5,
            name = "عنبر الليل الخشبي",
            brand = "نخبة العود",
            price = 510.0,
            category = "خشبي دافئ",
            description = "عطر دافئ وأنيق يجمع بين فخامة العنبر الأصيل وأخشاب الصندل لليالي الشتاء العاطرة.",
            notes = "عنبر دافئ، خشب الصندل، التوباك، التوابل الشرقية"
        ),
        Perfume(
            id = 6,
            name = "ياسمين دمشق الفاخر",
            brand = "قصر العطور",
            price = 290.0,
            category = "زهري ناعم",
            description = "نفحات عطرية زكية تأخذك في جولة بين بساتين الياسمين الشامي النضر في الصباح الباكر.",
            notes = "ياسمين شامي، فلة بيضاء، خشب الخيزران، المسك الوردي"
        ),
        Perfume(
            id = 7,
            name = "خشب الأرز المخملي",
            brand = "رويال سنت",
            price = 390.0,
            category = "خشبي دافئ",
            description = "طابع رجالي ونسائي مميز بالثبات والهدوء يرتكز على الأخشاب العطرية النادرة.",
            notes = "أرز أطلسي، جلود فاخرة، باتشولي، حبوب التونكا"
        ),
        Perfume(
            id = 8,
            name = "نسيم البحر والمسك",
            brand = "أوشن جولد",
            price = 230.0,
            category = "انتعاش ونظافة",
            description = "عبق رذاذ البحر والملح البحري ممتزج بالمسك الخفيف لإطلالة صيفية مفعمة بالحيوية.",
            notes = "نفحات بحرية، ملح ملحي، مسك كريستالي، أوراق الزيتون"
        )
    )

    fun getFavorites(): List<Perfume> {
        return perfumesList.filter { it.isFavorite }
    }

    fun getCartItems(): List<Perfume> {
        return perfumesList.filter { it.inCart && it.cartQuantity > 0 }
    }
}
