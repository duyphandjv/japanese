/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phanduy.tekotest.viewmodel

import androidx.lifecycle.*
import com.phanduy.tekotest.data.JapaneseWord
import kotlinx.coroutines.Dispatchers
import java.util.Random

@Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
class GamingViewModel(
        private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val hashMap : HashMap<String, ArrayList<String>> = HashMap()
    init {
        hashMap.put("a", arrayListOf("a", "i", "u", "e", "o"))
        hashMap.put("ka", arrayListOf("ka", "ki", "ku", "ke", "ko"))
        hashMap.put("sa", arrayListOf("sa", "shi", "su", "se", "so"))
        hashMap.put("ta", arrayListOf("ta", "chi", "tsu", "te", "to"))
        hashMap.put("na", arrayListOf("na", "ni", "nu", "ne", "no"))
        hashMap.put("ha", arrayListOf("ha", "hi", "fu", "he", "ho"))
        hashMap.put("ma", arrayListOf("ma", "mi", "mu", "me", "mo"))
        hashMap.put("ya", arrayListOf("ya", "yu", "yo"))
        hashMap.put("ra", arrayListOf("ra", "ri", "ru", "re", "ro"))
        hashMap.put("wa", arrayListOf("wa","o(wo)"))
        hashMap.put("n", arrayListOf("n"))
        hashMap.put("All", arrayListOf("a", "i", "u", "e", "o","ka", "ki", "ku", "ke", "ko","sa", "shi", "su", "se", "so","ta", "chi", "tsu", "te", "to"
            ,"na", "ni", "nu", "ne", "no","ha", "hi", "fu", "he", "ho","ma", "mi", "mu", "me", "mo","ya", "yu", "yo","ra", "ri", "ru", "re", "ro","wa","wo","n"))
    }


    val words : LiveData<ArrayList<JapaneseWord>> = getRandomWords().switchMap {
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            val list = ArrayList<JapaneseWord>()
            for (i in it) {
                list.add(JapaneseWord.build(i))
            }
            emit(list)
        }
    }

    fun refresh() {
        saveRandomWords(genRandomWords())
    }

    private fun genRandomWords(): ArrayList<String> {
        val size = LIST_GROUPS.size
        var count = 0
        val random = Random()
        val listGroups = ArrayList<String>()
        val listWords = ArrayList<String>()
        for (i in 0 until size) {
            if (count > 3) break
            val randomValue = random.nextInt(10)
            if (randomValue % 2 == 0) {
                count++
                listGroups.add(LIST_GROUPS[i])
            }
        }

        if (listGroups.isEmpty()) {
            listGroups.add("a")
            listGroups.add("ka")
            listGroups.add("sa")
        }

        for (g in listGroups) {
            listWords.addAll(hashMap[g]!!)
        }
        return listWords
    }

    companion object {
        private const val GROUPS = "GROUPS"
        private const val WORDS = "WORDS"
        private val LIST_GROUPS = arrayListOf("a", "ka", "sa", "ta", "na", "ha", "ma", "ya", "ra", "wa", "n")
    }

    private fun saveRandomWords(words: ArrayList<String>) {
        return savedStateHandle.set(WORDS, words)
    }

    private fun getRandomWords(): LiveData<ArrayList<String>> {
        return savedStateHandle.getLiveData<ArrayList<String>>(WORDS, hashMap["a"])
    }
}
