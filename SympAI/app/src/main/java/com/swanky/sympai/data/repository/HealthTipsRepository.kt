package com.swanky.sympai.data.repository

import com.swanky.sympai.R
import com.swanky.sympai.data.model.HealthTip
import com.swanky.sympai.data.model.HealthTipCategory

class HealthTipsRepository {
    
    fun getAllHealthTips(): List<HealthTip> {
        return listOf(
            // Daily Tips
            HealthTip(
                id = 1,
                title = "Su İçin",
                description = "Günde en az 8 bardak su için. Su, vücudunuzun düzgün çalışması için hayati önem taşır.",
                category = HealthTipCategory.DAILY_TIPS
            ),
            HealthTip(
                id = 2,
                title = "Egzersiz Yapın",
                description = "Günde en az 30 dakika yürüyüş yapın. Düzenli egzersiz kalp sağlığınızı korur.",
                category = HealthTipCategory.DAILY_TIPS
            ),
            HealthTip(
                id = 3,
                title = "Yeterli Uyuyun",
                description = "Günde 7-8 saat uyumaya özen gösterin. Kaliteli uyku bağışıklık sisteminizi güçlendirir.",
                category = HealthTipCategory.DAILY_TIPS
            ),
            HealthTip(
                id = 4,
                title = "Dengeli Beslenin",
                description = "Bol meyve ve sebze tüketin. Dengeli beslenme hastalıklara karşı koruma sağlar.",
                category = HealthTipCategory.DAILY_TIPS
            ),
            
            // Common Diseases
            HealthTip(
                id = 5,
                title = "Soğuk Algınlığı",
                description = "Burun akıntısı, hapşırma ve boğaz ağrısı ile karakterize viral enfeksiyon. Bol sıvı tüketin ve dinlenin.",
                category = HealthTipCategory.COMMON_DISEASES
            ),
            HealthTip(
                id = 6,
                title = "Grip",
                description = "Ateş, kas ağrıları ve yorgunluk ile seyreden viral hastalık. İstirahat edin ve bol sıvı alın.",
                category = HealthTipCategory.COMMON_DISEASES
            ),
            HealthTip(
                id = 7,
                title = "Baş Ağrısı",
                description = "Stres, yorgunluk veya dehidrasyon nedeniyle oluşabilen yaygın şikayet. Su için ve dinlenin.",
                category = HealthTipCategory.COMMON_DISEASES
            ),
            
            // First Aid
            HealthTip(
                id = 8,
                title = "Yanık",
                description = "Soğuk su ile 10-15 dakika soğutun. Buz kullanmayın. Ciddi yanıklarda doktora başvurun.",
                category = HealthTipCategory.FIRST_AID
            ),
            HealthTip(
                id = 9,
                title = "Kesik",
                description = "Temiz bezle baskı uygulayın. Kanamayı durdurmaya çalışın. Derin kesiklerde doktora gidin.",
                category = HealthTipCategory.FIRST_AID
            ),
            HealthTip(
                id = 10,
                title = "Boğulma",
                description = "Heimlich manevrası uygulayın. Acil servisi arayın. Panik yapmayın.",
                category = HealthTipCategory.FIRST_AID
            ),
            
            // Healthy Living
            HealthTip(
                id = 11,
                title = "Stres Yönetimi",
                description = "Derin nefes alın, meditasyon yapın ve hobilerinize zaman ayırın. Stres sağlığınızı olumsuz etkiler.",
                category = HealthTipCategory.HEALTHY_LIVING
            ),
            HealthTip(
                id = 12,
                title = "Kişisel Hijyen",
                description = "Ellerinizi sık sık yıkayın ve kişisel temizliğinize özen gösterin. Hastalıklardan korunun.",
                category = HealthTipCategory.HEALTHY_LIVING
            ),
            HealthTip(
                id = 13,
                title = "Düzenli Kontrol",
                description = "Yılda en az bir kez genel sağlık kontrolünden geçirin. Erken teşhis hayat kurtarır.",
                category = HealthTipCategory.HEALTHY_LIVING
            )
        )
    }
    
    fun getHealthTipsByCategory(category: HealthTipCategory): List<HealthTip> {
        return getAllHealthTips().filter { it.category == category }
    }
}