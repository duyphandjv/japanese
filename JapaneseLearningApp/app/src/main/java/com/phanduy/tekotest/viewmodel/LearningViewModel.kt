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
import kotlin.random.Random

@Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
class LearningViewModel(
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

    companion object {
        private const val GROUP = "GROUP"
        private const val WORD = "WORD"
        private const val WORD_INDEX = "WORD_INDEX"
    }

    val currentGroup : LiveData<String> = getGroup()
    val currentWord : LiveData<String> = getWord()

    val currentIndex : LiveData<Int> = getWordIndex()

    fun randomIndex() {
        val group = currentGroup.value!!
        val listData: ArrayList<String>? = hashMap.get<Any, java.util.ArrayList<String>>(group)
        if (listData.isNullOrEmpty()) return
        val randomIndex = randomIndex(listData.size, currentIndex.value!!)
        updateWord(randomIndex)
    }

    fun nextIndex() {
        val group = currentGroup.value!!
        val listData: ArrayList<String>? = hashMap.get<Any, java.util.ArrayList<String>>(group)
        if (listData.isNullOrEmpty()) return
        val randomIndex = randomIndex(listData.size, currentIndex.value!!)
        updateWord(randomIndex)

        var size = listData.size
        if(size <= 1) return

        val nextIndex = nextIndex(size, currentIndex.value!!)
        updateWord(nextIndex)
    }

    private fun nextIndex(size: Int, currentIndex: Int): Int {
        return if(currentIndex < size - 1) {
            currentIndex + 1
        } else {
            0
        }
    }

    private fun randomIndex(size: Int, currentIndex: Int): Int {
        if(size <= 1) return 0

        var index: Int = Random.nextInt(size)
        while (index == currentIndex) {
            index = Random.nextInt(size)
        }

        return index
    }

    fun updateGroup(group: String) {
        setGroup(group)
        updateWord(0)
    }

    private fun updateWord(index: Int) {
        setWordIndex(index)
        val group = currentGroup.value!!
        val listDatas: ArrayList<String>? = hashMap.get<Any, java.util.ArrayList<String>>(group)
        setWord(listDatas!!.get(index))
    }

    private fun setGroup(group : String) {
        savedStateHandle.set(GROUP, group)
    }

    fun setWordIndex(wordIndex : Int) {
        savedStateHandle.set(WORD_INDEX, wordIndex)
    }

    fun setWord(word: String) {
        savedStateHandle.set(WORD, word)
    }

    private fun getGroup() : MutableLiveData<String>{
        return savedStateHandle.getLiveData<String>(GROUP, "a")
    }

    private fun getWordIndex() : MutableLiveData<Int>{
        return savedStateHandle.getLiveData<Int>(WORD_INDEX, 0)
    }

    private fun getWord() : MutableLiveData<String>{
        return savedStateHandle.getLiveData<String>(WORD, "a")
    }
}