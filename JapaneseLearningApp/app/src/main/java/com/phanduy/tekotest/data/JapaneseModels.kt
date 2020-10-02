package com.phanduy.tekotest.data

data class JapaneseWord(val id: Int, val group: String, val word: String) {

    companion object {
        fun build(wordName: String, group: String): JapaneseWord {
            return JapaneseWord(
                wordName.hashCode(),
                group,
                wordName
            )
        }

        fun build(wordName: String): JapaneseWord {
            return JapaneseWord(
                wordName.hashCode(),
                "",
                wordName
            )
        }
    }

}