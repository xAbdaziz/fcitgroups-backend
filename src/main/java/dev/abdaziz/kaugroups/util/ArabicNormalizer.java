package dev.abdaziz.kaugroups.util;

public final class ArabicNormalizer {

    private ArabicNormalizer() {}

    public static String normalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        return text
                // Remove tashkeel (diacritics)
                .replaceAll("[\\u064B-\\u065F]", "")

                .replace("أ", "ا")
                .replace("إ", "ا")
                .replace("آ", "ا")
                .replace("ٱ", "ا")

                .replace("ة", "ه")

                .replace("ى", "ي")
                // Remove tatweel (ـ)
                .replace("\u0640", "")

                .toLowerCase();
    }
}



