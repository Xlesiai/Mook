package com.example.mook.helper

class Book(val title: String = "", val author: String = "", val cover: String = "", val text: String = "", val audio: String = "") {
    companion object{

    }
    override fun toString(): String {
        return "Title: ${title}| Author: ${author}| Cover: ${cover}| Text: ${text}| Audio: ${audio}|"
    }

    fun toJsonString(): String
    {
        return "{\"title\": \"${title}\" \"author\": \"${author}\" \"cover\": \"${cover}\" \"text\": \"${text}\" \"audio\": \"${audio}\" }"
    }

}